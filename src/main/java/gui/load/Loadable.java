package gui.load;

import gui.display.Display;

import java.util.InputMismatchException;

public interface Loadable {
    void load(Load whatToDisplay) throws InputMismatchException;
}
