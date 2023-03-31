package com.aistgroup.cinemarestfullapp.repository;

import com.aistgroup.cinemarestfullapp.entity.Seat;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

  Optional<Seat> findByRowOfSeatAndColumnOfSeatAndHallId(
      Integer rowOfSeat, Integer columnOfSeat, Long hallId);
}
