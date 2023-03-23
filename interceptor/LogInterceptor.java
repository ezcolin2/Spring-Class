@Slf4j
public class LogInterceptor implements HandlerInterceptor {
    //컨트롤러 호출 전
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String uri = request.getRequestURI();
        log.info("Request uri : {}", uri);
        return true;
    }

    //컨트롤러 호출 후
    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) throws Exception {
        String uri = request.getRequestURI();
        log.info("post handle : {}", uri);
    }

    //뷰 렌더링 이후
    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
        String uri = request.getRequestURI();
        log.info("Response : {}", uri);
    }
}
