package gui.load;

import data.Datasource;
import data.FutureMatch;
import data.Match;
import data.Team;
import gui.MainMenu;
import gui.display.Display;
import json.save.SaveFutureMatches;
import json.save.SaveJSON;
import json.save.SaveMatches;
import json.save.SaveTeams;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.InputMismatchException;
import java.util.List;

public class LoadData implements Loadable {
    private Datasource datasource = new Datasource();

    @Override
    public void load(Load whatToDisplay) throws InputMismatchException {
        if (whatToDisplay == Load.TEAMS) {
            loadTeams();
        } else if (whatToDisplay == Load.MATCHES) {
            loadMatches();
        } else if (whatToDisplay == Load.FUTURE_MATCHES) {
            loadFutureMatches();
        } else {
            throw new InputMismatchException("Wrong enum");
        }

        MainMenu.goBackToMainMenu();
    }

    private void loadTeams() {
        List<Team> teams = datasource.getTeams();
        SaveJSON<Team> saveTeams = new SaveTeams<>();
        saveTeams.save(teams);
    }

    private void loadMatches() {
        List<Match> matches = datasource.getMatches();
        SaveJSON<Match> saveMatches = new SaveMatches<>();
        saveMatches.save(matches);
    }

    private void loadFutureMatches() {
        List<FutureMatch> futureMatches = datasource.getFutureMatches();
        SaveJSON<FutureMatch> saveFutureMatches = new SaveFutureMatches<>();
        saveFutureMatches.save(futureMatches);
    }
}
