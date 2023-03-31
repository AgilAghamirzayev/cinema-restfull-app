package com.aistgroup.cinemarestfullapp.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HallInfoResponseModel {

  private Long id;
  private String name;
  private Integer maxSeats;
}
