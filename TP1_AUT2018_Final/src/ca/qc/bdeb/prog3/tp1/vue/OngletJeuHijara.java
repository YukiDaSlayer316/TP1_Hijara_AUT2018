/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.prog3.tp1.vue;

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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *classe qui affiche le jeu complet(les points des joueurs, le plateau de jeu, le temps de jeu,...)
 * @author 1749637
 */
public class OngletJeuHijara extends JPanel implements Observer {

    private Modele modele;
    private int positionOnglet;
    //private int nbSecondesEcoules = 0;

    public static final int NB_COLONNES_PLATEAU = 4,

  
    NB_LIGNES_PLATEAU = 4;

    private Quad[][] plateau = new Quad[NB_COLONNES_PLATEAU][NB_LIGNES_PLATEAU];

    private ArrayList<BarreJoueur> listePanneauxJoueurs = new ArrayList<BarreJoueur>();

    private JPanel pnlPlateau = new JPanel(new GridLayout(NB_COLONNES_PLATEAU, NB_LIGNES_PLATEAU));

    private JPanel pnlPrincipal = new JPanel(new BorderLayout());
    private JLabel lblTimer = new JLabel("0 minutes 0 secondes");
    private JLabel lblDureeJeu = new JLabel("Durée de jeu: ");
    private JPanel pnlTimer = new JPanel();

    private JLabel lblTourJoueur = new JLabel("Tour du joueur 1");

    Timer timer = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            modele.incrementerSecondesEcoulees();
            //nbSecondesEcoules++;
            int nbMinutes = modele.getNbMinutes();
            int nbSecondes = modele.getNbSecondes();

            lblTimer.setText(nbMinutes + " minutes " + nbSecondes + " secondes");
        }
    });

    /**
     *constructeur qui créé l'interface du plateau de jeu
     * @param modele le modele utilisé
     * @param positionOnglet la position de l'onglet sur lequel le jeu se situe
     */
    public OngletJeuHijara(Modele modele, int positionOnglet) {
        this.modele = modele;
        modele.addObserver(this);

        this.setLayout(new BorderLayout());

        setPreferredSize(new Dimension(200, 200));

        this.positionOnglet = positionOnglet;

        timer.start();
        initialiserPanneauxJoueurs();
        initialiserPlateau();
        creerPanneau();

        setVisible(true);

    }

    @Override
    public void update(Observable o, Object arg) {
        majPointsJoueur();
        majTourJoueur();
        majBackgroundBoutons();
        majGagnant();
    }

    /**méthode qui initialise le panneau du plateau**/
    private void initialiserPlateau() {
        for (int i = 0; i < NB_COLONNES_PLATEAU; i++) {
            for (int j = 0; j < NB_LIGNES_PLATEAU; j++) {
                Quad quad = new Quad(modele, i, j);
                plateau[i][j] = quad;
                pnlPlateau.add(quad);
            }
        }
    }

    /**méthode qui ajoute le panneau du plateau et des barres de points des joueurs **/
    private void creerPanneau() {
        pnlPrincipal.add(listePanneauxJoueurs.get(0), BorderLayout.NORTH);
        pnlPrincipal.add(pnlPlateau, BorderLayout.CENTER);
        pnlPrincipal.add(listePanneauxJoueurs.get(1), BorderLayout.SOUTH);

        pnlTimer.add(lblDureeJeu);
        pnlTimer.add(lblTimer);

        add(lblTourJoueur, BorderLayout.NORTH);
        add(pnlPrincipal, BorderLayout.CENTER);
        add(pnlTimer, BorderLayout.SOUTH);

    }

    /**méthode qui créé le panneaux de chaque joueur**/
    private void initialiserPanneauxJoueurs() {
        int nbJoueurs = modele.getListeJoueurs().size();
        for (int i = 0; i < nbJoueurs; i++) {
            BarreJoueur pnlBarreJoueur = new BarreJoueur(modele, i);
            listePanneauxJoueurs.add(pnlBarreJoueur);
        }
    }

    /**méthode qui met à jours chaque bouton à la fin de chaque tour**/
    private void majBackgroundBoutons() {
        for (int i = 0; i < NB_COLONNES_PLATEAU; i++) {
            for (int j = 0; j < NB_LIGNES_PLATEAU; j++) {
                plateau[i][j].majBoutons();
            }
        }

    }

    /**méthode qui met à jours les points de chaque joueurs après qu'un a joué**/
    private void majPointsJoueur() {
        for (int i = 0; i < listePanneauxJoueurs.size(); i++) {
            listePanneauxJoueurs.get(i).majPointsJoueur();
        }
    }

    /**méthode qui dit lequel des joueurs doit jouer**/
    private void majTourJoueur() {
        int posJoueurSuivant = (modele.getPosJoueurActuel() + 1);
        lblTourJoueur.setText("Tour du joueur " + posJoueurSuivant);        
    }

    
    /**méthode qui affiche le gagnant lorsque la partie est terminée **/
    private void majGagnant() {
        if (modele.isEstPlateauRempli()) {
            int posJoueurGagnant = modele.determinerposJoueurGagnant();
            
            try{
                int nbPoints=modele.getListeJoueurs().get(posJoueurGagnant).getNbPoints();
                                JOptionPane.showMessageDialog(OngletJeuHijara.this, "Le joueur " + (posJoueurGagnant + 1)+" a gagné avec " +nbPoints+" points");

            }catch(ArrayIndexOutOfBoundsException e){
                JOptionPane.showMessageDialog(OngletJeuHijara.this, "Égalité");
            }
            int choix = JOptionPane.showConfirmDialog(OngletJeuHijara.this, "Voulez-vous rejouer?");
                if (choix == JOptionPane.YES_OPTION) {
                    modele.reinitialiser();
                }else{
                    System.exit(0);
                }
        } 
            }
        
    }

