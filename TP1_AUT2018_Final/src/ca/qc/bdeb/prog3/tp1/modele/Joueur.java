/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.prog3.tp1.modele;

import java.awt.Color;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

/**
 * classe qui gère les joueurs
 * @author 1749637
 */
public class Joueur {
    private int numeroJoueur;
    private int nbPoints = 0;
    private Couleur couleur;

    /**
     * @param posJoueur position du joueur dans la liste 
     * @param couleur la couleur de la pierre du joueur
     **/
    public Joueur(int posJoueur,Couleur couleur) {
        this.numeroJoueur=posJoueur+1;
        this.couleur = couleur;
    }

    /**
     * @return nombre de points du joueurs
     */
    public int getNbPoints() {
        return nbPoints;
    }

    /**
     * @param nbPoints le nombre de points que le joueur a selon l'emplacement de ses pierres
     */
    public void setNbPoints(int nbPoints) {
        this.nbPoints = nbPoints;
    }
    
    /**
     * @return la couleur de la pieere du joueur
     */
    public Couleur getCouleur() {
        return couleur;
    }

    /**
     *
     * @return le numero du joueur
     */
    public int getNumeroJoueur() {
        return numeroJoueur;
    }

    /**
     *méthode qui change la couleur du joueur
     * @param couleur
     */
    public void setCouleur(Couleur couleur) {
        this.couleur = couleur;                   
    }
     
    
    /**
     * méthode appelée lorsque le joueur veut recommencer une partie
     * @param couleur couleur de la pierre du joueur
     **/
    public void reinitialiserJoueur(Couleur couleur) {
        this.nbPoints = 0;
        this.couleur=couleur;
    }
}
