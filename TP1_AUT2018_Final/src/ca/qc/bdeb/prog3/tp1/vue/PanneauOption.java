/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.prog3.tp1.vue;

import ca.qc.bdeb.prog3.tp1.modele.Couleur;
import ca.qc.bdeb.prog3.tp1.modele.Joueur;
import ca.qc.bdeb.prog3.tp1.modele.Modele;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * l'interface qui créé la fenêtre lorsque le joueur veut changer sa couleur
 *
 * @author 1749637
 */
public class PanneauOption extends JFrame implements Observer {

    private ButtonGroup groupeBoutons = new ButtonGroup();
    private JPanel pnlInfosAutresJoueurs;
    private JPanel pnlJoueurActuel = new JPanel();
    private JButton btnEnregistrer = new JButton("Enregistrer la couleur");
    private JLabel lblInfosAutresJoueurs = new JLabel("Voici les infos de l'autre joueur:");
    private JLabel lblVosInfos = new JLabel("Voici vos informations :");
//            JLabel lblVotreCouleur=new JLabel("Vous avez la couleur ");
    private JLabel lblCouleurJoueurActuel = new JLabel();

    private JComboBox cboBoite;

    private ArrayList<Joueur> listeJoueurs;
    //private Couleur[] tabCouleursDisponibles;
    private int posJoueurActuel;
    private Joueur joueurActuel;

    Modele modele;

    /**
     * constructeur de la fenêtre panneauoption
     *
     * @param modele le jeu complet
     */
    public PanneauOption(Modele modele) {
        this.modele = modele;
        modele.addObserver(this);

        setTitle("Changer la couleur de votre pierre");
        setSize(400, 200);

        this.setLayout(new BorderLayout());

        listeJoueurs = modele.getListeJoueurs();
        posJoueurActuel = modele.getPosJoueurActuel();
        joueurActuel = listeJoueurs.get(posJoueurActuel);
        pnlInfosAutresJoueurs = new JPanel(new GridLayout(listeJoueurs.size(), 1));
        initialiserListeCouleurDisponible();
        creerInterface();
        creerEvenement();

        setVisible(true);

    }

    /**
     * méthode qui créé l'interface de la fenêtre*
     */
    private void creerInterface() {
        creerPanneauInfosAutresJoueur();

        creerPanneauJoueurActuel();

        add(pnlInfosAutresJoueurs, BorderLayout.NORTH);
        add(pnlJoueurActuel, BorderLayout.CENTER);
        add(btnEnregistrer, BorderLayout.SOUTH);

    }

    @Override
    public void update(Observable o, Object o1) {

    }

    /**
     * méthode qui créé le panneau avec la couleur des autres joueurs*
     */
    private void creerPanneauInfosAutresJoueur() {
        pnlInfosAutresJoueurs.add(lblInfosAutresJoueurs);
        for (int i = 0; i < listeJoueurs.size(); i++) {

            if ((i != posJoueurActuel)) {
                Joueur joueur = listeJoueurs.get(i);
                JLabel lblNomAutresJoueurs = new JLabel("Le joueur " + (i + 1) + " a la couleur ");
                JLabel lblCouleurAutresJoueurs = new JLabel(joueur.getCouleur().getNom().toLowerCase());

                lblCouleurAutresJoueurs.setForeground(joueur.getCouleur().getColor());

                JPanel pnlJoueur = new JPanel();

                pnlJoueur.add(lblNomAutresJoueurs);
                pnlJoueur.add(lblCouleurAutresJoueurs);

                pnlInfosAutresJoueurs.add(pnlJoueur);
            }

        }
    }

    /**
     * méthode qui créé le panneau avec les informations du joueur actuel*
     */
    private void creerPanneauJoueurActuel() {
        pnlJoueurActuel.add(lblVosInfos);

        JPanel pnlInfosJoueurActuel = new JPanel();
        //creerCboBox();
        //cboBoite = new JComboBox(listeCouleursDisponibles);
        cboBoite.setPreferredSize(new Dimension(50, 20));
        JLabel lblNomJoueurActuel = new JLabel("Vous êtes le joueur " + (posJoueurActuel + 1) + " ");
        JLabel lblVotreCouleur = new JLabel("Vous avez la couleur ");
        JLabel lblChangezCouleur = new JLabel("Changez votre couleur à ");
        JPanel pnlChangezCouleur = new JPanel();

        lblCouleurJoueurActuel.setText(joueurActuel.getCouleur().getNom().toLowerCase());
        lblCouleurJoueurActuel.setForeground(joueurActuel.getCouleur().getColor());

        pnlInfosJoueurActuel.add(lblNomJoueurActuel);
        pnlInfosJoueurActuel.add(lblVotreCouleur);
        pnlInfosJoueurActuel.add(lblCouleurJoueurActuel);
        pnlChangezCouleur.add(lblChangezCouleur);
        pnlChangezCouleur.add(cboBoite);
        pnlJoueurActuel.add(pnlChangezCouleur);
        pnlJoueurActuel.add(pnlInfosJoueurActuel);

    }

    /**
     * méthode qui attribue la nouvelle couleur choisie par le joueur lorsqu'il
     * enregistre sa couleur*
     */
    private void creerEvenement() {
        btnEnregistrer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Couleur valeurSelectionnee = (Couleur) cboBoite.getSelectedItem();
                modele.setCouleurJoueurACtuel(valeurSelectionnee);

                JOptionPane.showMessageDialog(PanneauOption.this, "Vous avez changer votre couleur");
                dispose();
            }
        });

    }

    /**
     * méthode qui met les couleurs que le joueur peut choisir dans le comboBox*
     */
    private void initialiserListeCouleurDisponible() {
        ArrayList<Couleur> listeCouleurs = new ArrayList<>();
                ArrayList<String> listeNomCouleurs = new ArrayList<>();

        int nbCouleurs = Couleur.values().length;

        //initialiser couleur disponible
        for (int i = 0; i < nbCouleurs; i++) {
            listeCouleurs.add(Couleur.values()[i]);
        }

        //enlever couleurs non disponible
        for (int i = 0; i < listeJoueurs.size(); i++) {
            for (int j = 0; j < listeCouleurs.size(); j++) {
                if (listeJoueurs.get(i).getCouleur().equals(listeCouleurs.get(j))) {
                    listeCouleurs.remove(j);
                }
            }
        }
        
        for (int i = 0; i < listeCouleurs.size(); i++) {
            listeNomCouleurs.add(listeCouleurs.get(i).getNom());
        }
        

        cboBoite = new JComboBox(listeNomCouleurs.toArray());
    }

}
