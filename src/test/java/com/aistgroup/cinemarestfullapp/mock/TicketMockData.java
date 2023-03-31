package com.aistgroup.cinemarestfullapp.mock;

import com.aistgroup.cinemarestfullapp.dto.request.SeatRequestModel;
import com.aistgroup.cinemarestfullapp.entity.Hall;
import com.aistgroup.cinemarestfullapp.entity.Seat;
import com.aistgroup.cinemarestfullapp.entity.Session;
import com.aistgroup.cinemarestfullapp.entity.User;
import com.aistgroup.cinemarestfullapp.entity.enums.SeatStatus;
import java.math.BigDecimal;
import java.util.List;

public class TicketMockData {

  public static Session getSession() {

    return Session.builder()
        .id(1L)
        .price(BigDecimal.TEN)
        .hall(Hall.builder().id(1L).build())
        .build();
  }

  public static User getUser() {
    return User.builder()
        .balance(BigDecimal.valueOf(100))
        .build();
  }


  public static Seat getSeat() {
    return Seat.builder().rowOfSeat(1).columnOfSeat(1)
        .seatStatus(SeatStatus.AVAILABLE)
        .id(1L).build();
  }

  public static List<SeatRequestModel> getLisOfSeat() {
    return List.of(
        new SeatRequestModel(1, 1)
    );
  }
}
