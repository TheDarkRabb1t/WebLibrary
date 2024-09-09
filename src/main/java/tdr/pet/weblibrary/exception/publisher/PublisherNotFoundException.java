package tdr.pet.weblibrary.exception.publisher;

public class PublisherNotFoundException extends RuntimeException {
    public PublisherNotFoundException() {
        super("Publisher not found");
    }

    public PublisherNotFoundException(String message) {
        super(message);
    }
}
