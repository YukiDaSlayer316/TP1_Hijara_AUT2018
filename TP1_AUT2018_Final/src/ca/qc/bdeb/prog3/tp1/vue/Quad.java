/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.prog3.tp1.vue;

import ca.qc.bdeb.prog3.tp1.modele.Case;
import ca.qc.bdeb.prog3.tp1.modele.Joueur;
import ca.qc.bdeb.prog3.tp1.modele.Modele;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 *
 * @author 1749637
 */
public class Quad extends JPanel implements Observer {

    private int colonneQuad, ligneQuad;

    
    public static final int NB_COLONNES_BOUTONS = 2,

    
    NB_LIGNES_BOUTONS = 2;

    private Bouton[] listeIntermediaire = new Bouton[NB_COLONNES_BOUTONS * NB_LIGNES_BOUTONS];

    Modele modele;

    /**
     *constructeur du panneau (le carré qui a les 4 boutons)
     * @param modele le modele utilisé
     * @param colonneQuad la position en x que le quad occupe dans le plateau
     * @param ligneQuad la position en y que le quad occupe dans le plateau
     */
    public Quad(Modele modele, int colonneQuad, int ligneQuad) {
        this.modele = modele;
        modele.addObserver(this);

        this.colonneQuad = colonneQuad;
        this.ligneQuad = ligneQuad;

        this.setLayout(new GridLayout(NB_COLONNES_BOUTONS, NB_LIGNES_BOUTONS));

        initialiserBoutons();

        setPreferredSize(new Dimension(100, 100));
        setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.WHITE));

        setVisible(true);

    }

    /**méthode  initialise chaque boutons du carré
     * qui créé met tous les boutons dans un tableau et ensuite dans un panneau pour pouvoir les modifier après **/
    private void initialiserBoutons() {

        for (int i = 0; i < listeIntermediaire.length; i++) {
            Bouton btn = new Bouton((i + 1) + "");
            listeIntermediaire[i] = btn;
            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Bouton btnClique = (Bouton) e.getSource();
                    modele.setPositions(colonneQuad, ligneQuad, btnClique.getNumero());

                }
            });
        }

        listeIntermediaire[0].setEnabled(true);

        add(listeIntermediaire[1]);
        add(listeIntermediaire[2]);
        add(listeIntermediaire[0]);
        add(listeIntermediaire[3]);
    }

    @Override
    public void update(Observable o, Object arg) {
        //majBoutons();
    }

    /**
     *méthode qui change l'état de chaque bouton (couleur et s'il est jouable ou non) après le tour de chaque joueur
     */
    public void majBoutons() {
        int tailleCarree = (((modele.getPlateau())[colonneQuad][ligneQuad]).getCarree()).length;
        for (int i = 0; i < tailleCarree; i++) {
            Case cettecase = (((modele.getPlateau())[colonneQuad][ligneQuad]).getCarree())[i];
            Joueur joueur = (((modele.getPlateau())[colonneQuad][ligneQuad]).getCarree())[i].getJoueurAssocie();

            if (joueur != null) {
                listeIntermediaire[i].setBackground(joueur.getCouleur().getColor());
                listeIntermediaire[i].setEnabled(false);
                try {
                    listeIntermediaire[i + 1].setEnabled(true);
                } catch (ArrayIndexOutOfBoundsException e) {

               }
            }else{
                //quand je reinitialise
                listeIntermediaire[i].setBackground(null);
                boolean estCliquable=modele.getPlateau()[colonneQuad][ligneQuad].getCarree()[i].isEstCliquable();
                listeIntermediaire[i].setEnabled(estCliquable);                
            }

        }

    }

}
