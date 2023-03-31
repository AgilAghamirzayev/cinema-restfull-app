package com.aistgroup.cinemarestfullapp.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.aistgroup.cinemarestfullapp.dto.request.SessionRequestModel;
import com.aistgroup.cinemarestfullapp.dto.response.SessionResponseModel;
import com.aistgroup.cinemarestfullapp.service.SessionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("api/v1/sessions")
public class SessionController {

  private final SessionService sessionService;

  @GetMapping
  ResponseEntity<CollectionModel<SessionResponseModel>> getAllSessions() {
    List<SessionResponseModel> sessions = sessionService.getAllSessions();

    sessions.forEach(session -> session.add(
        linkTo(methodOn(SessionController.class).getSessionById(session.getId())).withSelfRel()));

    CollectionModel<SessionResponseModel> responseModel = CollectionModel.of(sessions);
    responseModel.add(linkTo(methodOn(SessionController.class).getAllSessions()).withSelfRel());
    return ResponseEntity.ok(responseModel);
  }

  @GetMapping("/{id}")
  ResponseEntity<SessionResponseModel> getSessionById(@PathVariable Long id) {
    SessionResponseModel session = sessionService.getSessionById(id);
    session.add(
        linkTo(methodOn(SessionController.class).getSessionById(session.getId())).withSelfRel());
    return ResponseEntity.ok(session);
  }

  @PostMapping
  ResponseEntity<Void> addSession(@RequestBody SessionRequestModel session,
      @RequestParam Long movieId, @RequestParam Long hallId) {
    sessionService.addSession(session, movieId, hallId);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PatchMapping("/{id}")
  ResponseEntity<Void> updateSession(@RequestBody SessionRequestModel session,
      @PathVariable Long id, @RequestParam(required = false) Long movieId,
      @RequestParam(required = false) Long hallId) {

    sessionService.updateSession(session, id, movieId, hallId);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{id}")
  ResponseEntity<Void> deleteSession(@PathVariable Long id) {
    sessionService.deleteSession(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/movies/{id}")
  ResponseEntity<CollectionModel<SessionResponseModel>> getSessionsByMovieId(
      @PathVariable Long id) {
    List<SessionResponseModel> sessions = sessionService.getSessionsByMovieId(id);
    CollectionModel<SessionResponseModel> responseModel = CollectionModel.of(sessions);
    responseModel.add(
        linkTo(methodOn(SessionController.class).getSessionsByMovieId(id)).withSelfRel());
    return ResponseEntity.ok(responseModel);
  }

}
