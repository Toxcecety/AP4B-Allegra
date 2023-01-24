import java.util.Arrays;
import java.util.Random;

public class Pioche {


    // ---------------------- ATTRIBUTES ---------------------- //

    private static final int total = 120;               // Total number of cards in the draw pile
    private final Carte[] cartes = new Carte[total];    // Array of cards in the draw pile
    private int index = 0;                              // Index of the next card to draw


    // ---------------------- CONSTRUCTOR ---------------------- //

    /**
     * Default constructor create draw pile with random card
     */
    public Pioche() {
        // Create the draw pile with random cards
        Random rand = new Random();
        int randInt, i = index;
        while (i < total) {

            // Take a random number between -1 and 11 included
            randInt = rand.nextInt(-1, 12);

            // Check if the number of cards of this type is correct
            if (check_carte(randInt, i)) {

                // Create the card
                Carte toInsert = new Carte(randInt);

                // Insert the card in the draw pile
                cartes[i] = toInsert;
                i++;
            }
        }
    }

    // ---------------------- ACCESS Methods ---------------------- //

    /**
     * Access to the current card in the draw pile
     * @return the current card in the draw pile
     */
    public Carte getFirstCard() {
        return cartes[index];
    }


    // ---------------------- OTHER Methods ---------------------- //

    /**
     * Draw a card from the draw pile
     * @return the card drawn
     */
    public Carte piocher_carte() {
        // Take the card at the index
        Carte returned = cartes[index];

        // Increment the index
        index++;

        // Return the card
        return returned;
    }

    /**
     * Check if there are enought card of the card passed by parameter
     * @param carte the card to check
     * @param fin index of the last card in the draw pile
     * @return true if the card can be inserted and false if not
     */
    private boolean check_carte(int carte, int fin) {
        // Check if the number of cards of this type is correct

        // Count the number of card of this type
        int nb = nb_carte(carte, fin);

        // And check if it's correct
        switch (carte) {
            case -1, 0, 1, 2, 3 -> {
                if (nb >= 8)
                    return false;
            }
            case 4, 5, 6, 7 -> {
                if (nb >= 11)
                    return false;
            }
            case 8, 9, 10, 11 -> {
                if (nb >= 9)
                    return false;
            }
        }
        return true;
    }

    /**
     * Count the number of card of the type passed by parameter
     * @param carte card to check into the draw pile
     * @param fin index of the last card in the draw pile
     * @return the number of card
     */
    private int nb_carte(int carte, int fin) {
        int nb = 0;

        // Count the number of card of this type
        for (int i = index; i < fin; i++) {
            if (carte == cartes[i].getCredits()) {
                nb++;
            }
        }

        // Return the number of card
        return nb;
    }

    /**
     * Print card in draw pile
     * @param entier if true, print all card if not, print all card remaining
     */
    public void printCartes(boolean entier) {

        // Print all card
        if (entier) {
            System.out.print(this);

        // Print all card remaining
        } else {
            StringBuilder str = new StringBuilder();
            str.append('[');
            for (int i = index; i < total; i++) {
                str.append(cartes[i]);
                if ((i+1) != total) {
                    str.append(" ,");
                }
            }
            str.append(']');
            System.out.print(str);
        }
        System.out.println();
    }


    // ---------------------- OVERRIDE Methods ---------------------- //

    /**
     * Print the draw pile
     * @return a string with all card in the draw pile
     */
    @Override
    public String toString() {
        return Arrays.toString(cartes);
    }
}