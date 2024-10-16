package org.example.blackoffice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "seatPreference")
public class SeatPreference {
    @Id
    @GeneratedValue
    private Long seatPrefId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "memberId")
    @JsonBackReference(value = "seat-pref")
    private Member member;

    @Column
    private Double preferred_desk_height;

    @Column
    private Double preferred_chair_height;
}
