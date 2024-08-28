package tdr.pet.weblibrary.exception.book;

public class BookCreationException extends RuntimeException {
    public BookCreationException() {
        super("Couldn't create book");
    }

    public BookCreationException(String message) {
        super(message);
    }
}
