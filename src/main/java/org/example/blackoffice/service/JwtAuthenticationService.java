package org.example.blackoffice.service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtAuthenticationService {

    private final String SECRET_KEY = "e1786d3eef6cdb4f12816883a096863de77120cb2f41cbbaf254d83e1bc1228f";  // 실제로는 환경 변수로 설정하는 것이 좋습니다

    // 로그인 시 액세스 토큰을 생성하는 메서드
    public String authenticateAndGenerateToken(String email) {
        // 비밀번호 검증 로직은 외부에서 처리되었다고 가정하고, 여기서는 토큰만 생성
        return createToken(new HashMap<>(), email, 1000 * 60 * 60); // 1시간 유효한 액세스 토큰
    }

    // 리프레시 토큰을 이용해 새로운 액세스 토큰 발급
    public String refreshAccessToken(String refreshToken) {
        try {
            // 리프레시 토큰에서 사용자 이메일 추출
            String email = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(refreshToken)
                    .getBody()
                    .getSubject();

            // 새로운 액세스 토큰 발급
            return createToken(new HashMap<>(), email, 1000 * 60 * 60); // 1시간 유효한 액세스 토큰

        } catch (ExpiredJwtException e) {
            // 리프레시 토큰이 만료된 경우 예외 처리
            throw new RuntimeException("Refresh token is expired");
        } catch (Exception e) {
            throw new RuntimeException("Invalid refresh token");
        }
    }

    // 리프레시 토큰 생성 메서드
    public String generateRefreshToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, email, 1000 * 60 * 60 * 24 * 7); // 7일간 유효한 리프레시 토큰
    }

    // JWT 토큰 생성 로직
    private String createToken(Map<String, Object> claims, String subject, long expirationTime) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
}
