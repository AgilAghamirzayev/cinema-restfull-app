package com.aistgroup.cinemarestfullapp.dto.response;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CinemaResponseModel extends RepresentationModel<CinemaResponseModel> {

  private Long id;
  private String name;
  List<HallResponseModel> halls = new ArrayList<>();

}
