package org.example.blackoffice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor // lombok이 생성자를 자동으로 추가해주는 어노테이션임
@AllArgsConstructor // 해당 클래스의 모든 필드를 매개변수로 받는 생성자를 자동으로 생성
public class SeatDto {
    private Long seatId;
    private String building;
    private String location;
    private boolean isReserved;
}
