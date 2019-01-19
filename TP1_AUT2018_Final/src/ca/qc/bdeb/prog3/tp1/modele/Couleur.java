/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.prog3.tp1.modele;

import java.awt.Color;

/**
 * enum qui contient tous les choix de couleurs de pierres
 *
 * @author emuli
 */
public enum Couleur {

    ROUGE("Rouge"),
    BLEU("Bleu"), ROSE("Rose"),
    ORANGE("Orange"),
    JAUNE("Jaune"),
    VERT("Vert"),;

    private String nom;

    /**
     * constructeur de l'enum*
     */
    private Couleur(String nom) {
        this.nom = nom;
    }

    /**
     * méthode qui converti l'enum couleur à une color
     * @return la couleur associé à l'enum couleur 
     *
     */
    public Color getColor() {
        switch (this) {
            case ROSE:
                return Color.PINK;

            case BLEU:
                return Color.CYAN;

            case ORANGE:
                return Color.ORANGE;

            case JAUNE:
                return Color.YELLOW;

            case VERT:
                return Color.GREEN;

            case ROUGE:
                return Color.RED;

        }
        return null;
    }

    /**
     * @return nom de la couleur
  *
     */
    public String getNom() {
        return nom;
    }

}
