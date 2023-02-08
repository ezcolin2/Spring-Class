@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final String secretKey;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorization == null || !authorization.split(" ")[0].equals("Bearer")) {
            logger.info("잘못된 토큰"+authorization);
            filterChain.doFilter(request, response);
            return;
        }
        String token = authorization.split(" ")[1];
        if (JwtUtil.isExpired(token, secretKey)){
            logger.info("만료된 토큰");
            filterChain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken("hello", null, List.of(new SimpleGrantedAuthority("USER")));

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}
