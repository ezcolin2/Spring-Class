package com.example.oauth2.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    public Optional<Member> findByOauth2Id(String oauth2Id);
    public Optional<Member> findByName(String username);
}
