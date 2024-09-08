package org.example.blackoffice.service;

import org.example.blackoffice.model.Member;
import org.example.blackoffice.repository.MemberRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    public CustomOAuth2UserService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member verifyGoogleToken(String idToken) throws JwtException {
        JwtDecoder jwtDecoder = JwtDecoders.fromOidcIssuerLocation("https://accounts.google.com");

        Jwt decodedToken = jwtDecoder.decode(idToken);
        String email = decodedToken.getClaim("email");
        String googleId = decodedToken.getClaim("sub");
        String displayName = decodedToken.getClaim("name");

        Optional<Member> userOptional = memberRepository.findByEmail(email);
//      // Optional: Member 타입의 객체를 감싸는 컨테이너임
        // Optional 객체는 Member 타입의 객체가 존재할 수도 안할 수도 있음
        Member member;
        if(userOptional.isPresent()) {
            member = userOptional.get(); // Optional 컨테이너에서 email로 찾은 데이터가 존재하면 그 값을 꺼내오는 것
            member.setDisplayName(displayName);
        }
        else {
            member = new Member();
            member.setEmail(email);
            member.setDisplayName(displayName);
            member.setGoogleId(googleId);
        }

        return memberRepository.save(member);
    }

    public Map<String, Object> getGoogleUserProfile(String accessToken) {
        String url = "https://accounts.google.com/oauth2/v3/userinfo?access_token=" + accessToken;
        RestTemplate restTemplate = new RestTemplate();
        //RestTemplate 객체 생성: Spring에서 제공하는 Rest 클라이언트로 서버와의 HTTP 통신을 간편하게 처리할 수 있게 해줌, Rest API 호출을 단순화 할 수 있다.
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        // getForEntity 메서드는 RestTemplate에서 제공하는 메서드 중 하나로, HTTP GET 요청을 서버에 보내고, 응답을 ResponseEntity<T> 형태로 받는 기능을 함
        // url은 데이터를 요청할 대상 서버의 주소임,
        // Map.class 응답 본문을 어떻게 변환할지 지정해주는 파라미터임, 즉 여기서는 Map<String, Object>로 자동 변환이 되는 것
        return response.getBody();
        // ResponseEntity 객체에서 응답 본문(Body)을 추출하는 메서드
    }

}

//@Override
//public OAuth2User loadUser(OAuth2UserRequest userRequest){
//    OAuth2User oAuth2User = super.loadUser(userRequest);
//
//    Map<String, Object> attributes = oAuth2User.getAttributes();
//
//    String email = (String) attributes.get("email");
//    String googleId = (String) attributes.get("sub");
//    String displayName = (String) attributes.get("name");
//
//    Optional<Member> userOptional = memberRepository.findByEmail(email);
//    Member member;
//    if (userOptional.isPresent()) {
//        member = userOptional.get();
//        member.setDisplayName(displayName);
//    }
//    else{
//        member = new Member();
//        member.setEmail(email);
//        member.setDisplayName(displayName);
//        member.setGoogleId(googleId);
//    }
//
//    memberRepository.save(member);
//
//    return oAuth2User;
//}