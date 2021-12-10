package data;

import org.apache.commons.math3.util.Precision;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Datasource {
    private static final String LA_LIGA_TABLE_URL = "https://www.skysports.com/la-liga-table";
    private static final String LA_LIGA_TEAM_CLASS = "standing-table__row";

    private static final String LA_LIGA_RESULTS_URL = "https://www.skysports.com/la-liga-results";
    private static final String LA_LIGA_RESULT_CLASS = "fixres__body";

    private static final String LA_LIGA_FUTURE_MATCHES_URL = "https://www.skysports.com/la-liga-fixtures";
    private static final String LA_LIGA_FUTURE_MATCHES_CLASS = "fixres__body";

    // # data.Team Pl W D L F A GD Pts Last 6
    // Splits into: position, name, games played, games won, games drawn, goals scored, goals conceded, points
    private static final String TEAM_INFO_REGEX = "(\\d+)\\s([a-zA-z]+\\s?[a-zA-z]+)\\s(\\d+)\\s(\\d+)\\s(\\d+)\\s(\\d+)\\s(\\d+)\\s(\\d+)\\s(-?\\d+)\\s(-?\\d+)";

    // Splits into different matches
    private static final String MATCH_INFO_REGEX = "(.*?)FT";

    // Splits into: day of week, day of month, month, home team, home team score, away team score, away team
    private static final String MATCH_INFO_REGEX_DATE = "([MTWFS]?[a-z]+)\\s(\\d{1,2})[thns]+\\s([A-Z]?[a-z]+)" +
            "\\s([a-zA-z]+\\s?[a-zA-z]+)\\s(\\d+)\\s(\\d+).*?([a-zA-z]+\\s?[a-zA-z]+)";

    // Splits into home team, home team score, away team score, away team
    private static final String MATCH_INFO_REGEX_NO_DATE = "\\s([a-zA-z]+\\s?[a-zA-z]+)\\s(\\d+)\\s(\\d+).*?([a-zA-z]+\\s?[a-zA-z]+)";

    // Searches for a date
    private static final String LA_LIGA_FUTURE_MATCHES_REGEX = "([MTWFS]+[a-z]+\\s\\d{1,2}[thns]+\\s[A-Z][a-z]+)";

    // Splits into: home team name, away team name, odds
    private static final String FUTURE_MATCHES_REGEX = "([a-zA-z]+\\s?[a-zA-z]+).*?([a-zA-z]+\\s?[a-zA-z]+)\\sHome\\s(\\d{1,2})/" +
            "(\\d{1,2})\\s(\\d{1,2})/(\\d{1,2})\\sAway\\s(\\d{1,2})/(\\d{1,2})";

    // Splits into: day of week, day of month, month
    private static final String DATE_REGEX = "([A-Za-z]+)\\s(\\d+)[a-z]+\\s([A-Za-z]+)";

    private List<Team> teams;
    private List<Match> matches;
    private List<FutureMatch> futureMatches;

    public Datasource() {
        teams = null;
        matches = null;
        futureMatches = null;
    }

    private void getLaLigaTeams() {
        this.teams = new ArrayList<>();

        Pattern teamInfoPattern = Pattern.compile(TEAM_INFO_REGEX);

        try {
            Document document = Jsoup.connect(LA_LIGA_TABLE_URL).get();
            Elements teamsFromWeb = document.getElementsByClass(LA_LIGA_TEAM_CLASS);

            // i = 1, because first occurrence is "data.Team"
            for (int i = 1; i < teamsFromWeb.size(); i++) {
                Matcher teamInfoMatcher = teamInfoPattern.matcher(teamsFromWeb.get(i).text());

                while (teamInfoMatcher.find()) {
                    int position = Integer.parseInt(teamInfoMatcher.group(1));
                    String name = teamInfoMatcher.group(2);
                    int gamesPlayed = Integer.parseInt(teamInfoMatcher.group(3));
                    int gamesWon = Integer.parseInt(teamInfoMatcher.group(4));
                    int gamesDrawn = Integer.parseInt(teamInfoMatcher.group(5));
                    int gamesLost = Integer.parseInt(teamInfoMatcher.group(6));
                    int goalsScored = Integer.parseInt(teamInfoMatcher.group(7));
                    int goalsConceded = Integer.parseInt(teamInfoMatcher.group(8));
                    int points = Integer.parseInt(teamInfoMatcher.group(10));

                    Team team = new Team(position, name, gamesPlayed, gamesWon, gamesDrawn, gamesLost, goalsScored, goalsConceded, points);
                    this.teams.add(team);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getLaLigaMatches() {
        this.matches = new ArrayList<>();
        getLaLigaTeams();

        Pattern matchInfoPattern = Pattern.compile(MATCH_INFO_REGEX);

        try {
            Document document = Jsoup.connect(LA_LIGA_RESULTS_URL).get();
            Elements matchesFromWeb = document.getElementsByClass(LA_LIGA_RESULT_CLASS);

            for (org.jsoup.nodes.Element element : matchesFromWeb) {
                Matcher matchInfoMatcher = matchInfoPattern.matcher(element.text());

                String dayOfWeek = "";
                String dayOfMonth = "";
                String month = "";
                Calendar date;

                String homeTeam = "";
                int homeTeamScore = 0;
                int awayTeamScore = 0;
                String awayTeam = "";

                while (matchInfoMatcher.find()) {
                    if (doesContainDate(matchInfoMatcher.group(1))) {
                        Pattern pattern = Pattern.compile(MATCH_INFO_REGEX_DATE);
                        Matcher matcher = pattern.matcher(matchInfoMatcher.group(1));
                        if (matcher.find()) {
                            dayOfWeek = matcher.group(1);
                            dayOfMonth = matcher.group(2);
                            month = matcher.group(3);
                            homeTeam = matcher.group(4);
                            homeTeamScore = Integer.parseInt(matcher.group(5));
                            awayTeamScore = Integer.parseInt(matcher.group(6));
                            awayTeam = matcher.group(7);
                        }
                    } else {
                        Pattern pattern = Pattern.compile(MATCH_INFO_REGEX_NO_DATE);
                        Matcher matcher = pattern.matcher(matchInfoMatcher.group(1));
                        if (matcher.find()) {
                            homeTeam = matcher.group(1);
                            homeTeamScore = Integer.parseInt(matcher.group(2));
                            awayTeamScore = Integer.parseInt(matcher.group(3));
                            awayTeam = matcher.group(4);
                        }
                    }

                    date = convertStringToCalendarDate(dayOfWeek, dayOfMonth, month);

                    Match match = new Match(date, findTeamByName(homeTeam), findTeamByName(awayTeam), homeTeamScore, awayTeamScore);
                    this.matches.add(match);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getLaLigaFutureMatches() {
        this.futureMatches = new ArrayList<>();
        getLaLigaTeams();

        Pattern matchInfoPattern = Pattern.compile(LA_LIGA_FUTURE_MATCHES_REGEX);

        try {
            Document document = Jsoup.connect(LA_LIGA_FUTURE_MATCHES_URL).get();
            Elements matchesFromWeb = document.getElementsByClass(LA_LIGA_FUTURE_MATCHES_CLASS);

            Element element = matchesFromWeb.get(0);
            Matcher matchInfoMatcher = matchInfoPattern.matcher(element.text());

            List<Integer> startIndexes = new ArrayList<>();
            List<Integer> endIndexes = new ArrayList<>();
            while (matchInfoMatcher.find()) {
                startIndexes.add(matchInfoMatcher.start());
                endIndexes.add(matchInfoMatcher.end());
            }

            List<String> dates = new ArrayList<>();
            List<String> matches = new ArrayList<>();
            for (int i = 0; i < startIndexes.size(); i++) {
                dates.add(element.text().substring(startIndexes.get(i), endIndexes.get(i)));
            }
            for (int i = 0; i < startIndexes.size(); i++) {
                if (i == startIndexes.size() - 1) {
                    matches.add(element.text().substring(endIndexes.get(i)));
                } else {
                    matches.add(element.text().substring(endIndexes.get(i), startIndexes.get(i + 1)));
                }
            }

            for (int i = 0; i < startIndexes.size(); i++) {
                if (!doesContainOdds(matches.get(i))) return;
                addToFutureMatches(dates.get(i), matches.get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addToFutureMatches(String stringDate, String matches) {
        Pattern matchPattern = Pattern.compile(FUTURE_MATCHES_REGEX);
        Matcher matchMatcher = matchPattern.matcher(matches);

        while (matchMatcher.find()) {
            String homeTeamName = matchMatcher.group(1);
            String awayTeamName = matchMatcher.group(2);
            int homeTeamOdd1 = Integer.parseInt(matchMatcher.group(3));
            int homeTeamOdd2 = Integer.parseInt(matchMatcher.group(4));
            int drawOdd1 = Integer.parseInt(matchMatcher.group(5));
            int drawOdd2 = Integer.parseInt(matchMatcher.group(6));
            int awayTeamOdd1 = Integer.parseInt(matchMatcher.group(7));
            int awayTeamOdd2 = Integer.parseInt(matchMatcher.group(8));

            double homeTeamOdd = Precision.round(((double) homeTeamOdd1 / (double) homeTeamOdd2) + 1, 2);
            double drawOdd = Precision.round(((double) drawOdd1 / (double) drawOdd2) + 1, 2);
            double awayTeamOdd = Precision.round(((double) awayTeamOdd1 / (double) awayTeamOdd2) + 1, 2);

            futureMatches.add(createFutureMatch(stringDate, homeTeamName, awayTeamName, homeTeamOdd, drawOdd, awayTeamOdd));
        }
    }

    private FutureMatch createFutureMatch(String stringDate, String homeTeamName, String awayTeamName, double homeTeamOdd, double drawOdd, double awayTeamOdd) {
        String dateRegex = DATE_REGEX;
        Pattern datePattern = Pattern.compile(dateRegex);
        Matcher dateMatcher = datePattern.matcher(stringDate);

        Calendar date = Calendar.getInstance();
        if (dateMatcher.find()) {
            date = convertStringToCalendarDate(dateMatcher.group(1), dateMatcher.group(2), dateMatcher.group(3));
        }

        return new FutureMatch(date, findTeamByName(homeTeamName), findTeamByName(awayTeamName), homeTeamOdd, drawOdd, awayTeamOdd);
    }

    private boolean doesContainOdds(String input) {
        return input.contains("/");
    }

    private boolean doesContainDate(String input) {
        if (input.toLowerCase().contains("monday")) {
            return true;
        } else if (input.toLowerCase().contains("tuesday")) {
            return true;
        } else if (input.toLowerCase().contains("wednesday")) {
            return true;
        } else if (input.toLowerCase().contains("thursday")) {
            return true;
        } else if (input.toLowerCase().contains("friday")) {
            return true;
        } else if (input.toLowerCase().contains("saturday")) {
            return true;
        } else return input.toLowerCase().contains("sunday");
    }

    private Team findTeamByName(String name) {
        Team teamToReturn = null;
        for (Team team : this.teams) {
            if (team.getName().equals(name)) {
                teamToReturn = team;
                break;
            }
        }
        return teamToReturn;
    }

    private Calendar convertStringToCalendarDate(String dayOfWeek, String dayOfMonth, String month) {
        if (dayOfWeek == null || dayOfWeek.isEmpty() || dayOfMonth == null || dayOfMonth.isEmpty() || month == null ||
                month.isEmpty()) {
            return Calendar.getInstance();
        }

        Calendar date = Calendar.getInstance();

        switch (dayOfWeek.toLowerCase()) {
            case "monday":
                date.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                break;
            case "tuesday":
                date.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
                break;
            case "wednesday":
                date.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
                break;
            case "thursday":
                date.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
                break;
            case "friday":
                date.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
                break;
            case "saturday":
                date.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
                break;
            case "sunday":
                date.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                break;
        }

        date.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dayOfMonth));

        switch (month.toLowerCase()) {
            case "january":
                date.set(Calendar.YEAR, 2022);
                date.set(Calendar.MONTH, Calendar.JANUARY);
                break;
            case "february":
                date.set(Calendar.YEAR, 2022);
                date.set(Calendar.MONTH, Calendar.FEBRUARY);
                break;
            case "march":
                date.set(Calendar.YEAR, 2022);
                date.set(Calendar.MONTH, Calendar.MARCH);
                break;
            case "april":
                date.set(Calendar.YEAR, 2022);
                date.set(Calendar.MONTH, Calendar.APRIL);
                break;
            case "may":
                date.set(Calendar.YEAR, 2022);
                date.set(Calendar.MONTH, Calendar.MAY);
                break;
            case "june":
                date.set(Calendar.YEAR, 2022);
                date.set(Calendar.MONTH, Calendar.JUNE);
                break;
            case "july":
                date.set(Calendar.YEAR, 2022);
                date.set(Calendar.MONTH, Calendar.JULY);
                break;
            case "august":
                date.set(Calendar.YEAR, 2021);
                date.set(Calendar.MONTH, Calendar.AUGUST);
                break;
            case "september":
                date.set(Calendar.YEAR, 2021);
                date.set(Calendar.MONTH, Calendar.SEPTEMBER);
                break;
            case "october":
                date.set(Calendar.YEAR, 2021);
                date.set(Calendar.MONTH, Calendar.OCTOBER);
                break;
            case "november":
                date.set(Calendar.YEAR, 2021);
                date.set(Calendar.MONTH, Calendar.NOVEMBER);
                break;
            case "december":
                date.set(Calendar.YEAR, 2021);
                date.set(Calendar.MONTH, Calendar.DECEMBER);
                break;
        }

        return date;
    }

    public List<Team> getTeams() {
        getLaLigaTeams();
        return teams;
    }

    public List<Match> getMatches() {
        getLaLigaMatches();
        return matches;
    }

    public List<FutureMatch> getFutureMatches() {
        getLaLigaFutureMatches();
        return futureMatches;
    }
}
