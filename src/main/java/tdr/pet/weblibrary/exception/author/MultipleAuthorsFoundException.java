package tdr.pet.weblibrary.exception.author;

public class MultipleAuthorsFoundException extends RuntimeException{
    public MultipleAuthorsFoundException() {
        super("Multiple authors found by those params");
    }

    public MultipleAuthorsFoundException(String message) {
        super(message);
    }
}
