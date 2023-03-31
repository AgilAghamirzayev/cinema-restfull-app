package com.aistgroup.cinemarestfullapp.service.impl;

import com.aistgroup.cinemarestfullapp.dto.request.SessionRequestModel;
import com.aistgroup.cinemarestfullapp.dto.response.SessionResponseModel;
import com.aistgroup.cinemarestfullapp.entity.Hall;
import com.aistgroup.cinemarestfullapp.entity.Movie;
import com.aistgroup.cinemarestfullapp.entity.Session;
import com.aistgroup.cinemarestfullapp.exception.IncorrectTimeRangeException;
import com.aistgroup.cinemarestfullapp.exception.ResourceNotFoundException;
import com.aistgroup.cinemarestfullapp.mapper.SessionMapper;
import com.aistgroup.cinemarestfullapp.repository.HallRepository;
import com.aistgroup.cinemarestfullapp.repository.MovieRepository;
import com.aistgroup.cinemarestfullapp.repository.SessionRepository;
import com.aistgroup.cinemarestfullapp.service.SessionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

  private final SessionMapper sessionMapper = SessionMapper.INSTANCE;
  private final SessionRepository sessionRepository;
  private final MovieRepository movieRepository;
  private final HallRepository hallRepository;


  @Override
  public List<SessionResponseModel> getAllSessions() {
    List<Session> sessionList = sessionRepository.findAll();
    return sessionMapper.mapToSessionResponseModelList(sessionList);
  }

  @Override
  public SessionResponseModel getSessionById(Long id) {
    Session session = sessionRepository.findById(id).orElseThrow(() ->
        new ResourceNotFoundException("Could not find session by id " + id));

    return sessionMapper.mapToSessionResponseModel(session);
  }

  @Override
  @Transactional
  public void addSession(SessionRequestModel requestModel, Long movieId, Long hallId) {

    if (requestModel.getStartTime().isAfter(requestModel.getEndTime())) {
      throw new IncorrectTimeRangeException("Start time must be before end time");
    }

    Movie movie = movieRepository.findById(movieId).orElseThrow(()
        -> new ResourceNotFoundException("Could not find movie by id " + movieId));

    Hall hall = hallRepository.findById(hallId).orElseThrow(() ->
        new ResourceNotFoundException("Could not find movie by id " + hallId));

    throwExceptionIfTimeOverlapping(requestModel, hallId, hall);

    Session session = sessionMapper.mapToSessionEntity(requestModel);

    session.setMovie(movie);
    session.setHall(hall);
    sessionRepository.save(session);
  }

  @Override
  @Transactional
  public void updateSession(SessionRequestModel requestModel, Long sessionId,
      Long movieId, Long hallId) {

    Session session = sessionRepository.findById(sessionId).orElseThrow(() ->
        new ResourceNotFoundException("Could not find session by id " + sessionId));

    if (movieId != null) {
      Movie movie = movieRepository.findById(movieId).orElseThrow(() ->
          new ResourceNotFoundException("Could not find movie by id " + movieId));
      session.setMovie(movie);
    }

    if (hallId != null) {
      Hall hall = hallRepository.findById(hallId).orElseThrow(() ->
          new ResourceNotFoundException("Could not find movie by id " + hallId));

      throwExceptionIfTimeOverlapping(requestModel, hallId, hall);

      session.setHall(hall);
    }

    if (requestModel.getPrice() != null) {
      session.setPrice(requestModel.getPrice());
    }

    if (requestModel.getStartTime() != null) {
      session.setStartTime(requestModel.getStartTime());
    }

    if (requestModel.getEndTime() != null) {
      session.setEndTime(requestModel.getEndTime());
    }

    if (requestModel.getSessionType() != null) {
      session.setSessionType(requestModel.getSessionType());
    }

    sessionRepository.save(session);
  }

  @Override
  public void deleteSession(Long id) {
    sessionRepository.findById(id).orElseThrow(() ->
        new ResourceNotFoundException("Could not find session by id " + id));

    sessionRepository.deleteById(id);
  }

  @Override
  public List<SessionResponseModel> getSessionsByMovieId(Long movieId) {
    List<Session> sessionList = sessionRepository.findByMovieId(movieId);
    List<SessionResponseModel> sessionsResponse =
        sessionMapper.mapToSessionResponseModelList(sessionList);
    log.info("Session mapped");
    return sessionsResponse;
  }

  private void throwExceptionIfTimeOverlapping(SessionRequestModel requestModel,
      Long hallId, Hall hall) {
    hall.getSessions().forEach(session -> {

          if (requestModel.getStartTime().isBefore(session.getEndTime()) &&
              requestModel.getEndTime().isAfter(session.getStartTime())) {
            throw new IncorrectTimeRangeException("Time taken for another session for this hall");
          }

          if (requestModel.getStartTime().isEqual(session.getStartTime()) ||
              requestModel.getEndTime().isEqual(session.getEndTime())) {
            throw new IncorrectTimeRangeException(
                "Start or End time in another time range session for hall id: " + hallId);
          }
        }
    );
  }
}
