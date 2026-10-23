package main.java.voyage.model;

import main.java.voyage.exception.SaisieInvalideException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Représente un participant au voyage : ses informations et sa liste de préférence de chambre.
 *
 * Liste des méthodes :
 * <ul>
 *   <li>definirPreferences : enregistre la liste de préférence de la personne tout en vérifiant si elle est valable.</li>
 *   <li>aDesPreferencesCompletes : vérifie si la personne a bien une liste de préférences complète.</li>
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

    /**
     * Définit et valide la liste de préférences de la personne.
     * Appelée notamment quand la personne change sa liste de préférences.
     *
     * @param nouvellesPreferences Liste ordonnée des numéros de chambres.
     * @param nbChambres Le nombre de chambres dans le groupe.
     * @throws SaisieInvalideException si la taille est incorrecte, s'il y a des doublons ou des numéros invalides.
     */
    public void definirPreferences(List<Integer> nouvellesPreferences, int nbChambres) throws SaisieInvalideException {
        // Gestion des erreurs : cas où la liste de préférence ne contient pas nbChambres,
        // car une personne doit classer toutes les chambres dans sa liste de préférences.
        if (nouvellesPreferences.size() != nbChambres) {
            // On lève l'Exception SaisieInvalideException
            throw new SaisieInvalideException("La liste de préférences doit contenir exactement " + nbChambres + " chambres.");
        }

        // On crée une liste brouillon pour ne pas toucher directement à preferences.
        // C'est au cas où il y a des erreurs détectées.
        List<Integer> tempPreferences = new ArrayList<>();

        // On parcourt chaque chambre dans la liste de préférences actuelle de l'utilisateur
        for (Integer chambre : nouvellesPreferences) {
            // Gestion des erreurs : cas où le numéro de chambre est invalide.
            // Les numéros de chambres sont compris entre 1 et nbChambres.
            if (chambre < 1 || chambre > nbChambres) {
                // On lève l'Exception SaisieInvalideException
                throw new SaisieInvalideException("Le numéro de chambre " + chambre + " est invalide (doit être compris entre 1 et " + nbChambres + ").");
            }
            // Gestion des erreurs : cas où il y a un doublon dans la liste de préférences.
            // La personne ne peut saisir une chambre qu'une seule fois dans sa liste de préférences.
            if (tempPreferences.contains(chambre)) {
                // On lève l'Exception SaisieInvalideException
                throw new SaisieInvalideException("La chambre " + chambre + " apparaît en double dans les préférences.");
            }
            tempPreferences.add(chambre);
        }

        // Si tout est valide, on met à jour les attributs avec la nouvelle liste de la personne :
        this.preferences.clear(); // on vide la liste de préférence actuelle
        this.preferences.addAll(tempPreferences); // on la remplace par la toute nouvelle liste de préférences
        this.rangChambres.clear(); // on remet à 0 le rang des chambres

        // On attribue à chaque chambre son rang.
        for (int i = 0; i < this.preferences.size(); i++) {
            this.rangChambres.put(this.preferences.get(i), i);
        }
    }

    /**
     * Vérifie si la personne a défini l'intégralité de ses préférences (aucun oublie effectué).
     *
     * @param nbChambres Le nombre total de chambres.
     * @return true si les préférences sont complètes, false sinon.
     */
    public boolean aDesPreferencesCompletes(int nbChambres) {
        return this.preferences.size() == nbChambres;
    }

    /**
     * Indique si la personne préfère strictement la chambre A à la chambre B.
     *
     * @param chambreA Numéro de la première chambre.
     * @param chambreB Numéro de la seconde chambre.
     * @return true si la chambre A est préférée à la chambre B.
     * @throws IllegalArgumentException si l'une des chambres n'est pas dans les préférences enregistrées.
     */
    public boolean prefere(int chambreA, int chambreB) {
        // Gestion des erreurs : cas où la chambre n'existe pas dans la liste de préférences de la personne.
        if (!rangChambres.containsKey(chambreA) || !rangChambres.containsKey(chambreB)) {
            // On lève l'Exception IllegalArgumentException
            throw new IllegalArgumentException("Impossible de comparer : l'une des chambres n'est pas dans les préférences de " + this.nom);
        }
        // Un rang plus petit correspond à une meilleure place dans la liste (indice 0 = préférée)
        return rangChambres.get(chambreA) < rangChambres.get(chambreB);
    }

    /**
     * Retourne le nom de la personne.
     *
     * @return Le nom de la personne.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Retourne une vue non modifiable des préférences pour respecter le principe d'encapsulation.
     *
     * @return La liste des préférences de chambres.
     */
    public List<Integer> getPreferences() {
        return Collections.unmodifiableList(preferences);
    }
}
