package tdr.pet.weblibrary.exception.publisher;

public class PublisherCreationException extends RuntimeException {
    public PublisherCreationException() {
        super("Couldn't create publisher");
    }

    public PublisherCreationException(String message) {
        super(message);
    }
}