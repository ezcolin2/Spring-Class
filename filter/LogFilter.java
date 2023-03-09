@Slf4j
public class LogFilter implements Filter {
    private static final String[] whiteList = {"/except", "/no-filter"}; //filter를 적용하지 않을 uri
    @Override
    public void init(FilterConfig filterConfig) throws ServletException { //서블릿 컨테이너 생성시 호출
        log.info("init method");
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException { //요청이 올 때마다 호출
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String uri = httpRequest.getRequestURI();
        try {
            if (!PatternMatchUtils.simpleMatch(whiteList, uri)) {
                log.info("Request URI : {}", uri);

            }
            chain.doFilter(request, response);
        } catch (Exception e) {
            throw e;
        } finally{
            log.info("Response : {}", uri);
        }
    }

    @Override
    public void destroy() {//서블릿 컨테이너 종료될 때 호출
        log.info("destroy method");
    }
}
