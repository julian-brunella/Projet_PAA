package main.java.voyage.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Contient le nombre de chambres et le graphe non-orienté des personnes où les arêtes représentent les relations "ne s'aiment pas" et les sommets représentent les personnes.
 *
 * Liste des méthodes :
 * <ul>
 *   <li>ajouterRelationConflit : Crée une arête entre deux personnes pour indiquer qu'elles ne s'aiment pas.</li>
 *   <li>definirPreferences : Délègue l'ajout d'une liste de préférences à la personne correspondante, après avoir vérifié son existence.</li>
 *   <li>obtenirPersonnesSansPreferencesValides : Parcourt le groupe et renvoie la liste des personnes ayant une liste incomplète.</li>
 *   <li>getVoisinsConflit : Renvoie l'ensemble des personnes qu'une personne donnée n'aime pas.</li>
 *   <li>getPersonnes : Renvoie la liste de toutes les personnes du groupe.</li>
 *   <li>getNbChambres : Renvoie le nombre total de chambres (égal au nombre de personnes).</li>
 * </ul>
 */
public class Groupe {
    private final int nbPersonnes; // représente le nombre de personnes dans le voyage
    private final Map<String, Personne> personnesParNom; // association entre nom et personne pour chaque personne
    private final Map<Personne, Set<Personne>> grapheVoyage; // graphe non orienté des relations "ne s'aiment pas" représenté par une liste d'adjacence

    public Groupe(int nbPersonnes){
        this.nbPersonnes = nbPersonnes;
        this.personnesParNom = new java.util.LinkedHashMap<>(); // LinkedHashMap pour garder l'ordre "A", "B", "C", ...
        this.grapheVoyage = new java.util.HashMap<>();

        // On crée les personnes ici (à voir si on garde ça ? ça me paraît bien)
        for (int i = 0 ; i < nbPersonnes ; i++){
            String nom = String.valueOf((char) ('A' + i)); // On crée nbPersonnes qui sont nommées avec les lettres de l'alphabet "A", "B", "C", ... dans l'ordre.
            Personne nouvellePersonne = new Personne(nom); // Création de la personne
            this.personnesParNom.put(nom, nouvellePersonne); // On l'ajoute dans l'association Nom/Personne
            this.grapheVoyage.put(nouvellePersonne, new HashSet<>()); // On l'ajoute dans le graphe, avec au départ aucune relation de non-entente
        }
    }
}
