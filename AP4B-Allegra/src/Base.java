import javax.swing.*;

public interface Base {

    // ---------------------- ATTRIBUTES ---------------------- //

    JFrame frame = new JFrame();    // The frame of the game
    JPanel cards = new JPanel();    //  a panel that uses CardLayout

    int jeuWidth = 1980;            // Width of the game
    int jeuHeight = 1080;           // Height of the game

    int menuWidth = 415;            // Width of the menu
    int menuHeight = 555;           // Height of the menu

    int X = 3;                      // Number of raw of the grid for the board
    int Y = 4;                      // Number of column of the grid for the board

    // ---------------------- OTHER Methods ---------------------- //

}