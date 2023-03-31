package com.aistgroup.cinemarestfullapp.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.aistgroup.cinemarestfullapp.dto.request.MovieRequestModel;
import com.aistgroup.cinemarestfullapp.dto.response.MovieInfoResponseModel;
import com.aistgroup.cinemarestfullapp.dto.response.MovieResponseModel;
import com.aistgroup.cinemarestfullapp.service.MovieService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/movies")
public class MovieController {

  private final MovieService movieService;

  @GetMapping("/info")
  ResponseEntity<CollectionModel<MovieResponseModel>> getMovieInfos() {
    List<MovieResponseModel> movies = movieService.getMovieInfos();
    CollectionModel<MovieResponseModel> movieResponseModels = CollectionModel.of(movies);

    movieResponseModels.add(linkTo(methodOn(MovieController.class).getMovieInfos()).withSelfRel());

    return ResponseEntity.ok(movieResponseModels);
  }

  @GetMapping("/{id}")
  ResponseEntity<MovieInfoResponseModel> getMovieById(@PathVariable Long id) {
    MovieInfoResponseModel movieById = movieService.getMovieById(id);

    movieById.add(linkTo(methodOn(CinemaController.class).getCinemaById(id)).withSelfRel());
    return ResponseEntity.ok(movieById);
  }

  @GetMapping
  ResponseEntity<CollectionModel<MovieInfoResponseModel>> searchMovie(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String description) {

    List<MovieInfoResponseModel> moviesInfo = movieService.getMovieByNameOrDescription(name,
        description);

    CollectionModel<MovieInfoResponseModel> movieResponseModels = CollectionModel.of(moviesInfo);
    movieResponseModels.add(
        linkTo(methodOn(MovieController.class).searchMovie(name, description)).withSelfRel());

    return ResponseEntity.ok(movieResponseModels);
  }

  @PostMapping
  ResponseEntity<Void> addMovie(@RequestBody MovieRequestModel responseModel) {
    movieService.addMovie(responseModel);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PatchMapping("/{id}")
  ResponseEntity<Void> updateMovie(
      @PathVariable Long id,
      @RequestBody MovieRequestModel updatedMovie) {
    movieService.updateMovie(id, updatedMovie);
    return ResponseEntity.noContent().build();
  }
}
