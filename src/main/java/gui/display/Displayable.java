package gui.display;

import java.util.InputMismatchException;

public interface Displayable {
    void display(Display whatToDisplay) throws InputMismatchException;
}
