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
    //định nghiĩa cho chạy khi gpặ 1 cái exception nào đó
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) //input sai, FE check lại
    public ResponseEntity handleValidation(MethodArgumentNotValidException exception){
        String message = "";
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()){
            message+= fieldError+": "+fieldError.getDefaultMessage();
        }
        return new ResponseEntity(message,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleValidation(Exception exception){
        return new ResponseEntity(exception.getMessage(),HttpStatus.BAD_REQUEST);
    }

}
