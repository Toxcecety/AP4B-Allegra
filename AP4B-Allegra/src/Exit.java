import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Exit implements ActionListener {

    // ---------------------- ATTRIBUTES ---------------------- //

    // Exit the program and close the window
    private final JFrame j;


    // ---------------------- CONSTRUCTOR ---------------------- //

    public Exit(JFrame j) {
        this.j = j;
    }


    // ---------------------- OVERRIDE Methods ---------------------- //

    @Override
    public void actionPerformed(ActionEvent e) {
        // Close the window
        j.dispose();
    }
}
