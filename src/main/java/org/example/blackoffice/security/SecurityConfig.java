package org.example.blackoffice.security;
import org.example.blackoffice.service.CustomOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService) {
        this.customOAuth2UserService = customOAuth2UserService;
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain_google(HttpSecurity http) throws Exception {
//        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeRequests(authorizeRequests -> authorizeRequests
//                        .requestMatchers("/login", "/error", "/member_info/**").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .oauth2Login(oauth2Login -> oauth2Login
//                        .userInfoEndpoint(userInfoEndpoint ->
//                                userInfoEndpoint.userService(customOAuth2UserService)
//                        )
//                )
//                .logout(logout -> logout
//                        .logoutUrl("/logout")
//                        .logoutSuccessUrl("/login?logout")
//                );
//        return http.build();
//    }
  
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)  // CSRF 보호를 비활성화
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/members/register", "/api/members/login", "/member_info/**", "/login", "/community/**").permitAll()  // 인증 없이 접근 가능
                        .anyRequest().authenticated()  // 다른 모든 요청은 인증 필요
                );

        return http.build();
    }

}


