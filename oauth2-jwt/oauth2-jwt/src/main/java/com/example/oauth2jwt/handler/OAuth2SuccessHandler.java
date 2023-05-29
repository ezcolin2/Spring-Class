package com.example.oauth2jwt.handler;

import com.example.oauth2jwt.domain.PrincipalDetails;
import com.example.oauth2jwt.oauth2.OAuth2MemberInfo;
import com.example.oauth2jwt.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        System.out.println(principal.getAttributes());
        System.out.println("oauth2 인증 성공");
        String jwt = jwtUtil.createJwt(principal.getMember().getOauth2Id(), principal.getMember().getId());
        String redirectUri = UriComponentsBuilder.fromUriString("/oauth2/login")
                .queryParam("accessToken", jwt).build().toUriString();
        getRedirectStrategy().sendRedirect(request, response, redirectUri);
    }
}
