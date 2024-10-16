package org.example.blackoffice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)

@Entity
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue
    private Long reservation_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    @JsonBackReference(value = "member-reservation")  // 회원(Member)와의 참조 설정
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seatId")
    @JsonBackReference(value = "seatData")
    private Seat seat;

    @Column
    private LocalDateTime reservation_start_time;

    @Column
    private LocalDateTime reservation_end_time;

    @Column
    private Double desk_height;

    @Column
    private Double chair_height;
}
