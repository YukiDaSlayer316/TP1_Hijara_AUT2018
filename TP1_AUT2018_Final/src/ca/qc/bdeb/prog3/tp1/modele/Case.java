/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.prog3.tp1.modele;

/**
 *classe qui gère une case (boutons)
 * @author emuli
 */
public class Case {
    
    private final int numeroCase;
    private Joueur joueurAssocie=null;
    private boolean estCliquable=false;
    
    /**
     *
     * @param numeroCase
     */
    public Case(int numeroCase){
        this.numeroCase=numeroCase;       
                
    }

    /**
     *
     * @return
     */
    public Joueur getJoueurAssocie() {
        return joueurAssocie;
    }

    /**
     *
     * @param joueurAssocie
     */
    public void setJoueurAssocie(Joueur joueurAssocie) {
        this.joueurAssocie = joueurAssocie;
    }

    /**
     *
     */
    public void desactiverBouton() {
        this.estCliquable = false;
    }

    /**
     *
     * @return
     */
    public boolean isEstCliquable() {
        return estCliquable;
    }

    /**
     *
     * @return
     */
    public int getNumeroCase() {
        return numeroCase;
    }

    /**
     *
     */
    public void activerBouton() {
        this.estCliquable = true;
    }
    
    /**
     *
     */
    public void reinitialiserJoueur() {
        this.joueurAssocie = null;
    }
   
    /**
     *
     */
    public void reinitialiserCase() {
        this.estCliquable = false;
        this.joueurAssocie=null;
    }
    
    
}
