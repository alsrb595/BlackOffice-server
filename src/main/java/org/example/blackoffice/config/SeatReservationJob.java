package org.example.blackoffice.config;

import org.example.blackoffice.controller.SeatWebSocketController;
import org.example.blackoffice.dto.SeatDto;
import org.example.blackoffice.model.Seat;
import org.example.blackoffice.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SeatReservationJob implements Job {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final SeatWebSocketController seatWebSocketController;
    private final SeatRepository seatRepository;
    public SeatReservationJob(SeatWebSocketController seatWebSocketController, SeatRepository seatRepository) {
        this.seatWebSocketController = seatWebSocketController;
        this.seatRepository = seatRepository;
    }

    @Override // Job 인터페이스에 있는 함수인듯
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Long seatId = context.getJobDetail().getJobDataMap().getLong("seatId");
//        LocalDateTime reservationEndTime = (LocalDateTime) context.getJobDetail().getJobDataMap().get("reservationEndTime");
        String reservationEndTimeString = context.getJobDetail().getJobDataMap().getString("reservationEndTime");

        // String을 LocalDateTime으로 변환
        LocalDateTime reservationEndTime = LocalDateTime.parse(reservationEndTimeString, formatter);

        if (reservationEndTime.isBefore(LocalDateTime.now())) {
            Seat seat = seatRepository.findById(seatId).orElse(null);
            seat.setReserved(false);
            SeatDto seatDto = new SeatDto(seat.getSeatId(), seat.getBuilding(), seat.getLocation(), false);
            seatWebSocketController.sendSeatUpdate(seatDto);
            seatRepository.save(seat);
        }

    }
}
