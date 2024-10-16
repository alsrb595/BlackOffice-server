package org.example.blackoffice.service;

import org.example.blackoffice.dto.SeatDetailDto;
import org.example.blackoffice.model.Seat;
import org.example.blackoffice.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatService {
    private final SeatRepository seatRepository;

    @Autowired
    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public Seat enrollSeat(Seat seatData){
        return seatRepository.save(seatData);
    }

    public List<Seat> getAllSeat(String building){
        return seatRepository.findAllByBuilding(building).orElse(null);
    }

    public List<Seat> getDetailSeat(SeatDetailDto seatDetailDto){
        return seatRepository.findAllByBuildingAndLocation(seatDetailDto.getBuilding(), seatDetailDto.getLocation());
    }
}
