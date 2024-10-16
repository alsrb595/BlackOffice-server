package org.example.blackoffice.service;

import jakarta.transaction.Transactional;
import org.example.blackoffice.config.SeatReservationScheduler;
import org.example.blackoffice.controller.SeatController;
import org.example.blackoffice.controller.SeatWebSocketController;
import org.example.blackoffice.dto.ReservationCreateDto;
import org.example.blackoffice.dto.SeatDto;
import org.example.blackoffice.model.Member;
import org.example.blackoffice.model.Reservation;
import org.example.blackoffice.model.Seat;
import org.example.blackoffice.repository.MemberInfoRepository;
import org.example.blackoffice.repository.MemberRepository;
import org.example.blackoffice.repository.ReservationRepository;
import org.example.blackoffice.repository.SeatRepository;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SeatReserveService {
    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;
    private final MemberInfoRepository memberInfoRepository;
    private final SeatRepository seatRepository;
    private final SeatWebSocketController seatWebSocketController;
    private final SeatController seatController;
    private final SeatReservationScheduler seatReservationScheduler;

    @Autowired
    public SeatReserveService(ReservationRepository reservationRepository, MemberRepository memberRepository, MemberInfoRepository memberInfoRepository, SeatRepository seatRepository, SeatWebSocketController seatWebSocketController, SeatController seatController, SeatReservationScheduler seatReservationScheduler) {
        this.reservationRepository = reservationRepository;
        this.memberRepository = memberRepository;
        this.memberInfoRepository = memberInfoRepository;
        this.seatRepository = seatRepository;
        this.seatWebSocketController = seatWebSocketController;
        this.seatController = seatController;
        this.seatReservationScheduler = seatReservationScheduler;
    }

    @Transactional
    public Reservation reserveSeat(Long userId, ReservationCreateDto reservationData) throws SchedulerException {
        LocalDateTime currentTime = LocalDateTime.now();
        Long seatId = reservationData.getSeatId();
        if(reservationRepository.existsBySeatIdAndCurrentTime(seatId, currentTime)){
            throw new RuntimeException("좌석이 이미 예약되어 있습니다.");
        } // 이미 예약 내역이 존재하면 예약 불가

        Reservation reservation = new Reservation();
        Member member = memberRepository.findById(userId).orElse(null);
        Seat seat = seatRepository.findById(seatId).orElse(null);
        seat.setReserved(true);
        reservation.setMember(member);
        reservation.setSeat(seat);
        reservation.setReservation_start_time(reservationData.getReservation_start_time());
        reservation.setReservation_end_time(reservationData.getReservation_end_time());

        if(reservationData.getDesk_height() == null){
            reservation.setDesk_height(100.0);
        }
        else{
            reservation.setDesk_height(reservationData.getDesk_height());
        }

        if(reservationData.getChair_height() == null)
        {
            reservation.setChair_height(50.0);
        }
        else{
            reservation.setChair_height(reservationData.getChair_height());
        }
        Reservation newReservation = reservationRepository.save(reservation);

        SeatDto seatDto = new SeatDto(seatId, "빌딩", "위치", true);
        seatWebSocketController.sendSeatUpdate(seatDto);

        seatReservationScheduler.scheduleSeatReservationJob(seatId, reservation.getReservation_end_time());
        return newReservation;
    }

    @Transactional
    public Reservation changeReserve(Long userId, ReservationCreateDto reservationData){
        List<Reservation> reservations = reservationRepository.findReservationsByUserIdOrderByIdDesc(userId);
        Reservation reservation = reservations.get(0);
        Seat oldSeat = reservation.getSeat();
        Long oldSeatId = oldSeat.getSeatId();
        String oldLocation = oldSeat.getLocation();
        String oldBuilding = oldSeat.getBuilding();

        LocalDateTime currentTime = LocalDateTime.now();
        Long newSeatId = reservationData.getSeatId();

        if(!reservations.isEmpty()){
            if(!reservationRepository.existsBySeatIdAndCurrentTime(newSeatId, currentTime)){

                Seat seat = seatRepository.findById(newSeatId).orElse(null);
                reservation.setSeat(seat);
                reservation.setReservation_start_time(reservationData.getReservation_start_time());
                reservation.setReservation_end_time(reservationData.getReservation_end_time());
                reservation.setDesk_height(reservationData.getDesk_height());
                reservation.setChair_height(reservationData.getChair_height());

                String newLocation = seat.getLocation();
                String newBuilding = seat.getBuilding();

                SeatDto oldSeatDto = new SeatDto(oldSeatId, oldBuilding, oldLocation, false);
                SeatDto newSeatDto = new SeatDto(newSeatId, newBuilding, newLocation, true);

                seatWebSocketController.sendSeatChange(oldSeatDto, newSeatDto);

            }
            else {
                throw new RuntimeException("예약 변경 실패");
            }
        }

        return reservationRepository.save(reservation);
    }

    public List<Reservation> getAllReservations(Long userId){
        return reservationRepository.findReservationsByUserIdOrderByIdDesc(userId);
    }

    public void deleteReservation(Long reservationId){
        Reservation reservation = reservationRepository.findById(reservationId).orElse(null);
        reservationRepository.delete(reservation);
    }

    @Transactional
    public void undoReservation(Long reservationId){
        Reservation reservation = reservationRepository.findById(reservationId).orElse(null);
        LocalDateTime currentTime = LocalDateTime.now();
        // 여기서 이제 endTime을 currentTime으로 바꿔주고 Seat 테이블의 isReserved를 false로 바꿔줘야 됨
        reservation.setReservation_end_time(currentTime);
        Seat seat = reservation.getSeat();
        seat.setReserved(false);
        SeatDto seatDto = new SeatDto(seat.getSeatId(), seat.getBuilding(), seat.getLocation(), false);
        seatWebSocketController.sendSeatUpdate(seatDto);

        reservationRepository.save(reservation);
    }

}
