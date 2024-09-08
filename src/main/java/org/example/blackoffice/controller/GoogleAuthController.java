package org.example.blackoffice.controller;

import org.example.blackoffice.model.Member;
import org.example.blackoffice.service.CustomOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/login")
public class GoogleAuthController {
    private final CustomOAuth2UserService customOAuth2UserService;

    @Autowired
    public GoogleAuthController(CustomOAuth2UserService customOAuth2UserService) {
        this.customOAuth2UserService = customOAuth2UserService;
    }

    @PostMapping
    public ResponseEntity<Member> login(@RequestBody Map<String, String> tokens) {
        String idToken = tokens.get("idToken"); // json 중에 키 값이 idToken인 경우
        String accessToken = tokens.get("accessToken");
        System.out.println("idToken: " + idToken);

        try {
            Member member = customOAuth2UserService.verifyGoogleToken(idToken);
            return ResponseEntity.ok(member); // 여기서 로그인 성공하면 플러터 앱으로 200 OK를 반환하는 것
        } catch (Exception e) {
            return ResponseEntity.status(401).body(null);
        }
    }
}
