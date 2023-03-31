package com.aistgroup.cinemarestfullapp.service;

import com.aistgroup.cinemarestfullapp.dto.request.SeatRequestModel;
import com.aistgroup.cinemarestfullapp.dto.response.TicketResponseModel;
import java.util.List;

public interface TicketService {

  void purchaseTicket(List<SeatRequestModel> seatRequests, Long sessionId, Long userId);

  void returnTicket(Long ticketId);

  List<TicketResponseModel> getTicketsByUser(Long userId);

  TicketResponseModel getTicketById(Long id);
}
