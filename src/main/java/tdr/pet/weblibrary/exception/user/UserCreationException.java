package tdr.pet.weblibrary.exception.user;

public class UserCreationException extends RuntimeException {
    public UserCreationException() {
        super("Error creating new user");
    }

    public UserCreationException(String message) {
        super(message);
    }
}
