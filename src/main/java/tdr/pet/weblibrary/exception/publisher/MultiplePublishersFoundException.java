package tdr.pet.weblibrary.exception.publisher;

public class MultiplePublishersFoundException extends RuntimeException {
    public MultiplePublishersFoundException() {
        super("Multiple publishers found");
    }

    public MultiplePublishersFoundException(String message) {
        super(message);
    }
}
