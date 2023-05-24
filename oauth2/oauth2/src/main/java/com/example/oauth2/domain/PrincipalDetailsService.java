package com.example.oauth2.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

// 시큐리티 설정에서 loginProcessingUrl("/login");
// login 요청이 오면 자동으로 UserDetails Service 타입으로 IOC 되어 있는 loadUserByUsername 함수 실행됨
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;


    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {

        Optional<Member> member = memberRepository.findByName(name);
        if (member.isPresent()) {
            return new PrincipalDetails(member.get());
        }
        return null;
    }
}
