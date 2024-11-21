package org.example.blackoffice.repository;

import org.example.blackoffice.model.Seat;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    Optional<Seat> findById(Long seatId);

    Optional<List<Seat>> findAllByBuilding(String building);

    @Query("select s from Seat s where s.building = :building and s.location = :location")
    List<Seat> findAllByBuildingAndLocation(@Param("building") String building, @Param("location") String location);
}
