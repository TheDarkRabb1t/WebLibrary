package tdr.pet.weblibrary.exception.author;

public class AuthorCreationException extends RuntimeException {
    public AuthorCreationException() {
        super("Error creating new author");
    }

    public AuthorCreationException(String message) {
        super(message);
    }
}
