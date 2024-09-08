package org.example.blackoffice.model;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
  
    @Column(unique = true)
    private String email;

    @Column(nullable = true)
    private String password;

    @Column(nullable = true)
    private String googleId;

    @Column(nullable = true)
    private String displayName;

    @OneToOne(mappedBy = "member")
    private MemberInfo memberInfo;
}
