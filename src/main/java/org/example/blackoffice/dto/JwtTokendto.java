package org.example.blackoffice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

public class JwtTokendto {
    @Builder
    @Data
    @AllArgsConstructor
    public static class JwtToken {
        private String grantType;
        private String accessToken;
        private String refreshToken;
    }
}
