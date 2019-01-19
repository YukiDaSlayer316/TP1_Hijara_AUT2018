/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.prog3.tp1.modele;

/**
 * classe qui gère un carrée
 * @author emuli
 */
public class Carree {

    /**
     *
     */
    public final static int NB_COLONNES_CARREE = 2,

    /**
     *
     */
    NB_LIGNES_CARREE = 2;
    
    /**
     *
     */
    public static final int NB_BOUTONS=NB_COLONNES_CARREE * NB_LIGNES_CARREE;

    private Case[] carree = new Case[NB_BOUTONS];

    private static int nbPierres = 0;
    private int colonne, ligne;

    /**
     *constructeur d'un carré avec les 4 boutons dans le plateau
     * @param colonne la position du carré dans le plateau en x
     * @param ligne la position du carré dans le plateau en y
     */
    public Carree(int colonne, int ligne) {
        this.colonne=colonne;
        this.ligne=ligne;

        initialiserCarree();
    }

    /**
     méthode qui initialise le carree avec ses boutons
     **/
    private void initialiserCarree() {
        for (int i = 0; i < carree.length; i++) {
            Case cetteCase = new Case( (i + 1));
            carree[i]=cetteCase;
        }
        carree[0].activerBouton();
    }

    /**    
     * @return retourne un carrée du plateau
     */
    public Case[] getCarree() {
        return carree;
    }

    /**
     *méthode qui  active et désactive les boutons disponibles selon le nombre de pierres posées 
     */
    public void activerDesactiverBoutonDisponible() {
        for (int i = 0; i < carree.length; i++) {
            //            if (carree[i].getNumeroCase() == nbPierres + 1) {

            if (carree[i].getNumeroCase() == nbPierres ) {
                carree[i].activerBouton();
            } else {
                carree[i].desactiverBouton();
            }
        }
    }

    /**
     *méthode qui incrémente le nombre de pierres à chaque fois qu'un utilisateur 
     * en dépose une
     */
    public void incrementerNbPierres() {
        Carree.nbPierres++;
    }

    /**
     *méthode qui réinitialise le carrée lorsque le joueur veut rejouer une partie
     */
    public void reintialiserCarree() {
        for (int i = 0; i < carree.length; i++) {
            carree[i].reinitialiserCase();
            nbPierres = 0;
            //reinitialiserBoutonDisponible();
        }
        carree[0].activerBouton();
    }

    /**
     * @return la position du carré dans le plateau en x
     */
    public int getColonne() {
        return colonne;
    }

    /**
     * @return la position du carré dans le plateau en y
     */
    public int getLigne() {
        return ligne;
    }

    /**
     * @return le nombre de boutons dans un carrée
     */
    public static int getNB_BOUTONS() {
        return NB_BOUTONS;
    }

    
    
    
    

}
