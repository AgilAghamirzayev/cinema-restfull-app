package com.aistgroup.cinemarestfullapp.repository;

import com.aistgroup.cinemarestfullapp.entity.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema, Long> {

//  @Query("""
//      SELECT new com.aistgroup.cinemarestfullapp.dto.response.CinemaResponseModel
//      (c.id, c.name, listagg(new com.aistgroup.cinemarestfullapp.dto.response.HallResponseModel(h)) )
//      from Cinema  c
//      join c.halls h
//      join h.seats s
//      """)
//  List<CinemaResponseModel> findAllCinemasWithHallsAndSeats();

}
