package org.example.blackoffice.service;

import org.example.blackoffice.model.Member;
import org.example.blackoffice.repository.MemberRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    public CustomOAuth2UserService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest){
        OAuth2User oAuth2User = super.loadUser(userRequest);

        Map<String, Object> attributes = oAuth2User.getAttributes();

        String email = (String) attributes.get("email");
        String googleId = (String) attributes.get("sub");
        String displayName = (String) attributes.get("name");

        Optional<Member> userOptional = memberRepository.findByEmail(email);
        Member member;
        if (userOptional.isPresent()) {
            member = userOptional.get();
            member.setDisplayName(displayName);
        }
        else{
            member = new Member();
            member.setEmail(email);
            member.setDisplayName(displayName);
            member.setGoogleId(googleId);
        }

        memberRepository.save(member);

        return oAuth2User;
    }
}
