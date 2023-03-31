package com.aistgroup.cinemarestfullapp.repository.specification;

import com.aistgroup.cinemarestfullapp.entity.Movie;
import org.springframework.data.jpa.domain.Specification;

public interface MovieSpecifications {

  static Specification<Movie> hasName(String name) {
    return (movie, cq, cb) -> cb.equal(movie.get("name"), name);
  }

  static Specification<Movie> descriptionContains(String description) {
    return (movie, cq, cb) -> cb.like(movie.get("description"), "%" + description + "%");
  }
}
