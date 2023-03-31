package com.aistgroup.cinemarestfullapp.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SeatRequestModel {

  private Integer rowOfSeat;
  private Integer columnOfSeat;
}
