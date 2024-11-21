package org.example.blackoffice.service;

import org.example.blackoffice.model.Member;
import org.example.blackoffice.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // 데이터베이스에서 이메일로 사용자를 찾는 로직
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // UserDetails로 변환 (사용자 정보가 Spring Security와 호환되도록 변환)
        return new org.springframework.security.core.userdetails.User(
                member.getEmail(),
                member.getPassword(),
                new ArrayList<>()
        );
    }
}
