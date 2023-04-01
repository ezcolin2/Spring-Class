@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final OAuth2MemberService oAuth2MemberService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                .cors().and()
                .authorizeRequests()
                .requestMatchers("/private/**").authenticated() //private로 시작하는 uri는 로그인 필수
                .requestMatchers("/admin/**").access("hasRole('ROLE_ADMIN')") //admin으로 시작하는 uri는 관릴자 계정만 접근 가능
                .anyRequest().permitAll() //나머지 uri는 모든 접근 허용
                .and()
                .formLogin() // form login 관련 설정
                .loginPage("/loginForm")
                .usernameParameter("name") // Member가 username이라는 파라미터 갖고 있으면 안 적어도 됨.
                .loginProcessingUrl("/login") // 로그인 요청 받는 url
                .defaultSuccessUrl("/home") // 로그인 성공 후 이동할 url
                .and().oauth2Login()//oauth2 관련 설정
                .loginPage("/loginForm") //로그인이 필요한데 로그인을 하지 않았다면 이동할 uri 설정
                .defaultSuccessUrl("/") //OAuth 구글 로그인이 성공하면 이동할 uri 설정
                .userInfoEndpoint()//로그인 완료 후 회원 정보 받기
                .userService(oAuth2MemberService).and().and().build(); //
    }
}
