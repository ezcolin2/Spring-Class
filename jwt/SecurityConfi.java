@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Value("${jwt.secretKey}")
    private  String secretKey;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .httpBasic().disable()//ui 들어오는 거 disable
                .csrf().disable()//크로스사이트 기능
                .cors().and()//크로스사이트 도메인 다를 때 허용
                .authorizeRequests()
//                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .requestMatchers(HttpMethod.POST, "/api/posts").authenticated()
                .anyRequest().permitAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new ExceptionHandlerFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtFilter(secretKey), UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
