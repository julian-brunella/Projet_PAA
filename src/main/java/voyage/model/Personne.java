package main.java.voyage.model;

import java.util.List;
import java.util.Map;

/**
 * Représente un participant au voyage : ses informations et sa liste de préférence de chambre.
 *
 * Liste des méthodes :
 * <ul>
 *   <li>definirPreferences : enregistre la liste de préférence de la personne tout en vérifiant si elle est valable.</li>
 *   <li>aDesPreferencesCompletes : vérifie si la personne a bien une liste de préférences complète.</li>
 *   <li>aAucunDoublons : vérifie la liste de préférences de la personne ne contient aucun doublon.</li>
 *   <li>prefere : compare deux chambres et dit laquelle le participant préfère</li>
 *   <li>getNom : renvoie le nom du participant</li>
 *   <li>getPreferences : renvoie la liste de préférence.</li>
 * </ul>
 */
public class Personne {
    private final String nom; // une seule lettre "A", "B", "C", ...
    private final List<Integer> preferences; // liste de préférences de la personne
    private final Map<Integer, Integer> rangChambres; // stocke le rang d'une chambre (indice). C'est une optimisation pour effectuer des comparaisons très rapidement.

    /**
     * Constructeur de Personne.
     *
     * @param nom Le nom ou l'identifiant de la personne (par exemple : "A", "B", "C", ...).
     */
    public Personne(String nom) {
        this.nom = nom;
        this.preferences = new java.util.ArrayList<>();
        this.rangChambres = new java.util.HashMap<>();
    }
}
