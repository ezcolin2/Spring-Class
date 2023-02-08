public class ExceptionHandlerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        try {
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            JsonResponse newError = JsonResponse.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message("expired token")
                    .httpStatus(HttpStatus.BAD_REQUEST).build();
            setErrorResponse(response, newError);
        }
            //토큰의 유효기간 만료
        catch (JwtException | IllegalArgumentException e) {
            JsonResponse newError = JsonResponse.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message("invalid token")
                    .httpStatus(HttpStatus.BAD_REQUEST).build();
            //유효하지 않은 토큰
            setErrorResponse(response, newError);
        }
    }
    private void setErrorResponse(
            HttpServletResponse response,
            JsonResponse error
    ){
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(error.getHttpStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        try{
            response.getWriter().write(objectMapper.writeValueAsString(error));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
