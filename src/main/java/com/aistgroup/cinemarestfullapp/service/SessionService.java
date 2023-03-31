package com.aistgroup.cinemarestfullapp.service;


import com.aistgroup.cinemarestfullapp.dto.request.SessionRequestModel;
import com.aistgroup.cinemarestfullapp.dto.response.SessionResponseModel;
import java.util.List;

public interface SessionService {

  List<SessionResponseModel> getAllSessions();

  SessionResponseModel getSessionById(Long id);

  void addSession(SessionRequestModel session, Long movieId, Long hallId);

  void updateSession(SessionRequestModel session, Long sessionId, Long movieId, Long hallId);

  void deleteSession(Long id);

  List<SessionResponseModel> getSessionsByMovieId(Long movieId);
}
