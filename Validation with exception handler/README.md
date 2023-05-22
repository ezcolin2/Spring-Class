# 예외 처리

1. @Controller나 @RestController가 적용된 빈에서 에러가 발생한다.
2. 1에서 발생한 에러를 @ControllerAdvice에서 catch 한다.
3. @ExceptionHandler가 붙은 메소드들 중 발생한 에러를 다룰 수 있는 handler에서 에러를 처리한다.
4. Custom error를 사용할 수도 있다.