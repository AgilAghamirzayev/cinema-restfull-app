package com.aistgroup.cinemarestfullapp.mock;

import com.aistgroup.cinemarestfullapp.dto.request.MovieRequestModel;
import com.aistgroup.cinemarestfullapp.dto.response.MovieResponseModel;
import com.aistgroup.cinemarestfullapp.entity.Cinema;
import com.aistgroup.cinemarestfullapp.entity.Hall;
import com.aistgroup.cinemarestfullapp.entity.Movie;
import com.aistgroup.cinemarestfullapp.entity.Session;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class MovieMockData {



  public static List<MovieResponseModel> getMovieInfos() {
    return List.of(
        MovieResponseModel.builder()
            .id(1L)
            .name("Movie 1")
            .cinemaName("Cinema 1")
            .hallName("Hall 1")
            .sessionPrice(new BigDecimal("12.50"))
            .startTime(LocalDateTime.of(2023, 4, 1, 18, 0))
            .endTime(LocalDateTime.of(2023, 4, 1, 20, 0))
            .build(),

        MovieResponseModel.builder()
            .id(2L)
            .name("Movie 2")
            .cinemaName("Cinema 2")
            .hallName("Hall 2")
            .sessionPrice(new BigDecimal("15.00"))
            .startTime(LocalDateTime.of(2023, 4, 2, 18, 0))
            .endTime(LocalDateTime.of(2023, 4, 2, 20, 0))
            .build()
    );

  }

  public static List<Movie> getMovies() {

    Movie movie1 =
        Movie.builder()
            .id(1L)
            .name("Movie 1")
            .build();

    Session session1 = Session
        .builder()
        .startTime(LocalDateTime.of(2023, 4, 1, 18, 0))
        .endTime(LocalDateTime.of(2023, 4, 1, 20, 0))
        .price(new BigDecimal("12.50"))
        .build();

    Hall hall1 =
        Hall.builder()
            .id(1L)
            .name("Hall 1")
            .maxSeats(50)
            .build();

    Cinema cinema1 = Cinema.builder()
        .id(1L)
        .name("Cinema 1")
        .build();
    hall1.setCinema(cinema1);
    session1.setHall(hall1);
    session1.setMovie(movie1);
    movie1.setSessions(List.of(session1));

    Movie movie2 =
        Movie.builder()
            .name("Movie 2")
            .build();
    Session session2 = Session
        .builder()
        .startTime(LocalDateTime.of(2023, 4, 1, 18, 0))
        .endTime(LocalDateTime.of(2023, 4, 1, 20, 0))
        .price(new BigDecimal("12.50"))
        .build();
    Hall hall2 =
        Hall.builder()
            .id(2L)
            .name("Hall 2")
            .maxSeats(50)
            .build();

    Cinema cinema2 = Cinema.builder()
        .id(2L)
        .name("Cinema 2")
        .build();
    hall2.setCinema(cinema2);
    session2.setHall(hall2);
    session2.setMovie(movie2);
    movie2.setSessions(List.of(session2));

    return List.of(movie1, movie2);
  }


  public static MovieRequestModel getMovieRequestModel() {
    return MovieRequestModel.builder()
        .name("Movie 1")
        .build();
  }
}
