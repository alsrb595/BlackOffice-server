package org.example.blackoffice.controller;

import org.example.blackoffice.dto.ReservationCreateDto;
import org.example.blackoffice.model.Reservation;
import org.example.blackoffice.service.SeatReserveService;
import org.quartz.SchedulerException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservation")
public class SeatReserveController {
    private final SeatReserveService seatReserveService;

    public SeatReserveController(SeatReserveService seatReserveService) {
        this.seatReserveService = seatReserveService;
    }

    @PostMapping("/reserve_seat/{userId}")
    public ResponseEntity<Reservation> reserveSeat(@PathVariable Long userId, @RequestBody ReservationCreateDto reservationData) throws SchedulerException {
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Custom-Header", "좌석 예약이 성공했습니다.");
        Reservation reservation = seatReserveService.reserveSeat(userId, reservationData);
        return ResponseEntity.ok(reservation);
    }

    @PutMapping("reserve/change/{userId}")
    public ResponseEntity<Reservation> changeReserve(@PathVariable Long userId, @RequestBody ReservationCreateDto reservationData){
        return ResponseEntity.ok(seatReserveService.changeReserve(userId, reservationData));
    }

    @PostMapping("/undo_reservation/{reservation_id}")
    public ResponseEntity<Reservation> undoReserve(@PathVariable Long reservation_id){
        seatReserveService.undoReservation(reservation_id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get_all/{userId}")
    public ResponseEntity<List<Reservation>> getAllReservations(@PathVariable Long userId){
        List<Reservation> reservations = seatReserveService.getAllReservations(userId);
        return ResponseEntity.ok(reservations);
    }

    @DeleteMapping("/delete/{reservation_id}")
    public ResponseEntity<Reservation> deleteReservation(@PathVariable Long reservation_id){
        seatReserveService.deleteReservation(reservation_id);
        return ResponseEntity.ok().build(); // http 응답을 빈 바디로 보낼 때 .build()를 사용
    }
}
