package tdr.pet.weblibrary.exception.author;

public class AuthorUpdateException extends RuntimeException {
    public AuthorUpdateException() {
        super("Error updating author");
    }

    public AuthorUpdateException(String message) {
        super(message);
    }
}
