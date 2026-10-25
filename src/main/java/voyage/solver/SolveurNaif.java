package main.java.voyage.solver;

import java.util.Set;
import java.util.HashSet;

import main.java.voyage.model.Groupe;
import main.java.voyage.model.Personne;
import main.java.voyage.model.Affectation;

/**
 * Implémentation de l'algorithme pour résoudre le problème d'affectation de chambres pour le voyage.
 * Algorithme naïf pour le moment (Partie 1).
 *
 * Liste de méthodes :
 * <ul>
 *   <li> solve : résout le problème.
 * </ul>
 */
public class SolveurNaif{

    /**
     * Résout de manière naïve (non-optimale) le problème d'attribution des chambres.
     *
     * @param groupe L'instance du groupe de personnes dans le problème.
     * @return Une affectation (non-optimale) qui résout le problème.
     */
    public Affectation solve(Groupe groupe){
        // On crée l'affectation, que l'on va renvoyer à la fin.
        Affectation affectation = new Affectation(groupe);

        // On crée la liste de chambres disponibles pour suivre au fur et à mesure lesquelles le sont toujours.
        Set<Integer> chambresDisponibles = new HashSet<>();
        for (int i = 1 ; i <= groupe.getNbChambres() ; i++){
            chambresDisponibles.add(i);
        }

        // On parcourt l'ensemble de toutes les personnes du voyage
        for (Personne personne : groupe.getPersonnes()){
            // Pour chaque personne, on regarde chacune de ses préférences
            for (Integer chambreVoulue : personne.getPreferences()){
                // Si la chambre regardée est disponible, on lui attribue celle-ci.
                if (chambresDisponibles.contains(chambreVoulue)){
                    affectation.attribuerChambre(personne, chambreVoulue);
                    chambresDisponibles.remove(chambreVoulue);
                    // Dans de cas, on met fin à la deuxième boucle for, puisque le problème est résolue pour la personne étudiée.
                    break;
                }
            }
        }
        // On renvoie simplement l'affectation finale (non-optimale).
        return affectation;
    }
}