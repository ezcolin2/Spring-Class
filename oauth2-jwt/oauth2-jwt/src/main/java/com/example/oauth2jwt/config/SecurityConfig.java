package com.example.oauth2jwt.config;

import com.example.oauth2jwt.Oauth2JwtApplication;
import com.example.oauth2jwt.domain.OAuth2MemberService;
import com.example.oauth2jwt.handler.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final OAuth2MemberService oAuth2MemberService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .csrf().disable() // rest api 방식으로 구현을 했기 때문에 csrf가 필요하지 않음
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable() //jwt라 formLogin 필요 없음.
                .httpBasic().disable() //스프링에서 기본적으로 제공하는 로그인 페이지 사용 안 함.
                .authorizeRequests()
                .anyRequest().permitAll().and()
                .oauth2Login()
                .authorizationEndpoint().baseUri("/oauth2/authorization")
                .and().redirectionEndpoint().baseUri("/login/oauth2/code/*")
                .and()
                .userInfoEndpoint().userService(oAuth2MemberService)
                .and().successHandler(oAuth2SuccessHandler).and().build();
    }
}
