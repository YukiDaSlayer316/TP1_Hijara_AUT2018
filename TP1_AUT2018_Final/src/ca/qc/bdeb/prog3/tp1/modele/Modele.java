/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.prog3.tp1.modele;

import java.awt.Color;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Observable;

/**
 *
 * @author 1749637
 */
public class Modele extends Observable {

    private int nbJoueurs = 2;
    private int nbPierresParJoueur = 32;
    private int nbToursRestant = nbPierresParJoueur;
    private int posJoueurActuel = 0;
    private int nbBoutons;
    private int nbSecondesEcoules = 0, nbMinutes, nbSecondes;

    private int posColonneQuad, posLigneQuad, numeroCase = 1;

    public static final int POINTS_NUMERO_CONSECUTIFS = 10, POINTS_NUMERO_ORDRE_NUMERIQUE = 15, POINTS_CARREE_REMPLI = 20;

    private ArrayList<Joueur> listeJoueurs = new ArrayList<>();

    public static final int NB_COLONNES_PLATEAU = 4, NB_LIGNES_PLATEAU = 4;

    private Carree[][] plateau = new Carree[NB_COLONNES_PLATEAU][NB_LIGNES_PLATEAU];

    /**
     * méthode qui créé les joueurs et le plateau nécessaire pour jouer à hijara
     */
    public Modele() {
        creerJoueurs();
        initialiserPlateau();
    }

    /**
     * méthode qui intialise le plateau
     */
    private void initialiserPlateau() {
        for (int i = 0; i < NB_COLONNES_PLATEAU; i++) {
            for (int j = 0; j < NB_LIGNES_PLATEAU; j++) {
                Carree carree = new Carree(i, j);
                plateau[i][j] = carree;
            }
        }
    }

    /**
     * méthode qui dit à la vue que le modèle a changé*
     */
    private void majObservers() {
        setChanged();
        notifyObservers();
    }

    /**
     * @return la liste de joueurs
     */
    public ArrayList<Joueur> getListeJoueurs() {
        return listeJoueurs;
    }

    /**
     * méthode qui créé les joueurs avant que la partie commence*
     */
    private void creerJoueurs() {
        for (int i = 0; i < nbJoueurs; i++) {
            Couleur couleur = Couleur.values()[i];
            Joueur joueur = new Joueur(i, couleur);
            listeJoueurs.add(joueur);
        }
    }

    /**
     * méthode qui calcule et attribue les points du joueur qui vient de faire
     * son tour*
     */
    private void calculerPointsJoueur() {
        nbBoutons = plateau[0][0].getCarree().length;
        int pointsJoueur = 0;
        pointsJoueur += calculerPointsNumeroConsecutifs();
        pointsJoueur += calculerPointsNumeroOrdreNumerique();
        pointsJoueur += calculerPointsCarreRempli();
        listeJoueurs.get(posJoueurActuel).setNbPoints(pointsJoueur);
        majObservers();

    }

    /**
     * méthode qui détermine la position du joueur gagnant lorsque la partie est
     * terminée
     *
     * @return la position du Joueur gagnant dans la liste de joueurs
     */
    public int determinerposJoueurGagnant() {
        int posJoueurGagnant = 0;

        Joueur joueurGagnant = listeJoueurs.get(0);
        Joueur joueurPerdant =null;

        for (int i = 1; i < listeJoueurs.size(); i++) {
            Joueur joueurCompare = listeJoueurs.get(i);
            if (joueurCompare.getNbPoints() > joueurGagnant.getNbPoints()) {
                joueurPerdant = joueurGagnant;
                joueurGagnant = joueurCompare;
                posJoueurGagnant = i;
            }else{
                joueurPerdant=joueurCompare;
            }
        }

        //verifier si egalite
        try {
            if (joueurPerdant.getNbPoints() == joueurGagnant.getNbPoints()) {
                return -1;
            }
        } catch (NullPointerException e) {

        }

        return posJoueurGagnant;
    }

    /**
     * méthode qui met à jour la nouvelle couleur choisi par le joueur dans le
     * panneau option et communique avec la vue
     *
     * @param couleur la nouvelle couleur choisie
     */
    public void setCouleurJoueurACtuel(Couleur couleur) {
        listeJoueurs.get(posJoueurActuel).setCouleur(couleur);
        majObservers();
    }

    /**
     * méthode qui calcule les points accumulés par le joueur si ses numeros
     * sont consecutifs
     *
     * @return les points accordés si les pierres du joueur ont des numéros
     * consécutifs à l'horizontale, la verticale ou la diagonale
     *
     */
    private int calculerPointsNumeroConsecutifs() {
        int pointsJoueurs = 0;
        pointsJoueurs += calculerPointsNumeroConsecutifsHorizontal();
        pointsJoueurs += calculerPointsNumeroConsecutifsVertical();
        pointsJoueurs += calculerPointsNumeroConsecutifsDiagonal();

        return pointsJoueurs;
    }

    /**
     * méthode qui calcule les points accumulés par le joueur si ses numeros
     * sont en ordre numérique
     *
     * @return les points accordés si les pierres du joueur ont des numéros en
     * ordre numérique à l'horizontale, la verticale ou la diagonale
     *
     */
    private int calculerPointsNumeroOrdreNumerique() {
        int pointsJoueurs = 0;
        pointsJoueurs += calculerPointsNumeroOrdreNumeriqueHorizontal();
        pointsJoueurs += calculerPointsNumeroOrdreNumeriqueVertical();
        pointsJoueurs += calculerPointsNumeroOrdreNumeriqueDiagonal();
        return pointsJoueurs;
    }

    /**
     * méthode qui calcule les points accumulés par le joueur s'il a rempli un
     * carré au complet
     *
     * @return les points accordés si le joueur a rempli un carré au complet
     *
     */
    private int calculerPointsCarreRempli() {
        int pointsJoueur = 0;

        Couleur couleurJoueur = listeJoueurs.get(posJoueurActuel).getCouleur();

        for (int i = 0; i < NB_COLONNES_PLATEAU; i++) {
            for (int j = 0; j < NB_LIGNES_PLATEAU; j++) {
                boolean estCarreAuJoueur = true;
                int posCase = 0;
                while (estCarreAuJoueur && posCase < ((plateau[i][j].getCarree()).length)) {
                    //il se peut que la case n'aille pas de joueur, donc pas de couleur associer
                    Case cetteCase = (plateau[i][j].getCarree()[posCase]);
                    if (cetteCase.getJoueurAssocie() != null) {
                        Couleur couleurCase = (cetteCase.getJoueurAssocie()).getCouleur();
                        if (!(couleurJoueur.equals(couleurCase))) {
                            estCarreAuJoueur = false;
                        }
                    } else {
                        estCarreAuJoueur = false;
                    }
                    posCase++;
                }

                if (estCarreAuJoueur) {
                    pointsJoueur += POINTS_CARREE_REMPLI;
                    //  System.out.println("numero carre rempli");

                }

            }
        }
        return pointsJoueur;
    }

    /**
     * méthode qui réinitialise le plateau et les joueurs lorsque l'utilisateur
     * recommence une partie
     */
    public void reinitialiser() {
        reinitialiserJoueurs();
        reinitialiserPlateau();
        majObservers();
    }

    /**
     * méthode appelée lorsque le joueur appuie sur un bouton
     *
     * @param posColonneQuad la position en x du carré dans lequel le bouton se
     * situe
     * @param posLigneQuad la position en y du carré dans lequel le bouton se
     * situe
     * @param numeroCase le numéro du bouton cliqué
     */
    public void setPositions(int posColonneQuad, int posLigneQuad, int numeroCase) {
        this.posColonneQuad = posColonneQuad;
        this.posLigneQuad = posLigneQuad;
        this.numeroCase = numeroCase;

        Case[] carree = plateau[posColonneQuad][posLigneQuad].getCarree();

        int posCase = numeroCase - 1;
        int posCaseSuivante = posCase + 1;

        boolean estCaseCliquable = carree[posCase].isEstCliquable();

        if (estCaseCliquable) {
            utilisateurDeposePierre(posCase, posCaseSuivante);
            calculerPointsJoueur();
            changerjoueur();
            //determinerGagnant();
            majObservers();
        }
    }

    /**
     * méthode qui associe un joueur à une case, qui incremente le nombre de
     * pierres dans le carré et qui active/désactive les boutons qui seront
     * jouables lors du prochain tour lorsque le joueur dépose sa pierre
     *
     */
    private void utilisateurDeposePierre(int posCase, int posCaseSuivante) {
        ((plateau[posColonneQuad][posLigneQuad].getCarree())[posCase]).setJoueurAssocie(listeJoueurs.get(posJoueurActuel));
        (plateau[posColonneQuad][posLigneQuad]).incrementerNbPierres();
        ((plateau[posColonneQuad][posLigneQuad].getCarree())[posCase]).desactiverBouton();
        try {
            ((plateau[posColonneQuad][posLigneQuad].getCarree())[posCaseSuivante]).activerBouton();
        } catch (ArrayIndexOutOfBoundsException e) {

        }
    }

    /**
     * méthode qui réinitialise tous les joueurs lorsqu'une nouvelle partie est
     * demandée*
     */
    private void reinitialiserJoueurs() {
        for (int i = 0; i < listeJoueurs.size(); i++) {
            Couleur couleur = Couleur.values()[i];
            (listeJoueurs.get(i)).reinitialiserJoueur(couleur);
        }
    }

    /**
     * méthode qui réinitialise le plateau lorsqu'une nouvelle partie est
     * demandée*
     */
    private void reinitialiserPlateau() {
        for (int i = 0; i < NB_COLONNES_PLATEAU; i++) {
            for (int j = 0; j < NB_LIGNES_PLATEAU; j++) {
                plateau[i][j].reintialiserCarree();
            }
        }
    }

    /**
     * méthode qui calcule les points accumulés par le joueur si ses numeros
     * sont en ordre numérique à l'horizontale
     *
     * @return les points accordés si les pierres du joueur sont des numéros en
     * ordre numérique à l'horizontale
     *
     */
    private int calculerPointsNumeroOrdreNumeriqueHorizontal() {

        int points = 0;

        boolean estColonneNumeroConsecutif;
        Couleur couleurJoueur = listeJoueurs.get(posJoueurActuel).getCouleur();
        int posCase;
        for (int i = 0; i < NB_COLONNES_PLATEAU; i++) {
            estColonneNumeroConsecutif = true;
            posCase = 0;
            for (int j = 0; j < NB_LIGNES_PLATEAU && estColonneNumeroConsecutif; j++) {
                Case cetteCase = (plateau[i][j].getCarree())[posCase];
                try {
                    Couleur couleurCase = cetteCase.getJoueurAssocie().getCouleur();
                    if (!couleurJoueur.equals(couleurCase)) {
                        estColonneNumeroConsecutif = false;
                    }
                } catch (NullPointerException e) {
                    estColonneNumeroConsecutif = false;
                }
                posCase++;
            }
            if (estColonneNumeroConsecutif) {
                points += POINTS_NUMERO_ORDRE_NUMERIQUE;

            }
        }
        return points;
    }

    /**
     * méthode qui calcule les points accumulés par le joueur si ses numeros
     * sont en ordre numérique à la verticale
     *
     * @return les points accordés si les pierres du joueur sont des numéros en
     * ordre numérique à la verticale
     *
     */
    private int calculerPointsNumeroOrdreNumeriqueVertical() {
        int points = 0;

        Couleur couleurJoueur = listeJoueurs.get(posJoueurActuel).getCouleur();

        boolean estLigneNumeroConsecutif;
        int posCase;
        for (int i = 0; i < NB_LIGNES_PLATEAU; i++) {
            estLigneNumeroConsecutif = true;
            posCase = 0;
            for (int j = 0; j < NB_COLONNES_PLATEAU && estLigneNumeroConsecutif; j++) {
                Case cetteCase = (plateau[j][i].getCarree())[posCase];
                try {
                    Couleur couleurCase = cetteCase.getJoueurAssocie().getCouleur();
                    if (!couleurJoueur.equals(couleurCase)) {
                        estLigneNumeroConsecutif = false;
                    }
                } catch (NullPointerException e) {
                    estLigneNumeroConsecutif = false;
                }
                posCase++;
            }
            if (estLigneNumeroConsecutif) {
                points += POINTS_NUMERO_ORDRE_NUMERIQUE;

            }
        }
        return points;
    }

    /**
     * méthode qui calcule les points accumulés par le joueur si ses numeros
     * sont en ordre numérique sur les 2 diagonales
     *
     * @return les points accordés si les pierres du joueur sont des numéros en
     * ordre numérique sur les 2 diagonales
     *
     */
    private int calculerPointsNumeroOrdreNumeriqueDiagonal() {
        int points = 0, posBouton = 0;
        Couleur couleurJoueur = listeJoueurs.get(posJoueurActuel).getCouleur();

        boolean estDiagonaleConsecutive = true;

        //diagonale haut-gauche à bas-droite 1-4
        for (int posLigneColonne = 0; posLigneColonne < NB_COLONNES_PLATEAU; posLigneColonne++) {
            Case cetteCase = (plateau[posLigneColonne][posLigneColonne].getCarree())[posBouton];
            try {
                Couleur couleurCase = cetteCase.getJoueurAssocie().getCouleur();
                if (!couleurCase.equals(couleurJoueur)) {
                    estDiagonaleConsecutive = false;
                }
            } catch (NullPointerException e) {
                estDiagonaleConsecutive = false;
            }
            posBouton++;
        }

        if (estDiagonaleConsecutive) {
            points += POINTS_NUMERO_ORDRE_NUMERIQUE;
        }

        //diagonale haut-gauche à bas-droite 4-1
        posBouton = nbBoutons - 1;
        estDiagonaleConsecutive = true;
        for (int posLigneColonne = 0; posLigneColonne < NB_COLONNES_PLATEAU; posLigneColonne++) {
            Case cetteCase = (plateau[posLigneColonne][posLigneColonne].getCarree())[posBouton];
            try {
                Couleur couleurCase = cetteCase.getJoueurAssocie().getCouleur();
                if (!couleurCase.equals(couleurJoueur)) {
                    estDiagonaleConsecutive = false;
                }
            } catch (NullPointerException e) {
                estDiagonaleConsecutive = false;
            }
            posBouton--;
        }

        if (estDiagonaleConsecutive) {
            points += POINTS_NUMERO_ORDRE_NUMERIQUE;
        }

        //diagonale haut-droite à bas-gauche 1-4
        estDiagonaleConsecutive = true;
        posBouton = 0;
        int posLigne = 0;

        for (int posColonne = NB_COLONNES_PLATEAU - 1; posColonne >= 0 && estDiagonaleConsecutive; posColonne--) {
            try {
                Case cetteCase = (plateau[posColonne][posLigne].getCarree())[posBouton];
                Couleur couleurCase = cetteCase.getJoueurAssocie().getCouleur();
                if (!couleurCase.equals(couleurJoueur)) {
                    estDiagonaleConsecutive = false;
                }
            } catch (NullPointerException e) {
                estDiagonaleConsecutive = false;
            }
            posLigne++;
            posBouton++;
        }

        if (estDiagonaleConsecutive) {
            points += POINTS_NUMERO_ORDRE_NUMERIQUE;

        }

        //diagonale haut-gauche à bas-droite 4-1
        estDiagonaleConsecutive = true;
        posBouton = nbBoutons - 1;
        posLigne = 0;
        for (int posColonne = NB_COLONNES_PLATEAU - 1; posColonne >= 0 && estDiagonaleConsecutive; posColonne--) {
            Case cetteCase = (plateau[posColonne][posLigne].
                    getCarree())[posBouton];
            try {

                Couleur couleurCase = cetteCase.getJoueurAssocie().getCouleur();
                if (!couleurCase.equals(couleurJoueur)) {
                    estDiagonaleConsecutive = false;
                }
            } catch (NullPointerException e) {
                estDiagonaleConsecutive = false;
            }
            posLigne++;
            posBouton--;
        }

        if (estDiagonaleConsecutive) {
            points += POINTS_NUMERO_ORDRE_NUMERIQUE;

        }

        return points;
    }

    /**
     * méthode qui calcule les points accumulés par le joueur si ses numeros
     * sont consécutifs à l'horizontale
     *
     * @return les points accordés si les pierres du joueur sont des numéros
     * consécutifs à l'horizontale
     *
     */
    private int calculerPointsNumeroConsecutifsHorizontal() {

        int points = 0;
        boolean estLigneConsecutive;

        Couleur couleurJoueur = listeJoueurs.get(posJoueurActuel).getCouleur();
        for (int posCase = 0; posCase < Carree.getNB_BOUTONS(); posCase++) {
            for (int i = 0; i < NB_COLONNES_PLATEAU; i++) {
                estLigneConsecutive = true;
                for (int j = 0; j < NB_LIGNES_PLATEAU && estLigneConsecutive; j++) {
                    Case cetteCase = (plateau[i][j].getCarree())[posCase];
                    try {
                        Couleur couleurCase = cetteCase.getJoueurAssocie().getCouleur();
                        if (!couleurCase.equals(couleurJoueur)) {
                            estLigneConsecutive = false;
                        }
                    } catch (NullPointerException e) {
                        estLigneConsecutive = false;
                    }

                }
                if (estLigneConsecutive) {
                    points += POINTS_NUMERO_CONSECUTIFS;
                }
            }
        }

        return points;
    }

    /**
     * méthode qui calcule les points accumulés par le joueur si ses numeros
     * sont consécutifs à la verticale
     *
     * @return les points accordés si les pierres du joueur sont des numéros
     * consécutifs à la verticale
     *
     */
    private int calculerPointsNumeroConsecutifsVertical() {
        int points = 0;
        boolean estLigneConsecutive = true;

        Couleur couleurJoueur = listeJoueurs.get(posJoueurActuel).getCouleur();
        for (int posCase = 0; posCase < Carree.getNB_BOUTONS(); posCase++) {
            // int posCase = numeroCase - 1;
            for (int i = 0; i < NB_LIGNES_PLATEAU; i++) {
                estLigneConsecutive = true;
                for (int j = 0; j < NB_COLONNES_PLATEAU && estLigneConsecutive; j++) {
                    Case cetteCase = (plateau[j][i].getCarree())[posCase];
                    try {
                        Couleur couleurCase = cetteCase.getJoueurAssocie().getCouleur();
                        if (!couleurCase.equals(couleurJoueur)) {
                            estLigneConsecutive = false;
                        }
                    } catch (NullPointerException e) {
                        estLigneConsecutive = false;
                    }

                }
                if (estLigneConsecutive) {
                    points += POINTS_NUMERO_CONSECUTIFS;

                }
            }
        }

        return points;
    }

    /**
     * méthode qui calcule les points accumulés par le joueur si ses numeros
     * sont consécutifs sur les 2 diagonales
     *
     * @return les points accordés si les pierres du joueur sont des numéros
     * consécutifs sur les 2 diagonales
     *
     */
    private int calculerPointsNumeroConsecutifsDiagonal() {
        int points = 0;
        Couleur couleurJoueur = listeJoueurs.get(posJoueurActuel).getCouleur();

        boolean estDiagonaleConsecutive;

        //diagonale haut-gauche a bas droite(marche)
        for (int posNumeroCase = 0; posNumeroCase < nbBoutons; posNumeroCase++) {
            estDiagonaleConsecutive = true;
            for (int posLigneColonne = 0; posLigneColonne < NB_COLONNES_PLATEAU && estDiagonaleConsecutive; posLigneColonne++) {
                Case cetteCase = (plateau[posLigneColonne][posLigneColonne].getCarree())[posNumeroCase];
                try {
                    Couleur couleurCase = cetteCase.getJoueurAssocie().getCouleur();
                    if (!couleurCase.equals(couleurJoueur)) {
                        estDiagonaleConsecutive = false;
                    }
                } catch (NullPointerException e) {
                    estDiagonaleConsecutive = false;
                }
            }
            if (estDiagonaleConsecutive) {
                points += POINTS_NUMERO_CONSECUTIFS;
                System.out.println("numero consecutif diago");

            }
        }
        //diagonale haut-droite a bas-gauche

        for (int posNumeroCase = 0; posNumeroCase < nbBoutons; posNumeroCase++) {
            estDiagonaleConsecutive = true;
            int posLigne = 0;
            for (int posColonne = NB_COLONNES_PLATEAU - 1; posColonne >= 0 && estDiagonaleConsecutive; posColonne--) {
                Case cetteCase = (plateau[posColonne][posLigne].getCarree())[posNumeroCase];
                try {
                    Couleur couleurCase = cetteCase.getJoueurAssocie().getCouleur();
                    if (!couleurCase.equals(couleurJoueur)) {
                        estDiagonaleConsecutive = false;
                    }
                } catch (NullPointerException e) {
                    estDiagonaleConsecutive = false;
                }
                posLigne++;
            }
            if (estDiagonaleConsecutive) {
                points += POINTS_NUMERO_CONSECUTIFS;                  

            }
        }

        return points;
    }

    /**
     * @return position que le joueur actuel occupe dans la liste de joueurs
     */
    public int getPosJoueurActuel() {
        return posJoueurActuel;
    }

    /**
     * @return le plateau de jeu
     */
    public Carree[][] getPlateau() {
        return plateau;
    }

    /**
     * méthode qui change le joueur qui joue à chaque fois que l'autre fini son
     * tour*
     */
    private void changerjoueur() {
        //a changer
        if (posJoueurActuel == 0) {
            posJoueurActuel = 1;
        } else {
            posJoueurActuel = 0;
        }

    }

    /**
     * méthode qui détermine si la partie est terminée
     *
     * @return true si la partie est fini, false s'il reste des cases à remplir
     */
    public boolean isEstPlateauRempli() {
        for (int i = 0; i < NB_COLONNES_PLATEAU; i++) {
            for (int j = 0; j < NB_LIGNES_PLATEAU; j++) {
                for (int posBouton = 0; posBouton < nbBoutons; posBouton++) {
                    try {
                        Couleur couleurCase = plateau[i][j].getCarree()[posBouton].getJoueurAssocie().getCouleur();
                    } catch (NullPointerException e) {
                        return false;
                    }
                }

            }
        }
        return true;

    }

    /**
     * méthode qui incrémente le nombres de minutes écoulées à chaque seconde*
     */
    public void incrementerSecondesEcoulees() {
        nbSecondesEcoules++;
        nbMinutes = nbSecondesEcoules / 60;
        nbSecondes = nbSecondesEcoules % 60;
    }

    /**
     * @return le nombre de secondes à afficher selon les minutes écoulées*
     */
    public int getNbSecondes() {
        return nbSecondes;
    }

    /**
     * @return le nombres de minutes écoulées
     */
    public int getNbMinutes() {
        return nbMinutes;
    }

}
