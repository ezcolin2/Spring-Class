package com.example.oauth2jwt.controller;

import com.example.oauth2jwt.domain.Member;
import com.example.oauth2jwt.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class OAuthController {
    private final BCryptPasswordEncoder encoder;
    private final MemberRepository memberRepository;
    @GetMapping("/loginForm")
    public String home() {
        return "loginForm";
    }
    @GetMapping("/joinForm")
    public String joinForm() {
        return "joinForm";
    }
    @PostMapping("/join")
    public String join(Member member) {
        String rawPwd = member.getPassword();
        System.out.println("member = " + member);
        member.setRole("ROLE_USER");
        member.setPassword(encoder.encode(rawPwd));
        memberRepository.save(member);
        return "redirect:/loginForm";
    }

    @GetMapping("/private")
    public String privatePage() {
        return "privatePage";
    }
    @GetMapping("/admin")
    public String adminPage() {
        return "adminPage";
    }
}
