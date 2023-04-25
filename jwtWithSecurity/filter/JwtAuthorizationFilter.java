public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private final  MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, MemberRepository memberRepository, JwtUtil jwtUtil) {
        super(authenticationManager);
        this.memberRepository=memberRepository;
        this.jwtUtil=jwtUtil;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("인증");
        String jwt = request.getHeader("Authorization");
        //jwt가 없거나 Bearer로 시작하지 않으면 거부
        if (jwt == null || !jwt.startsWith("Bearer")) {
            chain.doFilter(request, response);
            return;
        }
        //Bearer를 제거하고 jwt 값만 가져옴
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        String memberName = jwtUtil.getMemberName(token);
        //만료 여부 체크
        if (jwtUtil.isExpired(token)) {
            chain.doFilter(request, response);
            return;
        }
        //이 아래 코드가 실행된다는 뜻은 유효한 토큰이라는 뜻
        //이 코드에는 없는데 필요하다면 유저 존재 여부도 체크해야한다.
        Member memberEntity = memberRepository.findByUsername(memberName);
        PrincipalDetails principalDetails = new PrincipalDetails(memberEntity);
        Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
        //강제로 시큐리티의 세션에 접근하여 Authenticaion 객체를 저장.
        //요청과 응답이 종료되면 세션이 사라진다. session creation policy를 stateless로 설정했기 때문에.
        //이렇게 세션에 등록하는 이유는 권한(user, admin)에 따라 접근을 제한하기 위해서
        //권한 설정이 필요없다면 세션에 저장할 필요가 없다.
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

}
