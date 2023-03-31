package com.aistgroup.cinemarestfullapp.dto.response;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HallResponseModel {

  private Long id;
  private String name;
  private Integer maxSeats;
  private List<SeatResponseModel> seats;
}
