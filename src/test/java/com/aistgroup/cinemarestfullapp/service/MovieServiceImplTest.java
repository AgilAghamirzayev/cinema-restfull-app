package com.aistgroup.cinemarestfullapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.aistgroup.cinemarestfullapp.dto.request.MovieRequestModel;
import com.aistgroup.cinemarestfullapp.dto.response.MovieInfoResponseModel;
import com.aistgroup.cinemarestfullapp.dto.response.MovieResponseModel;
import com.aistgroup.cinemarestfullapp.entity.Movie;
import com.aistgroup.cinemarestfullapp.exception.ResourceNotFoundException;
import com.aistgroup.cinemarestfullapp.mapper.MovieMapper;
import com.aistgroup.cinemarestfullapp.mock.MovieMockData;
import com.aistgroup.cinemarestfullapp.repository.MovieRepository;
import com.aistgroup.cinemarestfullapp.service.impl.MovieServiceImpl;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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
class MovieServiceImplTest {

  @InjectMocks
  private MovieServiceImpl movieService;

  @Mock
  private MovieRepository movieRepository;

  @Mock
  private MovieMapper movieMapper;


  @Test
  public void givenGetMovieSessions_whenGetMovieInfos_thenReturnMovieResponseModel() {
    when(movieRepository.getMovieSessions()).thenReturn(MovieMockData.getMovieInfos());

    // Call the method being tested
    List<MovieResponseModel> result = movieService.getMovieInfos();

    // Verify the results
    assertEquals(2, result.size());
    assertEquals("Movie 1", result.get(0).getName());
    assertEquals(LocalDateTime.of(2023, 4, 1, 18, 0), result.get(0).getStartTime());
    assertEquals(LocalDateTime.of(2023, 4, 1, 20, 0), result.get(0).getEndTime());
    assertEquals("Cinema 1", result.get(0).getCinemaName());
    assertEquals("Hall 1", result.get(0).getHallName());
    assertEquals(new BigDecimal("12.50"), result.get(0).getSessionPrice());
    assertEquals("Movie 2", result.get(1).getName());
    assertEquals(LocalDateTime.of(2023, 4, 2, 18, 0), result.get(1).getStartTime());
    assertEquals(LocalDateTime.of(2023, 4, 2, 20, 0), result.get(1).getEndTime());
    assertEquals("Cinema 2", result.get(1).getCinemaName());
    assertEquals("Hall 2", result.get(1).getHallName());
    assertEquals(new BigDecimal("15.00"), result.get(1).getSessionPrice());
  }


  @Test
  void givenValidId_whenGetMovieById_thenReturnMovieInfoResponseModel() {
    // Arrange
    Movie movie = MovieMockData.getMovies().get(0);
    when(movieRepository.getMovieByMovieId(1L)).thenReturn(Optional.of(movie));

    // Act
    MovieInfoResponseModel result = movieService.getMovieById(1L);

    // Assert
    assertEquals(movie.getName(), result.getName());
    assertEquals(movie.getId(), result.getId());
  }

  @Test
  void givenInvalidId_whenGetMovieById_thenThrowResourceNotFoundException() {
    // Arrange
    Long id = 1L;

    when(movieRepository.getMovieByMovieId(id)).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(ResourceNotFoundException.class, () -> movieService.getMovieById(id));
  }

  @Test
  public void givenBothNull_whenGetMovieByNameOrDescription_thenReturnMovieInfoResponseModel() {
    List<MovieInfoResponseModel> responseModelList = movieService.getMovieByNameOrDescription(null,
        null);

    assertNotNull(responseModelList);
    assertEquals(0, responseModelList.size());
  }


  @Test
  public void givenValidMovieRequestModel_whenAddMovie_thenMovieSavedToRepository() {
    // Arrange
    MovieRequestModel requestModel = MovieMockData.getMovieRequestModel();
    Movie movie = new Movie();
    movie.setName("Movie 1");

    when(movieMapper.mapToMovieEntity(requestModel)).thenReturn(movie);

    // Act
    movieService.addMovie(requestModel);

    // Assert
    verify(movieRepository).save(movie);
  }

  @Test
  public void givenExistingMovieIdAndValidRequestModel_whenUpdateMovie_thenMovieUpdated() {
    // Given
    Long id = 1L;
    MovieRequestModel requestModel = MovieMockData.getMovieRequestModel();
    Movie movie = MovieMockData.getMovies().get(1);
    when(movieRepository.findById(id)).thenReturn(Optional.of(movie));

    // When
    movieService.updateMovie(id, requestModel);

    // Then
    verify(movieRepository, times(1)).findById(id);
    verify(movieRepository, times(1)).save(movie);
    assertEquals(requestModel.getName(), movie.getName());
    assertEquals(requestModel.getDescription(), movie.getDescription());
    assertEquals(requestModel.getMovieGenres(), movie.getMovieGenres());
    assertEquals(requestModel.getDuration(), movie.getDuration());
  }

  @Test
  public void givenNonExistingMovieIdAndValidRequestModel_whenUpdateMovie_thenResourceNotFoundExceptionThrown() {
    // Given
    Long id = 1L;
    MovieRequestModel requestModel = MovieMockData.getMovieRequestModel();
    when(movieRepository.findById(id)).thenReturn(Optional.empty());

    // Then
    assertThrows(ResourceNotFoundException.class, () -> movieService.updateMovie(1L, requestModel));
  }

}