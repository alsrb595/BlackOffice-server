package org.example.blackoffice.controller;

import org.example.blackoffice.dto.JwtTokendto;
import org.example.blackoffice.service.CustomUserDetailsService;
import org.example.blackoffice.service.JwtAuthenticationService;
import org.example.blackoffice.model.Member;
import org.example.blackoffice.service.MemberService;  // 추가
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.example.blackoffice.dto.AuthRequest;
import org.example.blackoffice.dto.Memberdto;

import java.util.Map;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtAuthenticationService jwtAuthenticationService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private MemberService memberService;  // 회원 정보를 가져오기 위한 MemberService 추가

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        // 로그인 성공 후 액세스 토큰과 리프레시 토큰 발급
        String accessToken = jwtAuthenticationService.authenticateAndGenerateToken(authRequest.getEmail());
        String refreshToken = jwtAuthenticationService.generateRefreshToken(authRequest.getEmail());

        // 이메일로 회원 정보 조회
        Member member = memberService.findByEmail(authRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("해당 이메일의 사용자가 없습니다."));

        // JwtToken DTO에 memberId도 포함해서 반환
        JwtTokendto.JwtToken jwtToken = JwtTokendto.JwtToken.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        Memberdto.Post memberDto = new Memberdto.Post(
                member.getId(),
                member.getEmail()
        );

        return ResponseEntity.ok(Map.of("jwtToken", jwtToken, "member", memberDto));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshAccessToken(@RequestBody JwtTokendto.JwtToken jwtToken) {
        // 리프레시 토큰을 사용하여 새로운 액세스 토큰 발급
        String newAccessToken = jwtAuthenticationService.refreshAccessToken(jwtToken.getRefreshToken());

        JwtTokendto.JwtToken newJwtToken = JwtTokendto.JwtToken.builder()
                .grantType("Bearer")
                .accessToken(newAccessToken)
                .refreshToken(jwtToken.getRefreshToken()) // 리프레시 토큰은 그대로 유지
                .build();

        return ResponseEntity.ok(newJwtToken);
    }
}
