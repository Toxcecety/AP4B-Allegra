import javax.swing.*;
import java.awt.*;


public class Fenetre implements Base {

    // ---------------------- ATTRIBUTES ---------------------- //


    // ---------------------- CONSTRUCTOR ---------------------- //

    /**
     * Build the window and panels to cardLayout
     * @param panels the panels to add to the cardLayout. Warning: Panels must have a name
     */
    public Fenetre(JPanel... panels) {

        // Set basic parameters of the window
        frame.setTitle("Allegra");
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(menuWidth, menuHeight);

        // Add the Menu bar
        frame.getContentPane().add(BorderLayout.NORTH, new MenuBar(frame));

        // Create the cardLayout
        cards.setLayout(new CardLayout());
        for (JPanel i : panels) {
            cards.add(i, i.getName());
        }

        // And add it to the window
        frame.add(BorderLayout.CENTER, cards);

        // Finally, display the window
        frame.setVisible(true);
    }


    // ---------------------- OTHER Methods ---------------------- //

    /**
     * Add a new panel to the cardLayout
     * The panel must have a name
     * @param panel the panel to add
     */
    public void addPanel(JPanel panel) {
        cards.add(panel, panel.getName());
    }
}
