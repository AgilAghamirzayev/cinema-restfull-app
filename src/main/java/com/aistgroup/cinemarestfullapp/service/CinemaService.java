package com.aistgroup.cinemarestfullapp.service;

import com.aistgroup.cinemarestfullapp.dto.response.CinemaInfoResponseModel;
import com.aistgroup.cinemarestfullapp.dto.response.CinemaResponseModel;
import java.util.List;

public interface CinemaService {

  List<CinemaResponseModel> getAllCinemas();

  CinemaResponseModel getCinemaById(Long id);

  List<CinemaInfoResponseModel> getAllCinemasInfos();
}
