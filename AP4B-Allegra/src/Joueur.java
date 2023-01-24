public class Joueur {


    // ---------------------- ATTRIBUTES ---------------------- //

    private final String nom;       // Player's name
    private int score;              // Player's score [number of rounds won]
    private final Plateau plateau;  // Player's board


    // ---------------------- CONSTRUCTOR ---------------------- //

    /**
     * Constuctor with a given name and a draw pile to make his game
     * @param nom name of the player
     * @param p Draw pile
     */
    public Joueur(String nom, Pioche p) {
        this.nom = nom;
        this.score = 0;
        this.plateau = new Plateau(p);
    }


    // ---------------------- ACCESS Methods ---------------------- //

    /**
     * Access to the name of the player
     * @return name of the player
     */
    public String getNom() {
        return nom;
    }

    /**
     * Access to the score of the player
     * @return score of the player
     */
    public int getScore() {
        return score;
    }

    /**
     * Access to the game of the player
     * @return game of the player
     */
    public Plateau getPlateau() {
        return plateau;
    }

    /**
     * Increment score of the player when he wins
     */
    public void setScore(int score) {
        this.score = score;
    }


    // ---------------------- OTHER Methods ---------------------- //

    /**
     * Calculates the score of the player for that round
     * @param joueur_suivant We also need the player on the left's cards
     * @return the total score of the player
     */
    public int roundScore(Joueur joueur_suivant) {
        int score = 0;

        for (int i = 0; i < 3; i++) {
            if(joueur_suivant.plateau.getCache(i,3) != 2){
                score += joueur_suivant.plateau.getCartes(i, 3);
            }

            for (int j = 0; j < 4; j++) {
                if(plateau.getCache(i,j) != 2){
                    score += plateau.getCartes(i,j);
                }
            }
        }
        return score;
    }

    /**
     * Checks if there are three aligned identical cards, if it is the case they are removed from the player's "plateau"
     */
    public void cartesAllign(Joueur joueur_suivant) {
        //We check the columns first
        for (int i = 0; i < 4; i++){
            if (plateau.isRetourner(0,i) && plateau.isRetourner(1, i) && plateau.isRetourner(2, i)){
                if (plateau.getCartes(0, i) == plateau.getCartes(1, i) && plateau.getCartes(1, i) == plateau.getCartes(2, i)){
                    plateau.retirer(0, i);
                    plateau.retirer(1, i);
                    plateau.retirer(2, i);
                }
            }
        }

        //We check the rows next [the ones with shared card with the other user]
        for (int i = 0; i < 3; i++){
            if (joueur_suivant.plateau.isRetourner(i,3) && plateau.isRetourner(i, 0) && plateau.isRetourner(i, 1)) {
                if (joueur_suivant.plateau.getCartes(i,3) == plateau.getCartes(i, 0) && plateau.getCartes(i, 0) == plateau.getCartes(i, 1)){
                    joueur_suivant.plateau.retirer(i,3);
                    plateau.retirer(i, 0);
                    plateau.retirer(i, 1);
                }
            }
        }

        //We check the other rows
        for (int i = 0; i < 2; i++){
            for (int j = 0; j < 3; j++) {
                if (plateau.isRetourner(j, i) && plateau.isRetourner(j, i+1) && plateau.isRetourner(j, i+2)){
                    if (plateau.getCartes(j, i) == plateau.getCartes(j, i+1) && plateau.getCartes(j, i+1) == plateau.getCartes(j, i+2)){
                        plateau.retirer(j, i);
                        plateau.retirer(j, i+1);
                        plateau.retirer(j, i+2);
                    }
                }
            }
        }
    }
}
