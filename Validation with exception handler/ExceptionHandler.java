@RestControllerAdvice
public class ExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> requestException(MethodArgumentNotValidException e) {
        .
        .
        .
    }

}
