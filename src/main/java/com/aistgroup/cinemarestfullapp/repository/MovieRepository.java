package com.aistgroup.cinemarestfullapp.repository;

import com.aistgroup.cinemarestfullapp.dto.response.MovieResponseModel;
import com.aistgroup.cinemarestfullapp.entity.Movie;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long>,
    JpaSpecificationExecutor<Movie> {

  @Query("""
      SELECT new com.aistgroup.cinemarestfullapp.dto.response.MovieResponseModel
            (m.id, m.name, s.startTime, s.endTime, c.name, h.name , s.price)
            FROM Session s
            JOIN  s.movie m
            JOIN  s.hall h
            JOIN  h.cinema c
      """)
  List<MovieResponseModel> getMovieSessions();


  @Query("""
      SELECT m
      FROM Movie  m
      WHERE m.id = :id
      """)
  Optional<Movie> getMovieByMovieId(@Param("id") Long id);

  List<Movie> findByNameOrDescriptionContaining(String name, String description);

}
