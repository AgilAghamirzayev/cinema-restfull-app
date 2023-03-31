package com.aistgroup.cinemarestfullapp.repository;

import com.aistgroup.cinemarestfullapp.entity.Hall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HallRepository extends JpaRepository<Hall, Long> {

}
