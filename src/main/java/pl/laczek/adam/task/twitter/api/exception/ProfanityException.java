package pl.laczek.adam.task.twitter.api.exception;

public class ProfanityException extends RuntimeException {
    public ProfanityException(String input) {
        super("Used profanity: " + input);
    }
}
