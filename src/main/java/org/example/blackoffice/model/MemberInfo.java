package org.example.blackoffice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class MemberInfo {
    @Id
    private Long id;

    @Column(nullable = false)
    private String user_name;

    @Column(nullable = false)
    private String user_email;

    @Column(nullable = false)
    private String department;

    @Column(nullable = false)
    private Double height;

    @Column(nullable = false)
    private String introduction;

    @Column(nullable = false)
    private String temperature;

    @Column(nullable = false)
    private String workTool;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    @JsonBackReference
    private Member member;
}
