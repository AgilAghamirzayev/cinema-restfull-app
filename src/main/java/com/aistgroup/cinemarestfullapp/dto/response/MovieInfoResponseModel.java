package com.aistgroup.cinemarestfullapp.dto.response;

import com.aistgroup.cinemarestfullapp.entity.enums.MovieGenre;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MovieInfoResponseModel extends RepresentationModel<MovieInfoResponseModel> {

  private Long id;
  private String name;
  private String description;
  private List<MovieGenre> movieGenres;
  private Integer duration;
}
