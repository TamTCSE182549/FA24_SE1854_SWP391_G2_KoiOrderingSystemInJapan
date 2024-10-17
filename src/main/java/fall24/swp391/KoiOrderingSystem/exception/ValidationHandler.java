package fall24.swp391.KoiOrderingSystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidationHandler {
    //định nghĩa cho chạy khi gặp 1 cái exception nào đó
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) //input sai, FE check lại
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException exception){
        StringBuilder message = new StringBuilder();
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()){
            message.append(fieldError.getField()).append(": ").append(fieldError.getDefaultMessage()).append("\n");
        }
        return new ResponseEntity<>(message.toString(),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleValidation(Exception exception){
        return new ResponseEntity<>(exception.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<String> handleAccountNotFoundException(AccountNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<?> handleGenericException(Exception exception){
        return new ResponseEntity<>("Error System: " + exception.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotUpdateException.class)
    public ResponseEntity<?> handleUpdateException(NotUpdateException exception){
        return new ResponseEntity<>("Error Update: " + exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotDeleteException.class)
    public ResponseEntity<?> handleDeleteException(NotDeleteException exception){
        return new ResponseEntity<>("Error Delete: " + exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotCreateException.class)
    public ResponseEntity<?> handleCreateException(NotCreateException exception){
        return new ResponseEntity<>("Error Create: " + exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotReadException.class)
    public ResponseEntity<?> handleReadException(NotReadException exception){
        return new ResponseEntity<>("Error Read Data: " + exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
