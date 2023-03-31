package com.aistgroup.cinemarestfullapp.dto.request;

import com.aistgroup.cinemarestfullapp.entity.enums.MovieGenre;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieRequestModel {

  private String name;
  private String description;
  private List<MovieGenre> movieGenres;
  private Integer duration;

}
