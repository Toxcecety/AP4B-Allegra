import java.awt.*;

public class Controller implements Base{


    // ---------------------- ATTRIBUTES ---------------------- //

    private int nbJoueurs;                          // Number of players
    private final Menu menu = new Menu();           // Menu Panel
    private final Jeu jeu;                                // Game Panel not initialized yet because we don't know the number of players yet
    private final Fenetre f = new Fenetre(menu);    // Window which will contain the menu and after initialization of the game, the game
    private Pioche p;                               // Draw pile
    private Defausse d;                             // Discard pile
    private Joueur[] listJoueurs;                   // List of all the players
    private int joueur;                             // The current players who's turn it is to play
    //Int to keep track of if a player has turned all of his cards, therefore initialising the last round
    private final int cartesRet = 6; //we initialise on 6 because the last player number is 5
    //Int indicating the winner of the game [you must win 3 rounds to win the game]
    private final int winner = 6; //we initialise on 6 because the last player number is 5


    // ---------------------- CONSTRUCTOR ---------------------- //

    public Controller() {
        // Loop to get the number of players and wait for the game to be ready
        do {
            System.out.print("");
        } while ((nbJoueurs = menu.getJoueurs()) == 0);
        //Initialized the game and added it to the window
        jeu = new Jeu(nbJoueurs);
        f.addPanel(jeu);
        //Changes JPanel to "Jeu"
        CardLayout cl = (CardLayout) (cards.getLayout());
        cl.show(cards, "jeu");
        frame.setSize(jeuWidth, jeuHeight);
        frame.setLocationRelativeTo(null);
        int player;

        //Loop until the 3 rounds are over
        for (int i = 0; i < 3; i++) {
            init();
            jeu.setD(d);
            jeu.setP(p);

            //Every player return these two first cards
            for(int j=0; j<nbJoueurs; j++){
                for(int carte=0; carte<2; carte++) {
                    printGame(j);
                    jeu.setAction(4);

                    while (jeu.getCardSelected()[0] == -1 && jeu.getCardSelected()[1] == -1) {
                        System.out.print("");
                    }
                    if (!listJoueurs[j].getPlateau().isRetourner(jeu.getCardSelected()[0], jeu.getCardSelected()[1])) {
                        listJoueurs[j].getPlateau().retourner(jeu.getCardSelected()[0], jeu.getCardSelected()[1]);
                    } else {
                        carte--;
                    }
                    jeu.setCardSelected(new int[]{-1, -1});
                }
            }

            jeu.setAction(0);

            // We start the round, so we start with the first player
            joueur = 0;
            boolean dernierTour = false;
            int tourJoueur = 0;
            //Each player gets a turn to play. We loop until the round of the game is over [one player has turned all his cards]
            while (tourJoueur < nbJoueurs) {
                jeu.setD(d);
                jeu.setP(p);
                printGame(joueur);

                while (jeu.getAction() == 0 || (jeu.getCardSelected()[0] == -1 && jeu.getCardSelected()[1] == -1)) {
                    System.out.print("");
                }

                if (jeu.getplayerCard()) {
                    player = joueur;
                } else {
                    if (joueur == nbJoueurs-1) {
                        player = 0;
                    } else {
                        player = joueur + 1;
                    }
                }
                //Switch depending on the user's choice of action
                switch (jeu.getAction()) {
                    case 1 -> actPioche(player);
                    case 2 -> actDef(player);
                    case 3 -> actPiocheDef(player);
                    default -> System.out.println("Erreur");
                }
                if (listJoueurs[joueur].getPlateau().allRetourner()) {
                    dernierTour = true;
                }
                //We move on to the next player but also check if there are three identical alligned cards
                if (joueur == nbJoueurs-1) {
                    listJoueurs[joueur].cartesAllign(listJoueurs[0]);
                    joueur = 0;
                } else {
                    listJoueurs[joueur].cartesAllign(listJoueurs[joueur+1]);
                    joueur++;
                }
                //We then reset certain attributes for the next player's turn
                jeu.setAction(0);
                jeu.printDialog("");
                jeu.eraseRevealedDrawPile();
                jeu.setCardSelected(new int[]{-1, -1});
                jeu.swapFrapperButton(false);

                //Check if the player has returned all his cards and launch the last turn of all the others players
                if(dernierTour){
                    tourJoueur ++;
                }
            }
            setScores(joueur);
            for (int j = 0; j < nbJoueurs; j++) {
                if (j == nbJoueurs-1) {
                    System.out.println("Score joueur " + j + " = " + listJoueurs[j].roundScore(listJoueurs[0]));
                } else {
                    System.out.println("Score joueur " + j + " = " + listJoueurs[j].roundScore(listJoueurs[j+1]));
                }
            }
        }
    }


    // ---------------------- OTHER Methods ---------------------- //

    /**
     * Method used to initialise the players and the game once the user has selected the number of players
     */
    private void init() {
        p = new Pioche();
        d = new Defausse();
        //Allocate list size
        listJoueurs = new Joueur[nbJoueurs];
        //Initialising each player
        for (int i = 0; i < nbJoueurs; i++) {
            listJoueurs[i] = new Joueur("Joueur " + (i+1), p);
        }
    }

    /**
     * Displays the different cards for each player depending on who's turn it is
     * @param j Indicates which player is the first to play
     */
    private void printGame(int j) {

        // We display the cards of each player, starting with the active player
        for (int i = 0; i < nbJoueurs; i++) {
            jeu.printCard(this.listJoueurs[j%nbJoueurs].getPlateau(), i);
            jeu.printName(this.listJoueurs[j%nbJoueurs].getNom(), i);
            j++;
        }

        // Print all things needed for the player to play
        jeu.printDiscardPile();
        jeu.printHiddenDrawPile();
        jeu.printRedButton();

        // Update the Panel
        jeu.revalidate();
    }

    /**
     * The user chooses to pick a card from the "Défausse" and exchanges it with one of his cards
     */
    private void actDef(int player) {
        Carte aEnlever = listJoueurs[player].getPlateau().getCarte(jeu.getCardSelected()[0], jeu.getCardSelected()[1]);
        Carte aAjouter = d.getDefausse();
        d.setDefausse(aEnlever);
        listJoueurs[player].getPlateau().setCarte(aAjouter, jeu.getCardSelected()[0], jeu.getCardSelected()[1]);
        listJoueurs[player].getPlateau().retourner(jeu.getCardSelected()[0], jeu.getCardSelected()[1]);
    }

    /**
     * The user reveals the card from the draw pile but then discards it. Therefore revealing a card from his "plateau"
     */
    private void actPiocheDef(int player){
        d.setDefausse(p.piocher_carte());
        listJoueurs[player].getPlateau().retourner(jeu.getCardSelected()[0], jeu.getCardSelected()[1]);
    }

    /**
     * The user reveals the card from the draw pile and then exchanges it with one of his cards
     */
    private void actPioche(int player){
        Carte aEnlever = listJoueurs[player].getPlateau().getCarte(jeu.getCardSelected()[0], jeu.getCardSelected()[1]);
        d.setDefausse(aEnlever);
        Carte aAjouter = p.piocher_carte();
        listJoueurs[player].getPlateau().setCarte(aAjouter, jeu.getCardSelected()[0], jeu.getCardSelected()[1]);
        listJoueurs[player].getPlateau().retourner(jeu.getCardSelected()[0], jeu.getCardSelected()[1]);
    }

    /**
     * Compares everyone's number of credits at the end of the round of the game to find which player has the lowest score
     */
    private int playerLowestScore() {
        int joueur_min = 6, score=1000, score_temp;

        for (int i = 0; i < nbJoueurs; i++) {
            if (i == (nbJoueurs-1)) {
                score_temp = listJoueurs[i].roundScore(listJoueurs[0]);
            } else {
                score_temp = listJoueurs[i].roundScore(listJoueurs[i+1]);
            }
            if (score > score_temp) {
                score = score_temp;
                joueur_min = i;
            }
        }
        return joueur_min;
    }

    /**
     * Indicate if there is a player that has the same number of points as the player with the least
     * @return True if yes, False if not
     */
    private boolean shareLowestScore() {
        int playerScoreMin = playerLowestScore();
        int scoreMin = listJoueurs[playerScoreMin].getScore();

        for (int i = 0; i < nbJoueurs; i++){
            if((listJoueurs[i].getScore() == scoreMin) && (playerScoreMin != i)){
                return true;
            }
        }
        return false;
    }

    /**
     * The function sets the score at the end of the round. If the player who turned over all his cards first has the highest score, his score for that round is doubled
     * @param joueurFirstFinish the first player to finish turning his cards
     */
    private void setScores(int joueurFirstFinish) {
        int playerScoreMin = playerLowestScore();

        for (int i = 0; i < (nbJoueurs-1); i++) {
            listJoueurs[i].setScore(listJoueurs[i].roundScore(listJoueurs[i+1]));
        }
        listJoueurs[nbJoueurs-1].setScore(listJoueurs[nbJoueurs-1].roundScore(listJoueurs[0]));

        if ((playerScoreMin != joueurFirstFinish) || shareLowestScore()) {
            listJoueurs[joueurFirstFinish].setScore(2*listJoueurs[joueurFirstFinish].getScore());
        }
    }
}
