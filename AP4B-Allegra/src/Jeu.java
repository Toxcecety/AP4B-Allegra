import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Jeu extends JPanel implements Base, ActionListener, MouseListener {


    // ---------------------- ATTRIBUTES ---------------------- //

    private int colonne;    // Column of the grid
    private int ligne;      // Raw of the grid

    // Table of cards
    // 12 cards per player
    // unknown player by now
    private final MyJLabel[][] display_cards;

    private final JLabel[] namePlayer;

    private MyJLabel hiddenDrawPile;            // Hidden draw pile Panel
    private MyJLabel revealedDrawPile;          // Revealed draw pile Panel
    private MyJLabel discardPile;               // Discard pile Panel
    private MyJLabel redButton;                 // Red button Panel
    private JTextPane messageBox;               // Message box Panel
    private JButton frapperButton;             // Button to hit
    private JLabel note;

    // Table of JPanel to fil the grid.
    // The grid is 5 raw and 5 column.
    // 24 JPanels for game et 1 case for the menu button
    private final JPanel[] panels;

    private int[] playerJPanels;                // Table to know which panel is for which player

    private int drawAndDiscardJPanel;           // Panel for the draw and discard pile

    private int redJPanel;                      // Panel for the help button

    private int messagePanel;                   // Panel for the message

    private int frapperPanel;                   // Panel for the hit button

    private int gap;                            // Gap between the panels


    Defausse d;                                 // Discard pile

    Pioche p;                                   // Draw pile

    private int action;                         // Action to do  (0 = nothing, 1 = draw, 2 = discard, 3 = reveal, 4 = )

    private int[] cardSelected = new int[2];    // Table to know which card is selected
    
    private boolean playerCard;                 // 1 -> the selected card belongs to the player / 0 -> it belongs to another player

    private int nbJoueur;


    // ---------------------- CONSTRUCTOR ---------------------- //

    /**
     * Build the game panel
     */
    public Jeu(int nombreJoueur) {

        nbJoueur = nombreJoueur;
        // Initialize the column and raw of the grid of the panel
        switch (nbJoueur) {
            case 2, 3, 4 -> {
                colonne = 5;
                ligne = 5;
            }
            case 5, 6 -> {
                colonne = 7;
                ligne = 5;
            }
            default -> System.out.println("Nombre de joueurs invalide");
        }

        // Set basic information about the main panel
        setName("jeu");                             // Set the name of the main panel, it will be used to switch between panels
        setLayout(new GridLayout(ligne, colonne));  // Set the layout of the main panel to a grid layout

        // Set Size of the panel and location
        frame.setSize(jeuWidth, jeuHeight);
        frame.setLocationRelativeTo(null);

        // Generate the table to store Icon to be displayed of each player
        display_cards = new MyJLabel[nbJoueur][12];

        namePlayer = new JLabel[nbJoueur];

        // Initialize the number of panel in the grid
        // Number of panel in the grid
        int nbPanel = (colonne * ligne);

        // Initialize the table of JPanel who will fill the main grid
        panels = new JPanel[nbPanel];

        // Initialize the panels and set the layout of each panel to GridLayout with 3 raw and 4 column.
        // Sometimes we need to change the layout of the panel to display something else
        for (int i = 0; i < nbPanel; i++) {
            panels[i] = new JPanel();
            panels[i].setLayout(new GridLayout(3, 4, 5, 5));
        }

        note = new JLabel("A vous de jouer !");
        note.setHorizontalAlignment(SwingConstants.CENTER);
        note.setVerticalAlignment(SwingConstants.CENTER);
        note.setFont(new Font("Calibri", Font.BOLD, 20));

        // Initialize the table to know which panel is for which player with a given number of player
        switch (nbJoueur) {
            case 2 -> deuxJoueurs();
            case 3 -> troisJoueurs();
            case 4 -> quatreJoueurs();
            case 5 -> cinqJoueurs();
            case 6 -> sixJoueurs();
            default -> System.out.println("Nombre de joueur invalide");
        }

        // Then initialize all cards panel of each player
        initPanelPlayerCard();
        initDrawAndDiscardPile();
        initRedButton();
        initMessagePanel();
        initFrapperPanel();

        // Finally, add the all panels to the grid of the main panel
        for (int i = 0; i < nbPanel; i++) {
            add(panels[i]);
        }

        cardSelected[0] = -1;
        cardSelected[1] = -1;

        // Update the main panel
        revalidate();
    }


    // ---------------------- ACCESS Methods ---------------------- //

    /**
     * Get the selected card
     * @return Table of selected card
     */
    public int[] getCardSelected() {
        return cardSelected;
    }

    /**
     * Set the selected card
     * @param cardSelected Table of selected card
     */
    public void setCardSelected(int[] cardSelected) {
        this.cardSelected = cardSelected;
    }

    /**
     * get the action to do
     * @return Action to do
     */
    public int getAction() {
        return action;
    }

    /**
     * Set the action to do
     * @param action Action to do
     */
    public void setAction(int action) {
        this.action = action;
    }

    /**
     * Set the draw pile
     * @param d Draw pile
     */
    public void setD(Defausse d) {
        this.d = d;
    }

    /**
     * Set the discard pile
     * @param p Discard pile
     */
    public void setP(Pioche p) {
        this.p = p;
    }

    /**
     *
     */
    public boolean getplayerCard(){
        return playerCard;
    }

    // ---------------------- OTHER Methods ---------------------- //

    /**
     * Initialize the table of cards for each player
     */
    private void initPanelPlayerCard() {
        if (nbJoueur != 4 && nbJoueur != 6) {
            panels[playerJPanels[0] - gap].setLayout(new GridLayout(3, 1));
            panels[playerJPanels[0] - gap].add(new JLabel());
            panels[playerJPanels[0] - gap].add(new JLabel());
            panels[playerJPanels[0] - gap].add(note);
        }

        // For each player and each card of the player initialize the display of the card, add it to the panel and set the listener
        for (int i = 0; i < playerJPanels.length; i++) {
            for (int j = 0; j < 12; j++) {
                display_cards[i][j] = new MyJLabel();
                display_cards[i][j].addMouseListener(this);
                panels[playerJPanels[i]].add(display_cards[i][j]);
            }

            namePlayer[i] = new JLabel();
            namePlayer[i].setHorizontalAlignment(SwingConstants.CENTER);
            namePlayer[i].setVerticalAlignment(SwingConstants.CENTER);
            namePlayer[i].setFont(new Font("Calibri", Font.BOLD, 20));
            panels[playerJPanels[i] + gap].setLayout(new GridLayout(3,1));
            if ((playerJPanels[i] + gap) == redJPanel) {
                panels[playerJPanels[i] + gap].add(namePlayer[i]);
            } else if ((playerJPanels[i] + gap) == drawAndDiscardJPanel) {
                panels[playerJPanels[i] + gap].add(namePlayer[i]);
            } else {
                panels[playerJPanels[i] + gap].add(namePlayer[i]);
                if (nbJoueur != 4) {
                    for (int j = 0; j < 2; j++) {
                        panels[playerJPanels[i] + gap].add(new JLabel());
                    }
                } else {
                    panels[playerJPanels[i] + gap].add(new JLabel());
                    panels[playerJPanels[i] + gap].add(note);
                }
            }
        }
    }

    /**
     * Initialize the draw and discard pile
     */
    private void initDrawAndDiscardPile() {
        hiddenDrawPile = new MyJLabel();
        hiddenDrawPile.addMouseListener(this);

        revealedDrawPile = new MyJLabel();

        discardPile = new MyJLabel();
        discardPile.addMouseListener(this);
        if (nbJoueur == 3 || nbJoueur == 5 || nbJoueur == 6) {
            JPanel temp = new JPanel();
            temp.setLayout(new GridLayout(1, 5, 5, 5));
            temp.add(new JLabel());
            temp.add(hiddenDrawPile);
            temp.add(revealedDrawPile);
            temp.add(discardPile);
            temp.add(new JLabel());
            panels[drawAndDiscardJPanel].add(temp);
            panels[drawAndDiscardJPanel].add(new JLabel());
        } else {

            // Change the layout of the panel to a grid layout with 3 raw and 5 column
            panels[drawAndDiscardJPanel].setLayout(new GridLayout(3, 5, 5, 5));

            // Fill the panel with empty JLabel
            // except the 6th, 7th, and 8th JLabel for hidden draw pile, revealed draw pile and discard pile
            for (int i = 0; i < 15; i++) {
                if (i == 6) {
                    panels[drawAndDiscardJPanel].add(hiddenDrawPile);
                } else if (i == 7) {
                    panels[drawAndDiscardJPanel].add(revealedDrawPile);
                } else if (i == 8) {
                    panels[drawAndDiscardJPanel].add(discardPile);
                } else {
                    panels[drawAndDiscardJPanel].add(new MyJLabel());
                }
            }
        }
    }

    /**
     * Initialize the red button
     * The red button is used to know the column which belong to the active player
     */
    private void initRedButton() {
        redButton = new MyJLabel();
        if (nbJoueur == 4 || nbJoueur == 6) {
            panels[redJPanel].add(new JLabel());
            JPanel temp = new JPanel(new GridLayout(1, 4));
            for (int i = 0; i < 3; i++) {
                temp.add(new JPanel());
            }
            temp.add(redButton);
            panels[redJPanel].add(temp);
        } else {
            for (int i = 0; i < 11; i++) {
                panels[redJPanel].add(new MyJLabel());
            }
            panels[redJPanel].add(redButton);
        }
    }

    /**
     * Initialize the message panel
     */
    private void initMessagePanel() {
        messageBox = new JTextPane();
        panels[messagePanel].add(messageBox);
    }

    private void initFrapperPanel() {
        frapperButton = new JButton("Frapper");
        frapperButton.setVisible(false);
        frapperButton.addActionListener(this);
        panels[frapperPanel].setLayout(new GridLayout(3, 3));
        for (int i = 0; i < 9; i++) {
            if (i == 4) {
                panels[frapperPanel].add(frapperButton);
            } else {
                panels[frapperPanel].add(new MyJLabel());
            }
        }
    }

    // ------- Methods to initialize all variables needed to know where to display players' cards and other things ------- //

    /**
     * Initialize the table to know which panel is for which player with 2 players
     */
    private void deuxJoueurs() {
        playerJPanels = new int[2];
        playerJPanels[0] = 13;      // Player 1
        playerJPanels[1] = 11;      // Player 2
        drawAndDiscardJPanel = 12;  // Draw and discard pile
        redJPanel = 6;              // Help button
        messagePanel = 2;           // Message panel
        frapperPanel = 24;          // Frapper panel
        gap = 5;
    }

    /**
     * Initialize the table to know which panel is for which player with 3 players
     */
    private void troisJoueurs() {
        playerJPanels = new int[3];
        playerJPanels[0] = 18;      // Player 1
        playerJPanels[1] = 16;      // Player 2
        playerJPanels[2] = 7;       // Player 3
        drawAndDiscardJPanel = 12;  // Draw and discard pile
        redJPanel = 11;             // Help button
        messagePanel = 2;           // Message panel
        frapperPanel = 24;          // Frapper panel
        gap = 5;
    }

    /**
     * Initialize the table to know which panel is for which player with 4 players
     */
    private void quatreJoueurs() {
        playerJPanels = new int[4];
        playerJPanels[0] = 18;      // Player 1
        playerJPanels[1] = 16;      // Player 2
        playerJPanels[2] = 6;       // Player 3
        playerJPanels[3] = 8;       // Player 4
        drawAndDiscardJPanel = 12;  // Draw and discard pile
        redJPanel = 11;             // Help button
        messagePanel = 2;           // Message panel
        frapperPanel = 24;          // Frapper panel
        gap = 5;
    }

    /**
     * Initialize the table to know which panel is for which player with 5 players
     */
    private void cinqJoueurs() {
        playerJPanels = new int[5];
        playerJPanels[0] = 25;      // Player 1
        playerJPanels[1] = 23;      // Player 2
        playerJPanels[2] = 8;       // Player 3
        playerJPanels[3] = 10;      // Player 4
        playerJPanels[4] = 12;      // Player 5
        drawAndDiscardJPanel = 17;  // Draw and discard pile
        redJPanel = 16;             // Help button
        messagePanel = 3;           // Message panel
        frapperPanel = 34;          // Frapper panel
        gap = 7;
    }

    /**
     * Initialize the table to know which panel is for which player with 6 players
     */
    private void sixJoueurs() {
        playerJPanels = new int[6];
        playerJPanels[0] = 24;      // Player 1
        playerJPanels[1] = 22;      // Player 2
        playerJPanels[2] = 8;       // Player 3
        playerJPanels[3] = 10;      // Player 4
        playerJPanels[4] = 12;      // Player 5
        playerJPanels[5] = 26;      // Player 6
        drawAndDiscardJPanel = 17;  // Draw and discard pile
        redJPanel = 15;             // Help button
        messagePanel = 3;           // Message panel
        frapperPanel = 34;          // Frapper panel
        gap = 7;
    }


    /**
     * Translate coordinates of the card pressed to the coordinates of the card in the table of the player
     * @param y The y coordinate of the card pressed
     * @return The x and y coordinates of the card in the table of the player
     */
    public int[] toTable(int y) {
        int[] tab = new int[2];

        for (int i = 0; i < y; i++) {
            tab[1]++;
            if (tab[1] == Y) {
                tab[1] = 0;
                tab[0] ++;
            }
        }

        return tab;
    }


    /**
     * Translate coordinates of the card in the table of the player to the coordinates of the card in the table of the game
     * @param x The x coordinate of the card in the table of the player
     * @param y The y coordinate of the card in the table of the player
     * @return The y coordinates of the card in the table of the game
     */
    public int toInterface(int x, int y) {
        return y + (x * 3 + x);
    }



    // ------- Methods to display the cards ------- //


    public void printName(String nom, int joueur) {
        namePlayer[joueur].setText(nom);
    }

    /**
     * Display the cards of the player
     * @param p The cards of the player
     * @param joueur The number of the player to know where to display the cards
     */
    public void printCard(Plateau p, int joueur) {
        for (int i = 0; i < X; i++) {
            for (int j = 0; j < Y; j++) {
                // If the card is hidden display the back of the card
                if (p.getCache(i, j) == 0) {
                    display_cards[joueur][toInterface(i, j)].setIcon(new ImageIcon("src\\images\\back.png"));

                // If the card is visible display the front of the card
                } else if (p.getCache(i, j) == 1) {
                    display_cards[joueur][toInterface(i, j)].setIcon(new ImageIcon("src/images/" + p.getCartes(i, j) + ".png"));

                // If the card is removed display a blank card
                } else {
                    display_cards[joueur][toInterface(i, j)].setIcon(new ImageIcon("src\\images\\blank.png"));
                }

                // Then display the card
                display_cards[joueur][toInterface(i, j)].paintComponent(display_cards[joueur][toInterface(i, j)].getGraphics());
            }
        }

    }

    public void printDiscardPile() {
        discardPile.setIcon(new ImageIcon("src/images/" + d.getDefausse().getCredits() + ".png"));
        discardPile.paintComponent(discardPile.getGraphics());
    }

    public void printHiddenDrawPile() {
        hiddenDrawPile.setIcon(new ImageIcon("src/images/back.png"));
        hiddenDrawPile.paintComponent(hiddenDrawPile.getGraphics());
    }

    public void printRevealedDrawPile() {
        revealedDrawPile.setIcon(new ImageIcon("src/images/" + p.getFirstCard().getCredits() + ".png"));
        revealedDrawPile.paintComponent(revealedDrawPile.getGraphics());
    }

    public void eraseRevealedDrawPile() {
        revealedDrawPile.setIcon(new ImageIcon("src/images/nothing.png"));
        revealedDrawPile.paintComponent(revealedDrawPile.getGraphics());
    }

    public void printRedButton() {
        redButton.setIcon(new ImageIcon("src/images/redButton.png"));
        redButton.paintComponent(redButton.getGraphics());
    }

    public void swapFrapperButton(boolean value) {
        frapperButton.setVisible(value);
    }

    public void printDialog(String message) {
        messageBox.setText(message);
        panels[messagePanel].validate();
    }


    // ---------------------- OVERRIDE Methods ---------------------- //

    @Override
    public void actionPerformed(ActionEvent e) {
        JDialog ask = new JDialog();
        String[] numberPossible = {"1", "2", "3", "4", "5"};
        JComboBox player = new JComboBox(numberPossible);
        ask.getContentPane().add(player);
        ask.setSize(800,800);
        ask.setAlwaysOnTop(true);
        ask.setLocationRelativeTo(null);
        ask.setModal(true);
        ask.setVisible(true);
    }

    // Click check in the JPanel
    // The manipulation will always start with a click on the draw pile or on the discard pile
    // And it will always end with a click on the player's cards
    @Override
    public void mouseClicked(MouseEvent e) {

        // If the player clicks on his board we check which card he clicked on
        if (e.getSource() != hiddenDrawPile && e.getSource() != discardPile) {
            if (action == 1 || action == 2 || action == 3) {
                for (int j = 0; j < display_cards[0].length; j++) {
                    if (e.getSource() == display_cards[0][j]) {
                        playerCard = true;
                        setCardSelected(toTable(j));
                    }
                }
                for (int i = 0; i < display_cards[1].length; i++) {
                    if (e.getSource() == display_cards[1][i]) {
                        if (i == 3 || i == 7 || i == 11) {
                            playerCard = false;
                            setCardSelected(toTable(i));
                        }
                    }
                }
            } else if (action == 4) {
                for (int j = 0; j < display_cards[0].length; j++) {
                    if (e.getSource() == display_cards[0][j]) {
                        setCardSelected(toTable(j));
                    }
                }
            }

        // If the player clicks on the draw pile we just send message and set the action
        } else if (e.getSource() == hiddenDrawPile) {
            if (action != 4) {

                // Set the action to 1
                setAction(1);

                // Display information about the action
                printDialog("Veuillez cliquer sur une de vos cartes pour l'échanger ou sur la défausse pour la défausser");

                // Display the drawn card
                printRevealedDrawPile();

                swapFrapperButton(true);
            }

        // If the player clicks on the discard pile we check if he can draw a card
        } else if (e.getSource() == discardPile) {

            // If the player click on the discard Pile after clicking on the draw pile we wait that he select a card in his board
            if (action == 1) {
                setAction(3);
                printDialog("Veuillez cliquer sur une de vos cartes pour la retourner");
            } else if (action == 0 && d.getDefausse().getCredits() != -2) {
                setAction(2);
                printDialog("Veuillez cliquer sur une de vos cartes à échanger");
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void paintComponent(Graphics g) {
        frame.setSize(jeuWidth, jeuHeight);
    }


    // ---------------------- Subclass ---------------------- //

    /**
     * A subclass of JLabel to add a method to paint the component and resize the image
     */
    static class MyJLabel extends JLabel {


        // ---------------------- Attributes ---------------------- //

        ImageIcon imageIcon;


        // ---------------------- Constructor ---------------------- //

        public MyJLabel() {
            super();
            this.imageIcon = null;
        }


        // ---------------------- ACCESS Methods ---------------------- //

        public void setIcon(ImageIcon imageIcon) {
            this.imageIcon = imageIcon;
        }


        // ---------------------- OVERRIDE Methods ---------------------- //

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (this.imageIcon != null) {
                g.drawImage(imageIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
}
