package com.aistgroup.cinemarestfullapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.aistgroup.cinemarestfullapp.dto.response.CinemaInfoResponseModel;
import com.aistgroup.cinemarestfullapp.dto.response.CinemaResponseModel;
import com.aistgroup.cinemarestfullapp.entity.Cinema;
import com.aistgroup.cinemarestfullapp.exception.ResourceNotFoundException;
import com.aistgroup.cinemarestfullapp.mapper.CinemaMapper;
import com.aistgroup.cinemarestfullapp.mock.CinemaMockData;
import com.aistgroup.cinemarestfullapp.repository.CinemaRepository;
import com.aistgroup.cinemarestfullapp.service.impl.CinemaServiceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CinemaServiceImplTest {

  @InjectMocks
  private CinemaServiceImpl cinemaServiceIml;

  @Mock
  private CinemaRepository cinemaRepository;

  @Mock
  private CinemaMapper cinemaMapper;

  @Test
  public void givenNoCinemasInDatabase_whenGetAllCinemas_thenReturnsEmptyList() {
    // arrange
    when(cinemaRepository.findAll()).thenReturn(new ArrayList<>());

    // act
    List<CinemaResponseModel> cinemas = cinemaServiceIml.getAllCinemas();

    // assert
    assertEquals(0, cinemas.size());
  }

  @Test
  public void givenCinemasInDatabase_whenGetAllCinemas_thenReturnsListOfCinemas() {
    // arrange
    List<Cinema> cinemaList = CinemaMockData.getCinemaList();
    when(cinemaRepository.findAll()).thenReturn(cinemaList);

    List<CinemaResponseModel> cinemaResponseModelList = new ArrayList<>();
    cinemaResponseModelList.add(new CinemaResponseModel(1L, "Cinema 1", new ArrayList<>()));
    cinemaResponseModelList.add(new CinemaResponseModel(2L, "Cinema 2", new ArrayList<>()));
    when(cinemaMapper.mapToCinemaResponseModelList(cinemaList)).thenReturn(cinemaResponseModelList);

    // act
    List<CinemaResponseModel> cinemas = cinemaServiceIml.getAllCinemas();

    // assert
    assertEquals(2, cinemas.size());
    assertEquals("Cinema 1", cinemas.get(0).getName());
    assertEquals("Cinema 2", cinemas.get(1).getName());
  }

  @Test
  public void givenCinemaInDatabase_whenGetCinemaById_thenReturnsCinema() {
    // arrange

    Cinema cinema = CinemaMockData.getCinemaList().get(0);
    CinemaResponseModel expectedResponseModel = CinemaMockData.getCinemaResponseModel();

    when(cinemaRepository.findById(1L)).thenReturn(Optional.of(cinema));
    when(cinemaMapper.mapToCinemaResponseModel(cinema)).thenReturn(expectedResponseModel);

    // act
    CinemaResponseModel actualResponseModel = cinemaServiceIml.getCinemaById(1L);

    // assert
    assertEquals(expectedResponseModel.getId(), actualResponseModel.getId());
    assertEquals(expectedResponseModel.getName(), actualResponseModel.getName());
  }

  @Test
  public void givenNoCinemaInDatabase_whenGetCinemaById_thenThrowsResourceNotFoundException() {
    // arrange
    when(cinemaRepository.findById(1L)).thenReturn(Optional.empty());

    // act and assert
    assertThrows(ResourceNotFoundException.class, () -> cinemaServiceIml.getCinemaById(1L));
  }

  @Test
  public void givenCinemasAndMoviesInDatabase_whenGetAllCinemaInfos_thenReturnsListOfCinemaInfos() {
    // arrange
    List<Cinema> cinemas = CinemaMockData.getCinemaList();
    List<CinemaInfoResponseModel> expectedResponseModels = CinemaMockData.getCinemaResponseModels();

    when(cinemaRepository.findAll()).thenReturn(cinemas);
    when(cinemaMapper.mapToCinemaInfoResponseModelList(cinemas)).thenReturn(expectedResponseModels);

    // act
    List<CinemaInfoResponseModel> actualResponseModels = cinemaServiceIml.getAllCinemasInfos();

    // assert
    assertEquals(expectedResponseModels.size(), actualResponseModels.size());
    assertEquals(expectedResponseModels.get(0).getId(), actualResponseModels.get(0).getId());
    assertEquals(expectedResponseModels.get(0).getName(), actualResponseModels.get(0).getName());
    assertEquals(expectedResponseModels.get(0).getHalls().size(),
        actualResponseModels.get(0).getHalls().size());
    assertEquals(expectedResponseModels.get(1).getId(), actualResponseModels.get(1).getId());
    assertEquals(expectedResponseModels.get(1).getName(), actualResponseModels.get(1).getName());
    assertEquals(expectedResponseModels.get(1).getHalls().size(),
        actualResponseModels.get(1).getHalls().size());
  }
}