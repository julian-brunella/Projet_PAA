package main.java.voyage.model;


import main.java.voyage.exception.SaisieInvalideException;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;


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


    /**
     * Échange les chambres de deux personnes spécifiées par leur nom.
     *
     * @param nom1 Le nom de la première personne.
     * @param nom2 Le nom de la seconde personne.
     * @throws SaisieInvalideException Si l'une des personnes n'existe pas ou n'a pas de chambre.
     */
    public void echangerChambres(String nom1, String nom2) throws SaisieInvalideException {
        // On cherche les personnes
        Personne p1 = null;
        Personne p2 = null;
        for (Personne p : groupe.getPersonnes()) {
            if (p.getNom().equals(nom1)) {
                p1 = p;
            } else if (p.getNom().equals(nom2)) {
                p2 = p;
            }
        }

        // Gestion des erreurs : vérification de l'existence des personnes
        if (p1 == null) {
            // On lève l'Exception SaisieInvalideException
            throw new SaisieInvalideException("La personne \"" + nom1 + "\" n'existe pas dans le groupe.");
        }
        if (p2 == null) {
            // On lève l'Exception SaisieInvalideException
            throw new SaisieInvalideException("La personne \"" + nom2 + "\" n'existe pas dans le groupe.");
        }

        // Echange des valeurs
        // Vérification qu'elles ont bien une chambre attribuée avant de faire l'échange
        if (!attribution.containsKey(p1) || !attribution.containsKey(p2)) {
            throw new SaisieInvalideException("Impossible d'échanger : au moins l'une de ces personnes n'a pas encore de chambre.");
        }

        // On fait l'échange à partir de là, après la vérification
        int chambre1 = attribution.get(p1);
        int chambre2 = attribution.get(p2);
        attribution.put(p1, chambre2);
        attribution.put(p2, chambre1);
    }

    /**
     * 
     * @param personne
     * @return
     */
    public boolean estJalouse(Personne personne){
        //recupere la position de la chambre dans la liste de la personne
        Integer chambrePersonne=this.attribution.get(personne);
        //Si la chambre n'a pas encore été affecté
        if (chambrePersonne==null){
            throw new IllegalArgumentException(personne.getNom()+" n'a pas de chambre.");
        }

        //On recupere les gens que la personne aime pas
        Set<Personne> voisinsConflits=this.groupe.getVoisinsConflit(personne);

        for (Personne pConflit:voisinsConflits){
            Integer chambrePConflit=this.attribution.get(pConflit);

            //Regarde si le rang de la chambre de la personne qu'elle aime pas est plus haut que la sienne
            if (chambrePConflit!=null && personne.prefere(chambrePConflit,chambrePersonne)){
                return true;
            }
        }
        return false;
    }



    /**
     * Calcule le coût de l'affectation. Le coût correspond au nombre total de personnes jalouses.
     *
     * @return Le nombre de personnes jalouses.
     */
    public int calculerCout() {
        int cout = 0; // le compteur qui représente le coût (qu'on va donc renvoyer à la fin)
        // On calcule le coût de l'affectation en sommant le nombre de personnes jalouses
        for (Personne p : groupe.getPersonnes()) {
            if (attribution.containsKey(p) && estJalouse(p)) {
                // Une personne jalouse de plusieurs voisins ne compte que pour 1
                // Ce n'est pas le nombre de personnes dont elle est jalouse
                cout++;
            }
        }
        return cout;
    }


    /**
     * Affiche clairement l'affectation.
     *
     * @return Une chaîne de caractères formatée (ex: "A:2\nB:1\nC:3").
     */
    public String afficher() {
        // StringBuilder car on fait une boucle où l'on modifie plein de fois la chaîne de caractères.
        // On évite ainsi de recréer un String à chaque tour de boucle.
        StringBuilder sb = new StringBuilder();

        // On ajoute chaque personne à la chaîne une à une, sous le format "Nom:Chambre" puis un retour à la ligne.
        for (Personne p : groupe.getPersonnes()) {
            if (attribution.containsKey(p)) {
                sb.append(p.getNom()).append(":").append(attribution.get(p)).append("\n"); // Retour à la ligne
            }
        }

        return sb.toString().trim(); // On enlève le dernier saut de ligne superflu
    }
}