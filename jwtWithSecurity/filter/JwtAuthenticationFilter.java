@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;


    //로그인을 시도할 때 실행
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        ObjectMapper objectMapper = new ObjectMapper();
        MemberDto memberDto = null;
        try {
            memberDto = objectMapper.readValue(request.getInputStream(), MemberDto.class); //request로 들어온 JSON 형식을 MemberDto로 가져옴
        } catch (Exception e) {
            e.printStackTrace();
        }
        //이 코드에서는 유저 정보가 존재하는지 체크하는 과정이 빠져있다. 필요시 추가.
        //토큰 생성
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(memberDto.getUsername(), memberDto.getPassword());
        //authenticationManager의 authenticate 메소드 실행.
        //authenticationManager는 처리할 수 있는 authenticationProvider를 찾아서 authenticationProvider의 authenticate 메소드 실행.
        Authentication authenticate = authenticationManager.authenticate(token);
        //Authentication 객체 반환. 세션에 저장됨.
        return authenticate;

    }
    //인증을 성공하면 실행
    //response Authorization header에 jwt를 담아서 보내줌
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        PrincipalDetails principal = (PrincipalDetails) authResult.getPrincipal();
        Member member = principal.getMember();
        String jwt = jwtUtil.createJwt(member.getUsername(), member.getId());
        response.setHeader("Authorization", jwt);
    }
}
