package org.example.blackoffice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ReservationCreateDto {
    private Long seatId;
    private LocalDateTime reservation_start_time;
    private LocalDateTime reservation_end_time;
    private Double desk_height;
    private Double chair_height;
}
