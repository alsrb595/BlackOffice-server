package org.example.blackoffice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "member")
public class Member {
    @Id
    private String email;

    @Column(nullable = true)
    private String password;

    @Column(nullable = true)
    private String googleId;

    @Column
    private String displayName;

    @Builder
    public Member(String email, String password, String googleId, String displayName) {
        this.email = email;
        this.password = password;
        this.googleId = googleId;
        this.displayName = displayName;
    }
}
