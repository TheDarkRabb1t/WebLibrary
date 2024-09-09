package tdr.pet.weblibrary.exception.book;

public class BookNotFoundException extends RuntimeException{
    public BookNotFoundException() {
        super("Book wasn't found");
    }

    public BookNotFoundException(String message) {
        super(message);
    }
}
