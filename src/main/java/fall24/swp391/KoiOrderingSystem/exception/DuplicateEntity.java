package fall24.swp391.KoiOrderingSystem.exception;

public class DuplicateEntity extends RuntimeException{
    public DuplicateEntity(String message){
        super(message);
    }
}
