@RestControllerAdvice
public class CustomExceptionManager {
    @ExceptionHandler(DuplicatedUserIdException.class)
    public ResponseEntity<JsonResponse> duplicatedUserIdException(DuplicatedUserIdException e) {
        JsonResponse res = JsonResponse.builder()
                .code(HttpStatus.CONFLICT.value())
                .httpStatus(HttpStatus.CONFLICT)
                .message(e.getMessage()).build();
        return new ResponseEntity<>(res, res.getHttpStatus());
    }
}
