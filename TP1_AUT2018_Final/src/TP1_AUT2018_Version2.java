
import ca.qc.bdeb.prog3.tp1.modele.Modele;
import ca.qc.bdeb.prog3.tp1.vue.Fenetre;

/*
auto-évaluation: au début, j'ai eu de la misère à comprendre quand est-ce que la vue appelle la méthode.
Ma classe Fenetre et OngletJeuHijara pourrait être fusionné, mais je croyais qu'il fallait faire des onglets, alors je les ai séparé.
Je trouve que mon enum Couleur est le meilleur aspect de mon TP. Par contre, je pourrais améliorer la façon dont je trouve les lignes et les colonnes pour faciliter la lecture
 */


/**
 *
 * @author 1749637
 */
public class TP1_AUT2018_Version2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Modele modele=new Modele();
        Fenetre fenetre=new Fenetre(modele);
    }
    
}
