package com.aistgroup.cinemarestfullapp.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.aistgroup.cinemarestfullapp.dto.response.CinemaInfoResponseModel;
import com.aistgroup.cinemarestfullapp.dto.response.CinemaResponseModel;
import com.aistgroup.cinemarestfullapp.service.CinemaService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/cinemas")
public class CinemaController {

  private final CinemaService cinemaService;

  @GetMapping
  ResponseEntity<CollectionModel<CinemaResponseModel>> getAllCinemas() {
    List<CinemaResponseModel> cinemas = cinemaService.getAllCinemas();

    CollectionModel<CinemaResponseModel> cinemaResponseModels = CollectionModel.of(cinemas);
    cinemaResponseModels.add(
        linkTo(methodOn(CinemaController.class).getAllCinemas()).withSelfRel());

    return ResponseEntity.ok(cinemaResponseModels);
  }

  @GetMapping("/info")
  ResponseEntity<CollectionModel<CinemaInfoResponseModel>> getAllCinemaInfos() {
    List<CinemaInfoResponseModel> cinemas = cinemaService.getAllCinemasInfos();
    CollectionModel<CinemaInfoResponseModel> cinemaResponseModels = CollectionModel.of(cinemas);
    cinemaResponseModels.add(
        linkTo(methodOn(CinemaController.class).getAllCinemaInfos()).withSelfRel());

    return ResponseEntity.ok(cinemaResponseModels);
  }

  @GetMapping("/{id}")
  ResponseEntity<CinemaResponseModel> getCinemaById(@PathVariable Long id) {
    CinemaResponseModel cinemaById = cinemaService.getCinemaById(id);
    cinemaById.add(linkTo(methodOn(CinemaController.class).getCinemaById(id)).withSelfRel());
    return ResponseEntity.ok(cinemaById);
  }

}
