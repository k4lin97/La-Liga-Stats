package gui;

import gui.display.Display;
import gui.display.DisplayData;
import gui.load.Load;
import gui.load.LoadData;

import javax.swing.*;
import java.util.InputMismatchException;

public class MainMenu extends JFrame {
    public MainMenu() throws InputMismatchException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final String[] options = {"Display Table", "Display Results", "Display Fixtures", "Display Statistics", "Load Teams", "Load Results", "Load Fixtures", "Exit"};

        int result = JOptionPane.showOptionDialog(null, "What would you like to do?", "La Liga statistics",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[options.length - 1]);

        switch (result) {
            case 0:
                new DisplayData().display(Display.TABLE);
                break;
            case 1:
                new DisplayData().display(Display.RESULTS);
                break;
            case 2:
                new DisplayData().display(Display.FIXTURES);
                break;
            case 3:
                new DisplayData().display(Display.STATISTICS);
                break;
            case 4:
                new LoadData().load(Load.TEAMS);
                break;
            case 5:
                new LoadData().load(Load.MATCHES);
                break;
            case 6:
                new LoadData().load(Load.FUTURE_MATCHES);
                break;
            case 7:
                System.exit(0);
                break;
        }
    }

    public static void goBackToMainMenu() {
        int returnValue = JOptionPane.showConfirmDialog(null, "Do you want to go back to main menu?",
                "Go back", JOptionPane.YES_NO_OPTION);

        if (returnValue == JOptionPane.YES_OPTION) {
            new MainMenu();
        } else if (returnValue == JOptionPane.NO_OPTION) {
            System.exit(0);
        }
    }
}
