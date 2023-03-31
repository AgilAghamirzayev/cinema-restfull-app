package com.aistgroup.cinemarestfullapp.service;

import com.aistgroup.cinemarestfullapp.dto.request.MovieRequestModel;
import com.aistgroup.cinemarestfullapp.dto.response.MovieInfoResponseModel;
import com.aistgroup.cinemarestfullapp.dto.response.MovieResponseModel;
import java.util.List;

public interface MovieService {

  List<MovieResponseModel> getMovieInfos();

  MovieInfoResponseModel getMovieById(Long id);

  List<MovieInfoResponseModel> getMovieByNameOrDescription(String name, String description);

  void addMovie(MovieRequestModel requestModel);

  void updateMovie(Long id, MovieRequestModel updatedMovie);

}
