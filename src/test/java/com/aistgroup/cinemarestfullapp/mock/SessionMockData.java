package com.aistgroup.cinemarestfullapp.mock;

import com.aistgroup.cinemarestfullapp.dto.request.SessionRequestModel;
import com.aistgroup.cinemarestfullapp.entity.Hall;
import com.aistgroup.cinemarestfullapp.entity.Movie;
import com.aistgroup.cinemarestfullapp.entity.Session;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class SessionMockData {

  public static Movie getMovie() {
    return Movie.builder()
        .id(1L)
        .name("The Matrix")
        .description(
            "A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.")
        .build();
  }


  public static Hall getHall() {
    return Hall.builder()
        .id(1L)
        .name("Hall 1")
        .maxSeats(50)
        .build();
  }


  public static Session getSession() {
    return Session.builder()
        .id(1L)
        .hall(getHall())
        .movie(getMovie())
        .startTime(LocalDateTime.of(2023, 4, 1, 10, 0, 0))
        .endTime(LocalDateTime.of(2023, 4, 1, 11, 30, 0))
        .price(new BigDecimal("15.0"))
        .sessionType("3D")
        .build();
  }

  public static SessionRequestModel getSessionRequestModel() {
    return SessionRequestModel.builder()
        .startTime(LocalDateTime.of(2023, 4, 1, 10, 0, 0))
        .endTime(LocalDateTime.of(2023, 4, 1, 11, 30, 0))
        .price(new BigDecimal("15.0"))
        .sessionType("3D")
        .build();
  }
}
