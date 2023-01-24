import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JPanel implements Base, ActionListener {

    // ---------------------- ATTRIBUTES ---------------------- //

    private final JComboBox comboBoxNombreJoueurs;
    private JButton buttonStart;
    private int joueurs;


    // ---------------------- CONSTRUCTOR ---------------------- //

    public Menu() {
        setName("menu");
        setSize(menuWidth, menuHeight);
        frame.setLocationRelativeTo(null);
        setLayout(null);

        JLabel labelNombreJoueur = new JLabel(new ImageIcon("src\\Images\\login\\nb_joueur.png"));
        labelNombreJoueur.setBounds(50, 195, 100, 28);
        add(labelNombreJoueur);

        String[] numberPossible = {"2", "3", "4", "5", "6"};
        comboBoxNombreJoueurs = new JComboBox(numberPossible);
        comboBoxNombreJoueurs.setBounds(200, 195, 150, 32);
        add(comboBoxNombreJoueurs);

        buttonStart = new JButton("Commencer");
        buttonStart.setSize(100, 32);
        buttonStart.setLocation((menuWidth/2)-50, 250);
        buttonStart.addActionListener(this);
        add(buttonStart);

        JLabel labelBackground = new JLabel(new ImageIcon("src\\Images\\login\\backgroun.jpg"));
        labelBackground.setBounds(0, 0, 400, 514);
        add(labelBackground);
    }


    // ---------------------- ACCESS Methods ---------------------- //

    public int getJoueurs(){
        return joueurs;
    }

    /**
     * Method used to set the number of players selected
     */
    public void setNbJoueurs(){
        String choixJ = (String)comboBoxNombreJoueurs.getSelectedItem();
        this.joueurs = Integer.parseInt(choixJ);
    }


    // ---------------------- OVERRIDE Methods ---------------------- //

    @Override
    public void actionPerformed(ActionEvent e) {
        // We gather the info regarding the number of players selected
        setSize(menuWidth, menuHeight);
        setNbJoueurs();
    }
}