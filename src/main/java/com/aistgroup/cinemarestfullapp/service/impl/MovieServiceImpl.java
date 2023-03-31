package com.aistgroup.cinemarestfullapp.service.impl;

import static com.aistgroup.cinemarestfullapp.repository.specification.MovieSpecifications.descriptionContains;
import static com.aistgroup.cinemarestfullapp.repository.specification.MovieSpecifications.hasName;

import com.aistgroup.cinemarestfullapp.dto.request.MovieRequestModel;
import com.aistgroup.cinemarestfullapp.dto.response.MovieInfoResponseModel;
import com.aistgroup.cinemarestfullapp.dto.response.MovieResponseModel;
import com.aistgroup.cinemarestfullapp.entity.Movie;
import com.aistgroup.cinemarestfullapp.exception.ResourceNotFoundException;
import com.aistgroup.cinemarestfullapp.mapper.MovieMapper;
import com.aistgroup.cinemarestfullapp.repository.MovieRepository;
import com.aistgroup.cinemarestfullapp.service.MovieService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

  private final MovieMapper movieMapper = MovieMapper.INSTANCE;
  private final MovieRepository movieRepository;


  @Override
  public List<MovieResponseModel> getMovieInfos() {
    return movieRepository.getMovieSessions();
  }

  @Override
  public MovieInfoResponseModel getMovieById(Long id) {
    Movie movie = movieRepository.getMovieByMovieId(id)
        .orElseThrow(() -> new ResourceNotFoundException("Movie not found by id " + id));

    MovieInfoResponseModel responseModel = movieMapper.mapToMovieInfoResponseModel(movie);

    log.info("Mapped movie: {}", responseModel);
    return responseModel;
  }

  @Override
  public List<MovieInfoResponseModel> getMovieByNameOrDescription(String name, String description) {

    Specification<Movie> spec = Specification.where(null);

    if (!isNameNullOrEmptyOrBlank(name)) {
      spec = spec.or(hasName(name));
    }

    if (!isDescriptionNullOrEmptyOrBlank(description)) {
      spec = spec.or(descriptionContains(description));
    }

    List<Movie> movieList = movieRepository.findAll(spec);

    List<MovieInfoResponseModel> responseModelList =
        movieMapper.mapToMovieInfoResponseModelList(movieList);
    log.info("Movie list mapped");
    return responseModelList;
  }

  @Override
  public void addMovie(MovieRequestModel requestModel) {
    Movie movie = movieMapper.mapToMovieEntity(requestModel);
    movieRepository.save(movie);
  }

  @Override
  public void updateMovie(Long id, MovieRequestModel requestModel) {
    Movie movie = movieRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Movie not found by id " + id));

    if (requestModel.getMovieGenres() != null) {
      movie.setMovieGenres(requestModel.getMovieGenres());
    }

    if (requestModel.getDescription() != null) {
      movie.setDescription(requestModel.getDescription());
    }

    if (requestModel.getName() != null) {
      movie.setName(requestModel.getName());
    }

    if (requestModel.getDuration() != null) {
      movie.setDuration(requestModel.getDuration());
    }

    log.info("Movie updated {}", movie);
    movieRepository.save(movie);
  }

  private boolean isNameNullOrEmptyOrBlank(String name) {
    return name == null || name.isBlank() || name.isEmpty();
  }

  private boolean isDescriptionNullOrEmptyOrBlank(String description) {
    return description == null || description.isBlank() || description.isEmpty();
  }

}
