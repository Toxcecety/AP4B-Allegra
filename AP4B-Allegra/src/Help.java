import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Help implements ActionListener {


    // ---------------------- ATTRIBUTES ---------------------- //


    // ---------------------- CONSTRUCTOR ---------------------- //


    // ---------------------- OVERRIDE Methods ---------------------- //

    // Display the help window
    @Override
    public void actionPerformed(ActionEvent e) {
        // Create the help window as a JDialog
        JDialog jDialog = new JDialog();

        // Create a label to display the help message with a picture
        JLabel jLabel = new JLabel(new ImageIcon("src\\Images\\rule.png"));

        // Set the size of the JLabel
        jLabel.setBounds(0, 0, 599, 727);

        // Add the JLabel to the JDialog
        jDialog.getContentPane().add(jLabel);

        // Set the size of the JDialog
        jDialog.setSize(800, 800);

        // Set the location of the JDialog
        jDialog.setAlwaysOnTop(true);

        // Set the JDialog at the center of the screen
        jDialog.setLocationRelativeTo(null);

        // Set the JDialog as modal
        jDialog.setModal(true);

        // Display the JDialog
        jDialog.setVisible(true);
    }
}
