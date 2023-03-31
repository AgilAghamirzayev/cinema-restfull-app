package com.aistgroup.cinemarestfullapp.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.aistgroup.cinemarestfullapp.dto.request.SeatRequestModel;
import com.aistgroup.cinemarestfullapp.entity.Seat;
import com.aistgroup.cinemarestfullapp.entity.Session;
import com.aistgroup.cinemarestfullapp.entity.Ticket;
import com.aistgroup.cinemarestfullapp.entity.User;
import com.aistgroup.cinemarestfullapp.exception.ResourceNotFoundException;
import com.aistgroup.cinemarestfullapp.mock.TicketMockData;
import com.aistgroup.cinemarestfullapp.repository.SeatRepository;
import com.aistgroup.cinemarestfullapp.repository.SessionRepository;
import com.aistgroup.cinemarestfullapp.repository.TicketRepository;
import com.aistgroup.cinemarestfullapp.repository.UserRepository;
import com.aistgroup.cinemarestfullapp.service.impl.TicketServiceImpl;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class TicketServiceImplTest {

  @Mock
  private SessionRepository sessionRepository;

  @Mock
  private UserRepository userRepository;

  @Mock
  private TicketRepository ticketRepository;

  @Mock
  private SeatRepository seatRepository;

  @InjectMocks
  private TicketServiceImpl ticketService;


  @Test
  public void givenValidRequest_whenPurchaseTicket_thenReturnSuccess() {
    // Given
    List<SeatRequestModel> seatRequests = TicketMockData.getLisOfSeat();
    User user = TicketMockData.getUser();
    Session session = TicketMockData.getSession();

    when(seatRepository.findByRowOfSeatAndColumnOfSeatAndHallId(seatRequests.get(0).getRowOfSeat(),
        seatRequests.get(0).getColumnOfSeat(), 1L))
        .thenReturn(Optional.of(TicketMockData.getSeat()));
    when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));
    when(userRepository.findById(1L)).thenReturn(Optional.of(user));

    // When
    ticketService.purchaseTicket(seatRequests, 1L, 1L);

    // Then
    verify(sessionRepository).findById(1L);
    verify(userRepository).findById(1L);
    verify(ticketRepository).save(any(Ticket.class));
    verify(seatRepository).save(any(Seat.class));
    verify(userRepository).save(user);
  }

  @Test
  public void givenInvalidSessionId_whenPurchaseTicket_thenThrowResourceNotFoundException() {
    // Given
    List<SeatRequestModel> seatRequests = TicketMockData.getLisOfSeat();

    Long sessionId = 1L;
    Long userId = 1L;
    when(sessionRepository.findById(sessionId)).thenReturn(Optional.empty());

    // When/Then
    assertThrows(ResourceNotFoundException.class,
        () -> ticketService.purchaseTicket(seatRequests, sessionId, userId));
    verify(sessionRepository).findById(sessionId);
    verify(userRepository, never()).findById(any(Long.class));
    verify(ticketRepository, never()).save(any(Ticket.class));
    verify(seatRepository, never()).save(any(Seat.class));
    verify(userRepository, never()).save(any(User.class));
  }

  @Test
  public void givenInvalidUserId_whenPurchaseTicket_thenThrowResourceNotFoundException() {
    // Given

    Long sessionId = 1L;
    Long userId = 1L;
    Session session = new Session();
    when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));
    when(userRepository.findById(userId)).thenReturn(Optional.empty());

    // When/Then
    assertThrows(ResourceNotFoundException.class,
        () -> ticketService.purchaseTicket(TicketMockData.getLisOfSeat(), sessionId, userId));
    verify(sessionRepository).findById(sessionId);
    verify(userRepository).findById(userId);
    verify(ticketRepository, never()).save(any(Ticket.class));
    verify(seatRepository, never()).save(any(Seat.class));
    verify(userRepository, never()).save(any(User.class));
  }

}