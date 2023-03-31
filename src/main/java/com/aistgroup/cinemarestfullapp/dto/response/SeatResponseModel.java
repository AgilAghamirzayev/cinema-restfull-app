package com.aistgroup.cinemarestfullapp.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeatResponseModel {

  private Long id;
  private Integer rowOfSeat;
  private Integer columnOfSeat;
}
