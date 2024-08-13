package org.example.blackoffice.dto;

import lombok.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class Memberdto {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class Post{
        @NotBlank(message = "Username cannot be blank")
        private String username;  //id

        @NotBlank(message = "Password cannot be blank")
        private String password;

        @Email(message = "Email should be valid")
        @NotBlank(message = "Email is required")
        private String email;
    }
}
