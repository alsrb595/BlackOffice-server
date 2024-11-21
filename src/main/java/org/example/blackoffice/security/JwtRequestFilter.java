package org.example.blackoffice.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.ServletException;
import org.example.blackoffice.service.CustomUserDetailsService;
import org.example.blackoffice.service.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.FilterChain;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {


        if (request.getServletPath().equals("/api/auth/login")) {
            chain.doFilter(request, response);
            return;
        }

        // 요청의 Authorization 헤더에서 JWT 토큰 가져오기
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        // Bearer 토큰인지 확인
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);  // "Bearer " 이후의 토큰 부분만 추출
            try {
                username = jwtTokenUtil.extractUsername(jwt);  // JWT에서 사용자명 추출
            } catch (ExpiredJwtException e) {
                // JWT가 만료된 경우 로그를 남기거나 예외 처리
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("JWT Token has expired");
                return;  // 필터 체인을 중단
            } catch (JwtException e) {
                // JWT가 변조되거나 잘못된 경우 예외 처리
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid JWT Token");
                return;  // 필터 체인을 중단
            }
        }

        // 사용자명을 가지고 있고, 아직 인증되지 않은 경우
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // 토큰이 유효한지 검증
            if (jwt != null && jwtTokenUtil.validateToken(jwt, userDetails.getUsername())) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);  // 인증 설정
            }
        }

        // 다음 필터로 요청 전달
        chain.doFilter(request, response);
    }
}
