package com.aistgroup.cinemarestfullapp.dto.response;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CinemaInfoResponseModel {

  private Long id;
  private String name;
  List<HallInfoResponseModel> halls = new ArrayList<>();

}
