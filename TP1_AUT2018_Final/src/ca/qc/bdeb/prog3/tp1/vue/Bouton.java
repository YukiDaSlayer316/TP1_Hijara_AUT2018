/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.prog3.tp1.vue;

import ca.qc.bdeb.prog3.tp1.modele.Joueur;
import ca.qc.bdeb.prog3.tp1.modele.Modele;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;

/**
 * classe qui gère un bouton
 * @author 1749637
 */
public class Bouton extends JButton {
    
    private Joueur joueurAssociee=null;
    private int numero;
    
       /**
     *constructeur d'un bouton
     * @param texte numéro du bouton
     */
    
    public Bouton(String texte ){
        super(texte);
        
        this.numero=Integer.parseInt(texte);
        
        setPreferredSize(new Dimension(20,20));
        setEnabled(false);      
    } 
    
    /**
     * @return le numero d'un bouton
     */
    public int getNumero() {
        return numero;
    }

    
    
    
    
    
    
    
    

    
    
    
    
    
    
    
    
    
}
