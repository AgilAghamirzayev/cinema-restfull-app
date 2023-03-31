package com.aistgroup.cinemarestfullapp.repository;

import com.aistgroup.cinemarestfullapp.entity.Ticket;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

  List<Ticket> getTicketByUserId(Long userId);
}
