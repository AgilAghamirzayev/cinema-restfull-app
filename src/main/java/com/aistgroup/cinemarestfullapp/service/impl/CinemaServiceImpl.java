package com.aistgroup.cinemarestfullapp.service.impl;

import com.aistgroup.cinemarestfullapp.dto.response.CinemaInfoResponseModel;
import com.aistgroup.cinemarestfullapp.dto.response.CinemaResponseModel;
import com.aistgroup.cinemarestfullapp.entity.Cinema;
import com.aistgroup.cinemarestfullapp.exception.ResourceNotFoundException;
import com.aistgroup.cinemarestfullapp.mapper.CinemaMapper;
import com.aistgroup.cinemarestfullapp.repository.CinemaRepository;
import com.aistgroup.cinemarestfullapp.service.CinemaService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CinemaServiceImpl implements CinemaService {

  private final CinemaMapper cinemaMapper = CinemaMapper.INSTANCE;
  private final CinemaRepository cinemaRepository;

  @Override
  public List<CinemaResponseModel> getAllCinemas() {
    List<Cinema> cinemas = cinemaRepository.findAll();
    return cinemaMapper.mapToCinemaResponseModelList(cinemas);
  }

  @Override
  public CinemaResponseModel getCinemaById(Long id) {
    Cinema cinema = cinemaRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Cinema not found by id " + id));

    return cinemaMapper.mapToCinemaResponseModel(cinema);
  }

  @Override
  public List<CinemaInfoResponseModel> getAllCinemasInfos() {
    List<Cinema> cinemas = cinemaRepository.findAll();
    return cinemaMapper.mapToCinemaInfoResponseModelList(cinemas);
  }

}
