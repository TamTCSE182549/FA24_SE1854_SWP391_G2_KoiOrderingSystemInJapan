package fall24.swp391.KoiOrderingSystem.exception;

public class ExistingEntity extends RuntimeException {
    public ExistingEntity(String message) {
        super(message);
    }
}
