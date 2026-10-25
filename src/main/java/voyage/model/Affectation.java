package main.java.voyage.model;


import java.util.Map;
import java.util.HashMap;


/**
 * Représente une solution au problème : l'affectation d'une chambre à chaque participant.
 * Cette classe permet de simuler la répartition, de modifier les attributions et de calculer le coût (c'est-à-dire le nombre de personnes jalouses).
 *
 * Liste des méthodes :
 * <ul>
 *   <li>attribuerChambre : Associe une chambre spécifique à une personne.</li>
 *   <li>echangerChambres : Permute les chambres actuellement attribuées à deux personnes distinctes.</li>
 *   <li>estJalouse : Vérifie si une personne donnée est jalouse d'un de ses voisins de conflit.</li>
 *   <li>calculerCout : Compte le nombre total de personnes jalouses (le coût de l'affectation).</li>
 *   <li>afficher : Génère l'affichage textuel de l'affectation sous le format "A:2", "B:1", "C:3"...</li>
 * </ul>
 */
public class Affectation {
    private final Groupe groupe; // Objet de la classe Groupe, pour accéder aux personnes et au graphe des conflits
    private final Map<Personne, Integer> attribution; // Associe chaque personne à son numéro de chambre

    /**
     * Constructeur d'une Affectation.
     *
     * @param groupe L'instance du groupe de personnes pour cette affectation.
     */
    public Affectation(Groupe groupe) {
        this.groupe = groupe;
        this.attribution = new HashMap<>();
    }

    /**
     * Attribue une chambre à une personne.
     *
     * @param personne Le participant à qui on va attribuer la chambre.
     * @param chambre Le numéro de la chambre.
     */
    public void attribuerChambre(Personne personne, int chambre) {
        this.attribution.put(personne, chambre);
    }


}
