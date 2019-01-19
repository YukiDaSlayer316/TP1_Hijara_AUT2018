/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.prog3.tp1.vue;

import ca.qc.bdeb.prog3.tp1.modele.Joueur;
import ca.qc.bdeb.prog3.tp1.modele.Modele;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Observer;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author emuli
 */
public class BarreJoueur extends JPanel implements Observer {

    /**
     *
     */
    public static final int NB_CASES_POINTS = 20;
    //private Joueur joueur;
    private JLabel lblCouleur;
    private int posJoueur;
    private Color colorJoueur;

    private JLabel lblPoints = new JLabel(" points ");
    private JPanel pnlPoints = new JPanel(new GridLayout(1, NB_CASES_POINTS));

    private JLabel[] tabPanneauxPoints = new JLabel[NB_CASES_POINTS];

    Modele modele;

    /**
     *
     * @param modele
     * @param posJoueur
     */
    public BarreJoueur(Modele modele, int posJoueur) {
        this.modele = modele;
        modele.addObserver(this);
        this.posJoueur = posJoueur;
        colorJoueur = modele.getListeJoueurs().get(posJoueur).getCouleur().getColor();

        this.setLayout(new BorderLayout());

        setPreferredSize(new Dimension(50, 25));
        creerPanneauPoints();
        assemblerPaneaux();

    }

    private void creerPanneauPoints() {
        int nombreAffiche = 0;
        for (int i = 0; i < NB_CASES_POINTS; i++) {
            JLabel lbl = new JLabel(nombreAffiche + "");
            tabPanneauxPoints[i] = lbl;
            pnlPoints.add(tabPanneauxPoints[i]);
            nombreAffiche += 5;
        }
        tabPanneauxPoints[0].setForeground(colorJoueur);
    }

    private void assemblerPaneaux() {
        Joueur joueur = modele.getListeJoueurs().get(posJoueur);

        lblCouleur = new JLabel("Joueur " + (joueur.getNumeroJoueur() + "    "));
        lblCouleur.setForeground(joueur.getCouleur().getColor());

        add(lblCouleur, BorderLayout.WEST);
        add(pnlPoints, BorderLayout.CENTER);
        add(lblPoints, BorderLayout.EAST);

    }

    @Override
    public void update(java.util.Observable o, Object arg) {
        majCouleurJoueur();
        majPointsJoueur();
    }

    /**
     *
     */
    public void majPointsJoueur() {
        Color colorJoueur = modele.getListeJoueurs().get(posJoueur).getCouleur().getColor();
        int pointJoueur = (modele.getListeJoueurs()).get(posJoueur).getNbPoints();
        for (int i = 0; i < NB_CASES_POINTS; i++) {
            tabPanneauxPoints[i].setForeground(Color.BLACK);
        }
        int posCaseChange = pointJoueur / 5;
        try {
            tabPanneauxPoints[posCaseChange].setForeground(colorJoueur);
        } catch (ArrayIndexOutOfBoundsException e) {

        }
    }

    private void majCouleurJoueur() {
        colorJoueur = modele.getListeJoueurs().get(posJoueur).getCouleur().getColor();
        lblCouleur.setForeground(colorJoueur);
        
    }

    
       
    
    
}
