package com.aistgroup.cinemarestfullapp.mapper;

import com.aistgroup.cinemarestfullapp.dto.request.MovieRequestModel;
import com.aistgroup.cinemarestfullapp.dto.response.MovieInfoResponseModel;
import com.aistgroup.cinemarestfullapp.entity.Movie;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface MovieMapper {

  MovieMapper INSTANCE = Mappers.getMapper(MovieMapper.class);

  MovieInfoResponseModel mapToMovieInfoResponseModel(Movie movie);

  List<MovieInfoResponseModel> mapToMovieInfoResponseModelList(List<Movie> movieList);

  Movie mapToMovieEntity(MovieRequestModel requestModel);

}
