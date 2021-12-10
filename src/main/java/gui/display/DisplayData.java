package gui.display;

import data.FutureMatch;
import data.Match;
import data.Team;
import gui.MainMenu;
import json.read.ReadJSON;
import json.read.ReadMatches;
import json.read.ReadTeams;
import org.apache.commons.math3.util.Precision;
import statistics.Statistics;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Vector;

public class DisplayData implements Displayable {
    private Statistics statistics = new Statistics();

    @Override
    public void display(Display whatToDisplay) throws InputMismatchException {
        JTable jTable;
        if (whatToDisplay == Display.TABLE) {
            jTable = prepareDataTable();
        } else if (whatToDisplay == Display.RESULTS) {
            jTable = prepareDataResults();
        } else if (whatToDisplay == Display.FIXTURES) {
            jTable = prepareDataFixtures();
        } else if (whatToDisplay == Display.STATISTICS) {
            jTable = prepareDataStatistics();
        } else {
            throw new InputMismatchException("Wrong enum");
        }

        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) jTable.getDefaultRenderer(Object.class);
        renderer.setHorizontalAlignment(SwingConstants.CENTER);

        JScrollPane scrollPane = new JScrollPane(jTable);
        scrollPane.setPreferredSize(new Dimension(800, 400));
        JOptionPane.showMessageDialog(null, scrollPane, "La Liga data", JOptionPane.INFORMATION_MESSAGE);

        MainMenu.goBackToMainMenu();
    }

    private JTable prepareDataTable() {
        ReadJSON<Team> readTeams = new ReadTeams<>();
        String teamsPath = "src//main//resources//teams.json";
        List<Team> teams = readTeams.read(teamsPath);

        Vector<String> columnNames = new Vector<>();
        columnNames.add("Position");
        columnNames.add("Name");
        columnNames.add("Played games");
        columnNames.add("Won games");
        columnNames.add("Drawn games");
        columnNames.add("Lost games");
        columnNames.add("Goals scored");
        columnNames.add("Goals conceded");
        columnNames.add("Points");

        if (teams == null) {
            return new JTable(null, columnNames);
        }

        Vector<Vector> data = new Vector<>();
        for (Team team : teams) {
            Vector<String> row = new Vector<>();
            row.add(String.valueOf(team.getPosition()));
            row.add(String.valueOf(team.getName()));
            row.add(String.valueOf(team.getGamesPlayed()));
            row.add(String.valueOf(team.getGamesWon()));
            row.add(String.valueOf(team.getGamesDrawn()));
            row.add(String.valueOf(team.getGamesLost()));
            row.add(String.valueOf(team.getGoalsScored()));
            row.add(String.valueOf(team.getGoalsConceded()));
            row.add(String.valueOf(team.getPoints()));

            data.add(row);
        }

        return new JTable(data, columnNames);
    }

    private JTable prepareDataResults() {
        ReadJSON<Match> readMatches = new ReadMatches<>();
        String matchesPath = "src//main//resources//matches.json";
        List<Match> matches = readMatches.read(matchesPath);

        Vector<String> columnNames = new Vector<>();
        columnNames.add("Home team");
        columnNames.add("Score");
        columnNames.add("Score");
        columnNames.add("Away team");
        columnNames.add("Date");

        if (matches == null) {
            return new JTable(null, columnNames);
        }

        Vector<Vector> data = new Vector<>();
        for (Match match : matches) {
            Vector<String> row = new Vector<>();
            row.add(match.getHomeTeam().getName());
            row.add(String.valueOf(match.getHomeTeamScore()));
            row.add(String.valueOf(match.getAwayTeamScore()));
            row.add(match.getAwayTeam().getName());

            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            row.add(format.format(match.getDate().getTime()));

            data.add(row);
        }

        return new JTable(data, columnNames);
    }

    private JTable prepareDataFixtures() {
        List<FutureMatch> futureMatches = statistics.getFutureMatches();

        Vector<String> columnNames = new Vector<>();
        columnNames.add("Home team");
        columnNames.add("Odd");
        columnNames.add("Draw odd");
        columnNames.add("Odd");
        columnNames.add("Away team");
        columnNames.add("Date");

        if (futureMatches == null) {
            return new JTable(null, columnNames);
        }

        Vector<Vector> data = new Vector<>();
        for (FutureMatch futureMatch : futureMatches) {
            Vector<String> row = new Vector<>();
            row.add(futureMatch.getHomeTeam().getName());
            row.add(String.valueOf(futureMatch.getHomeTeamOdd()));
            row.add(String.valueOf(futureMatch.getDrawOdd()));
            row.add(String.valueOf(futureMatch.getAwayTeamOdd()));
            row.add(futureMatch.getAwayTeam().getName());

            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            row.add(format.format(futureMatch.getDate().getTime()));

            data.add(row);
        }

        return new JTable(data, columnNames);
    }

    private JTable prepareDataStatistics() {
        List<Double> wonOdds = statistics.getListOfWonOdds();
        int lowOddsNum = 0; // 1.00 - 1.99
        int avgOddsNum = 0; // 2.00 - 2.99
        int hghOddsNum = 0; // 3.00 +
        int allOddsNum = wonOdds.size();

        for (Double odd : wonOdds) {
            if (odd <= 1.99) {
                lowOddsNum++;
            } else if (odd > 1.99 && odd <= 2.99) {
                avgOddsNum++;
            } else {
                hghOddsNum++;
            }
        }

        Vector<String> columnNames = new Vector<>();
        columnNames.add("Won odds in range 1.00 - 1.99");
        columnNames.add("Won odds in range 2.00 - 2.99");
        columnNames.add("Won odds in range 3.00 +");

        Vector<Vector> data = new Vector<>();
        Vector<Double> row = new Vector<>();

        row.add(Precision.round((((double) lowOddsNum / (double) allOddsNum) * 100), 2));
        row.add(Precision.round((((double) avgOddsNum / (double) allOddsNum) * 100), 2));
        row.add(Precision.round((((double) hghOddsNum / (double) allOddsNum) * 100), 2));

        data.add(row);

        return new JTable(data, columnNames);
    }
}
