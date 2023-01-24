
public class Plateau implements Base {

    // ---------------------- ATTRIBUTES ---------------------- //
    private final Carte[][] cartes = new Carte[X][Y];   // Array of cards on the board
    private final int[][] cache = new int[X][Y];        // 0 -> hidden / 1 -> revealed / 2 -> no card


    // ---------------------- CONSTRUCTOR ---------------------- //

    /**
     * Constructor create a board with the cards passed by parameter
     * @param p The draw pile to take the cards from
     */
    public Plateau(Pioche p) {

        // For each card on the board
        for (int i = 0; i < X; i++) {
            for (int j = 0; j < Y; j++) {

                // Take a card from the draw pile
                cartes[i][j] = p.piocher_carte();
                cache[i][j] = 0;
            }
        }
    }

    // ---------------------- ACCESS Methods ---------------------- //

    /**
     * Access to the state of the card at the position passed by parameter
     * @param x X position of the card
     * @param y Y position of the card
     * @return the state of the card
     */
    public int getCache(int x, int y) {
        return cache[x][y];
    }

    /**
     * Access to the card at the position passed by parameter
     * @param x X position of the card
     * @param y Y position of the card
     * @return the card's credits at the position
     */
    public int getCartes(int x, int y) {
        return cartes[x][y].getCredits();
    }

    /**
     * Returns the card depending on its position [the name needs to be changes]
     * @param x X position of the card
     * @param y Y position of the card
     * @return the card at this specific position
     */
    public Carte getCarte(int x, int y) {
        return cartes[x][y];
    }

    public void setCarte(Carte newCard, int x, int y) {
        cartes[x][y] = newCard;
    }


    // ---------------------- OTHER Methods ---------------------- //

    /**
     * Method used to reveal card [if said card hasn't been revealed or removed yet
     * @param x x coordinate of the card
     * @param y y coordinate of the card
     */
    public void retourner(int x, int y) {
        if (!this.isRetourner(x, y)) {
            cache[x][y] = 1;
        }
    }

    /**
     * Method used to remove a card
     * @param x x coordinate of the card
     * @param y y coordinate of the card
     */
    public void retirer(int x, int y) {
        if (!this.isRetirer(x, y)) {
            cache[x][y] = 2;
        }
    }

    /**
     * @param x x coordinate of the card
     * @param y y coordinate of the card
     * @return true if the card has been revealed
     */
    protected boolean isRetourner(int x, int y) {
        return cache[x][y] == 1 || cache[x][y] == 2;
    }

    /**
     * Method used to check if a user has removed a card
     * @param x x coordinate of the card
     * @param y y coordinate of the card
     * @return value of the table cache at the position x, y
     */
    protected boolean isRetirer(int x, int y) {
        return cache[x][y] == 2;
    }

    /**
     * Method used to know if all the cards have been revealed or removed
     * @return a boolean 0 -> if they're not all revealed // 1 -> if they are all revealed
     */
    public boolean allRetourner() {
        int i = 0, j = 0;
        boolean notTurned = false;
        while (i < X && !notTurned) {
            while (j < Y && !notTurned) {
                if (cache[i][j] == 0) {
                    notTurned = true;
                }
                j++;
            }
            i++;
            j = 0;
        }
        return !notTurned;
    }


    // ---------------------- OVERRIDE Methods ---------------------- //

    /**
     * Method used to print the board
     * @return a string containing the board
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < X; i++) {
            for (int j = 0; j < Y; j++) {
                //if (isRetourner(i, j)) {
                    str.append(" ").append(cartes[i][j]).append(" ");
                /*} else {
                    str.append(" - ");
                }*/
            }
            str.append("\n");
        }
        return str.toString();
    }
}