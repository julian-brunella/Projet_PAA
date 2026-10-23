package main.java.voyage.exception;

/**
 * Exception levée lorsqu'une saisie utilisateur est invalide (personne inexistante, préférences incomplètes ou format incorrect).
 */
public class SaisieInvalideException extends Exception {

    /**
     * Construit une nouvelle exception avec le message d'erreur spécifié.
     * @param message Le détail de l'erreur.
     */
    public SaisieInvalideException(String message) {
        super(message);
    }
}