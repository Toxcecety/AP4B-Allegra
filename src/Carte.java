public class Carte {

    // ---------------------- ATTRIBUTES ---------------------- //

    private int credits; // Number of credits on the card


    // ---------------------- CONSTRUCTOR ---------------------- //

    // Constructor
    public Carte() {
        this.credits = -2;
    }

    // Constructor
    public Carte(int nb) {
        this.credits = nb;
    }


    // ---------------------- ACCESS Methods ---------------------- //

    // Get the number of credits on the card
    public int getCredits() {
        return credits;
    }

    // Set the number of credits on the card
    public void setCredits(int credits) {
        this.credits = credits;
    }


    // ---------------------- OVERRIDE Methods ---------------------- //

    @Override
    public String toString() {
        return "" + credits;
    }
}
