package org.example.blackoffice.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import org.example.blackoffice.model.Task;

import java.util.ArrayList;
import java.util.List;

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

    @OneToOne(mappedBy = "member") // OneToOne 관계에서는 부모 엔티티에 mappedBy를 적어주면 된다.
    @JsonManagedReference
    private MemberInfo memberInfo;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Task> tasks = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Posts> posts = new ArrayList<>();

    @OneToOne(mappedBy = "member")
    @JsonManagedReference(value = "seat-pref")
    private SeatPreference seatPreference;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "member-reservation")  // 회원과의 참조를 설정
    private List<Reservation> reservations = new ArrayList<>();
}
