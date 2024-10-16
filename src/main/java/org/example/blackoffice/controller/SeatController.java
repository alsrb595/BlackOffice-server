package org.example.blackoffice.controller;

import org.example.blackoffice.dto.PositionDto;
import org.example.blackoffice.dto.SeatDetailDto;
import org.example.blackoffice.model.Seat;
import org.example.blackoffice.service.SeatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seat")
public class SeatController {
    private final SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @GetMapping("/get_position")
    public ResponseEntity<PositionDto> getPosition() {
        PositionDto positionDto = new PositionDto();
        positionDto.setITBT("4층, 오픈허브, 크리에티브랩");
        positionDto.set제2공학관("1층, 2층, 3층");
        positionDto.set백남학술정보관("1열람실, 2열람실, 3열람심");
        positionDto.setHomlz("2층");

        return ResponseEntity.ok(positionDto);
    }
    @PostMapping("/enroll_seat")
    public ResponseEntity<Seat> enrollSeat(@RequestBody Seat seatData) {
        return ResponseEntity.ok(seatService.enrollSeat(seatData));
    }

    @GetMapping("/all_seat/{building}")
    public ResponseEntity<List<Seat>> getAllSeat(@PathVariable String building) {
        List<Seat> seats = seatService.getAllSeat(building);
        return ResponseEntity.ok(seats);
    }

    @GetMapping("/all_seat/detail")
    public ResponseEntity<List<Seat>> getDetailSeat(@RequestBody SeatDetailDto seatDetailDto) {
        List<Seat> seats = seatService.getDetailSeat(seatDetailDto);
        return ResponseEntity.ok(seats);
    }

}
