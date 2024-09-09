package tdr.pet.weblibrary.exception.book;

public class MultipleBooksFoundException extends RuntimeException {
    public MultipleBooksFoundException() {
        super("Multiple books found");
    }

    public MultipleBooksFoundException(String message) {
        super(message);
    }
}
