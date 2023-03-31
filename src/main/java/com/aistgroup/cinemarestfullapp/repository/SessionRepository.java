package com.aistgroup.cinemarestfullapp.repository;

import com.aistgroup.cinemarestfullapp.entity.Session;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

  List<Session> findByMovieId(Long movieId);
}
