@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtUtil jwtUtil;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf().disable() // rest api 방식으로 구현을 했기 때문에 csrf가 필요하지 않음
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable() //jwt라 formLogin 필요 없음.
                .httpBasic().disable() //스프링에서 기본적으로 제공하는 로그인 페이지 사용 안 함.
                .apply(new MyCustomDsl())
                .and().authorizeRequests()
                .requestMatchers("/user/**")
                .access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
                .requestMatchers("/admin/**")
                .access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll().and().build();

    }

    public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {
        @Override
        public void configure(HttpSecurity http) throws Exception {
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
            http
                    .addFilter(new JwtAuthenticationFilter(authenticationManager, jwtUtil));

        }
    }
}
