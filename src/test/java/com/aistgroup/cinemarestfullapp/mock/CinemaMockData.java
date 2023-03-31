package com.aistgroup.cinemarestfullapp.mock;

import com.aistgroup.cinemarestfullapp.dto.response.CinemaInfoResponseModel;
import com.aistgroup.cinemarestfullapp.dto.response.CinemaResponseModel;
import com.aistgroup.cinemarestfullapp.entity.Cinema;
import java.util.ArrayList;
import java.util.List;

public class CinemaMockData {

  public static  List<Cinema>  getCinemaList (){
    return List.of(
        Cinema.builder()
            .id(1L)
            .name("Cinema 1")
            .halls(new ArrayList<>())
            .build(),
        Cinema.builder()
            .id(2L)
            .name("Cinema 2")
            .halls(new ArrayList<>())
            .build()
    );
  }

  public static CinemaResponseModel getCinemaResponseModel() {
   return CinemaResponseModel.builder()
        .id(1L)
        .name("Cinema 1")
       .build();
  }

  public static List<CinemaInfoResponseModel> getCinemaResponseModels() {
    return List.of(
        CinemaInfoResponseModel.builder()
            .id(1L)
            .name("Cinema 1")
            .halls(new ArrayList<>())
            .build(),
        CinemaInfoResponseModel.builder()
            .id(2L)
            .name("Cinema 2")
            .halls(new ArrayList<>())
            .build()
    );
  }
}
