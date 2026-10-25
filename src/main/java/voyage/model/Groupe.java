package main.java.voyage.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import java.util.ArrayList;
import java.util.Collections;

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

    public void ajouterRelationConflit(String nom1,String nom2){
        //On recupere les personnes a partir de leur nom
        Personne p1=personnesParNom.get(nom1);
        Personne p2=personnesParNom.get(nom2);

        if (p1==null || p2==null){
            throw new IllegalArgumentException("Aucune personne avec le nom donné");
        }

        if (p1.equals(p2)){
            throw new IllegalArgumentException("La même personne à été donné");
        }

        //La relation est symetrique
        this.grapheVoyage.get(p1).add(p2);
        this.grapheVoyage.get(p2).add(p1);
    }

    public void definirPreferences(String nom,ArrayList<Integer> preferences){
        //On recupere les listes de preference de chaque personne
        Personne p=personnesParNom.get(nom);
        if (p!=null){
            p.definirPreferences(preferences,this.nbPersonnes);
        }
    }

    public ArrayList<Personne> obtenirPersonnesSansPreferencesValides(){
        //Regarde pour chaque personne si sa liste de preference est complete
        ArrayList<Personne> personnesIncompletes=new ArrayList<>();
        for (Personne p:personnesParNom.values()){
            if (!p.aDesPreferencesCompletes(this.nbPersonnes)){
                personnesIncompletes.add(p);
            }
        }
        return personnesIncompletes;
    }

    public Set<Personne> getVoisinsConflit(Personne personne){
        if (!grapheVoyage.containsKey(personne)){
            throw new IllegalArgumentException("Cette personne n'est pas dans le groupe");
        }
        //Renvoie une copie pour pas que le set originel puisse etre modifié
        return new HashSet<>(grapheVoyage.get(personne));
    }

    public ArrayList<Personne> getPersonnes(){
        //pas besoin d'expliquer la
        return new ArrayList<>(personnesParNom.values());
    }

    public int getNbChambres(){
        //pareil
        return this.nbPersonnes;    
    }
}