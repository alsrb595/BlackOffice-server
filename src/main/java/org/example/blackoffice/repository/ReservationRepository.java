package org.example.blackoffice.repository;

import org.example.blackoffice.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN TRUE ELSE FALSE END " +
            "FROM Reservation r WHERE r.seat.seatId = :seatId " + // :seatId는 메서드의 매개변수로 전달된 값을 의미하는 것 @Param을 통해 전달된 값을 의미함
            "AND :currentTime BETWEEN r.reservation_start_time AND r.reservation_end_time")
    boolean existsBySeatIdAndCurrentTime(@Param("seatId") Long seatId,
                                         @Param("currentTime") LocalDateTime currentTime);

    @Query("select r from Reservation r where r.member.id = :userId order by r.reservation_id desc")
    List<Reservation> findReservationsByUserIdOrderByIdDesc(@Param("userId") Long userId);

}