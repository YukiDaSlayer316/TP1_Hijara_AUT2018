/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.prog3.tp1.vue;

import ca.qc.bdeb.prog3.tp1.modele.Couleur;
import ca.qc.bdeb.prog3.tp1.modele.Modele;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *classe principale de la vue qui affiche le jeu complet avec les options qui ne concernent pas necessairement la partie
 * @author 1749637
 */
public class Fenetre extends JFrame implements Observer {

    private static int nbParties = 0;

    private Modele modele;
    
    private JMenuBar mnuBar = new JMenuBar();
    private JMenu mnuFichier = new JMenu("Fichier");
    private JMenu mnuAide = new JMenu("Aide");
    private JMenuItem mnuNouvellePartie = new JMenuItem("Nouvelle partie");
    private JMenuItem mnuNouvelleFenetre = new JMenuItem("Nouvelle fenetre ");
    private JMenuItem mnuOption = new JMenuItem("Option...");
    private JMenuItem mnuQuitter = new JMenuItem("Quitter...");
    private JMenuItem mnuAPropos = new JMenuItem("À propos...");    

    private JTabbedPane tabOnglets = new JTabbedPane();

    private OngletJeuHijara pnlOnglet1;

    /**
     *constructeur de ce que l'utilisateur voit 
     * @param modele les éléments qui composent le jeu joué
     */
    public Fenetre(Modele modele) {
        this.modele = modele;
        modele.addObserver(this);

        setTitle("TP1_HIJARA");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        creerMenu();
        creerEvenementsMenus();
        creerInterface();
     
        setVisible(true);
    }

    /**méthode qui créer le menu**/
    private void creerMenu() {
        mnuFichier.add(mnuNouvellePartie);
       // mnuFichier.add(mnuNouvelleFenetre);
        mnuFichier.add(mnuOption);
        mnuFichier.addSeparator();
        mnuFichier.add(mnuQuitter);

        mnuAide.add(mnuAPropos);

        mnuBar.add(mnuFichier);
        mnuBar.add(mnuAide);

        setJMenuBar(mnuBar);
    }

    /**méthode qui créer les évènements pour les menus**/
    private void creerEvenementsMenus() {
        mnuNouvellePartie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                modele.reinitialiser();
            }
        });

        mnuOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                PanneauOption panneauOption=new PanneauOption(modele);                
                            }
        });

        mnuNouvelleFenetre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                nbParties++;
                OngletJeuHijara pnlOnglet = new OngletJeuHijara(modele, nbParties);
                tabOnglets.add("Partie " + (nbParties + 1), pnlOnglet);

            }
        });

        mnuQuitter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                int choix = JOptionPane.showConfirmDialog(Fenetre.this, "Voulez-vous vraiment quitter?");
                if (choix == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showOptionDialog(null, "Voulez vous fermer l’application?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        mnuAPropos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae
            ) {
                JOptionPane.showMessageDialog(Fenetre.this, "Émulie Chhor, 14 octobre 2018");
            }
        }
        );
    }

    /**méthode qui créée l'interface**/
    private void creerInterface() {
        pnlOnglet1 = new OngletJeuHijara(modele, nbParties);
        tabOnglets.add("1re partie", pnlOnglet1);

        add(tabOnglets, BorderLayout.CENTER);
    }


    @Override
    public void update(Observable o, Object arg) {
     
    }
}
