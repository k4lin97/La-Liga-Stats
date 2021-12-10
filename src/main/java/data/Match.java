package data;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class Match {
    private Calendar date;
    private Team homeTeam;
    private Team awayTeam;
    private int homeTeamScore;
    private int awayTeamScore;

    public Match(Calendar date, Team homeTeam, Team awayTeam, int homeTeamScore, int awayTeamScore) {
        this.date = date;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeTeamScore = homeTeamScore;
        this.awayTeamScore = awayTeamScore;
    }

    public Calendar getDate() {
        return date;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public int getHomeTeamScore() {
        return homeTeamScore;
    }

    public int getAwayTeamScore() {
        return awayTeamScore;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(homeTeam.getName());
        stringBuilder.append(" ");
        stringBuilder.append(homeTeamScore);
        stringBuilder.append(" - ");
        stringBuilder.append(awayTeamScore);
        stringBuilder.append(" ");
        stringBuilder.append(awayTeam.getName());

        stringBuilder.append(" | Data: ");
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        stringBuilder.append(format.format(date.getTime()));

        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Match)) return false;
        Match match = (Match) o;

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String date1 = format.format(match.getDate().getTime());
        String date2 = format.format(date.getTime());

        return date1.equals(date2) &&
                match.getHomeTeam().getName().equals(this.homeTeam.getName()) &&
                match.getAwayTeam().getName().equals(this.awayTeam.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, homeTeam, awayTeam);
    }
}
