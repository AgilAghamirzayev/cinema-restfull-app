package com.aistgroup.cinemarestfullapp.service.impl;

import com.aistgroup.cinemarestfullapp.dto.request.SeatRequestModel;
import com.aistgroup.cinemarestfullapp.dto.response.TicketResponseModel;
import com.aistgroup.cinemarestfullapp.entity.Seat;
import com.aistgroup.cinemarestfullapp.entity.Session;
import com.aistgroup.cinemarestfullapp.entity.Ticket;
import com.aistgroup.cinemarestfullapp.entity.User;
import com.aistgroup.cinemarestfullapp.entity.enums.SeatStatus;
import com.aistgroup.cinemarestfullapp.exception.InsufficientBalanceException;
import com.aistgroup.cinemarestfullapp.exception.ResourceNotFoundException;
import com.aistgroup.cinemarestfullapp.exception.SoldOutAlreadyException;
import com.aistgroup.cinemarestfullapp.mapper.TicketMapper;
import com.aistgroup.cinemarestfullapp.repository.SeatRepository;
import com.aistgroup.cinemarestfullapp.repository.SessionRepository;
import com.aistgroup.cinemarestfullapp.repository.TicketRepository;
import com.aistgroup.cinemarestfullapp.repository.UserRepository;
import com.aistgroup.cinemarestfullapp.service.TicketService;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

  private final TicketMapper ticketMapper = TicketMapper.INSTANCE;
  private final TicketRepository ticketRepository;
  private final SessionRepository sessionRepository;
  private final UserRepository userRepository;
  private final SeatRepository seatRepository;


  @Override
  @Transactional
  public void purchaseTicket(List<SeatRequestModel> seatRequests, Long sessionId, Long userId) {
    Session session = sessionRepository.findById(sessionId)
        .orElseThrow(() -> new ResourceNotFoundException("Session not found by id: " + sessionId));

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ResourceNotFoundException("User not found by id: " + userId));

    buyTicket(seatRequests, session, user);

    BigDecimal ticketPrice = session.getPrice().multiply(BigDecimal.valueOf(seatRequests.size()));

    if (user.getBalance().compareTo(ticketPrice) < 0) {
      throw new InsufficientBalanceException("Insufficient balance");
    }

    user.setBalance(user.getBalance().subtract(ticketPrice));
    userRepository.save(user);
    log.info("Ticket(s) successfully purchased");
  }

  private void buyTicket(List<SeatRequestModel> seatRequests, Session session, User user) {
    seatRequests.forEach(seatRequest -> {
      Seat seat = getSeatFromDatabase(session, seatRequest);

      throwIfSeatSold(seat);
      saveSeat(seat);
      saveTicket(session, user, seat);
    });
  }

  private void saveTicket(Session session, User user, Seat seat) {
    Ticket ticket = Ticket.builder()
        .session(session)
        .seat(seat)
        .user(user)
        .build();

    ticketRepository.save(ticket);
    log.info("Ticket saved successfully");
  }

  private void saveSeat(Seat seat) {
    seat.setSeatStatus(SeatStatus.SOLD);
    seatRepository.save(seat);
    log.info("Seat saved successfully");
  }

  private Seat getSeatFromDatabase(Session session, SeatRequestModel seatRequest) {
    return seatRepository.findByRowOfSeatAndColumnOfSeatAndHallId(
        seatRequest.getRowOfSeat(),
        seatRequest.getColumnOfSeat(),
        session.getHall().getId()
    ).orElseThrow(() -> new ResourceNotFoundException(
        "Seat not found by row: " + seatRequest.getRowOfSeat()
            + " column: " + seatRequest.getColumnOfSeat()));
  }

  @Override
  @Transactional
  public void returnTicket(Long ticketId) {
    Ticket ticket = ticketRepository.findById(ticketId)
        .orElseThrow(() -> new ResourceNotFoundException("Ticket not found"));

    User user = returnUserPurchase(ticket);
    Seat seat = setSeatAviable(ticket);

    userRepository.save(user);
    seatRepository.save(seat);
    ticketRepository.delete(ticket);
  }

  @Override
  public List<TicketResponseModel> getTicketsByUser(Long userId) {
    List<Ticket> tickets = ticketRepository.getTicketByUserId(userId);

    return ticketMapper.mapToTicketResponseModelList(tickets);
  }

  @Override
  public TicketResponseModel getTicketById(Long id) {
    Ticket ticket = ticketRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Ticket not found"));

    return ticketMapper.mapToTicketResponseModelList(ticket);
  }

  private void throwIfSeatSold(Seat seat) {
    if (seat.getSeatStatus().equals(SeatStatus.SOLD)) {
      throw new SoldOutAlreadyException("Seat by row: " + seat.getRowOfSeat() + " column: "
          + seat.getColumnOfSeat() + " already sold");
    }
  }

  private Seat setSeatAviable(Ticket ticket) {
    Seat seat = ticket.getSeat();
    seat.setSeatStatus(SeatStatus.AVAILABLE);
    return seat;
  }

  private User returnUserPurchase(Ticket ticket) {
    User user = ticket.getUser();
    BigDecimal ticketPrice = ticket.getSession().getPrice();
    user.setBalance(user.getBalance().add(ticketPrice));
    return user;
  }

}
