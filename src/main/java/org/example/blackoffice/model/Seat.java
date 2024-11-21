package org.example.blackoffice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)

@Entity
@Table(name = "seat")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 무조건 1씩 증가하도록 하는 것
    private Long seatId;

    @Column(nullable = false)
    private String building;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private boolean isReserved;

    @OneToMany(mappedBy = "seat", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "seatData")
    @JsonIgnore
    private List<Reservation> reservations = new ArrayList<>();
}
