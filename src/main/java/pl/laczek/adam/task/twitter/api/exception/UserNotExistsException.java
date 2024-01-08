package pl.laczek.adam.task.twitter.api.exception;

public class UserNotExistsException extends RuntimeException {
    public UserNotExistsException(String userId) {
        super("User not exists: " + userId);
    }
}
