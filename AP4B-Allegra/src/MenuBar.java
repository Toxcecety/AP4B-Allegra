import javax.swing.*;

public class MenuBar extends JMenuBar {

    // ---------------------- ATTRIBUTES ---------------------- //


    // ---------------------- CONSTRUCTOR ---------------------- //

    public MenuBar(JFrame frame) {
        // Create the menu bar
        JMenuBar menuBar = new JMenuBar();

        // Create the different menu
        JMenu fileMenu = new JMenu("FILE");
        JMenu helpMenu = new JMenu("help");

        // Add the menu to the menu bar
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);

        // Create the different menu items
        //JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem exitItem = new JMenuItem("Exit");
        JMenuItem helpItem = new JMenuItem("Help");

        // Add listeners to the menu items
        exitItem.addActionListener(new Exit(frame));
        helpItem.addActionListener(new Help());

        // Add the menu items to the menu
        //fileMenu.add(saveItem);
        fileMenu.add(exitItem);
        helpMenu.add(helpItem);

        // Add the menu bar to the frame
        add(menuBar);
    }
}
