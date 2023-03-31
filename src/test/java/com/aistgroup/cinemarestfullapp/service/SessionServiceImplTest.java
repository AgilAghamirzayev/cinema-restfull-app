package com.aistgroup.cinemarestfullapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.aistgroup.cinemarestfullapp.dto.request.SessionRequestModel;
import com.aistgroup.cinemarestfullapp.dto.response.SessionResponseModel;
import com.aistgroup.cinemarestfullapp.entity.Hall;
import com.aistgroup.cinemarestfullapp.entity.Movie;
import com.aistgroup.cinemarestfullapp.entity.Session;
import com.aistgroup.cinemarestfullapp.exception.IncorrectTimeRangeException;
import com.aistgroup.cinemarestfullapp.exception.ResourceNotFoundException;
import com.aistgroup.cinemarestfullapp.mock.SessionMockData;
import com.aistgroup.cinemarestfullapp.repository.HallRepository;
import com.aistgroup.cinemarestfullapp.repository.MovieRepository;
import com.aistgroup.cinemarestfullapp.repository.SessionRepository;
import com.aistgroup.cinemarestfullapp.service.impl.SessionServiceImpl;
import java.util.ArrayList;
import java.util.Collections;
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
class SessionServiceImplTest {

  @Mock
  private SessionRepository sessionRepository;

  @Mock
  private MovieRepository movieRepository;

  @Mock
  private HallRepository hallRepository;

  @InjectMocks
  private SessionServiceImpl sessionService;

  private final SessionRequestModel requestModel = SessionMockData.getSessionRequestModel();
  private final Movie movie = SessionMockData.getMovie();
  private final Hall hall = SessionMockData.getHall();
  private final Session session = SessionMockData.getSession();

  @Test
  public void addSession_shouldThrowException_whenStartTimeIsAfterEndTime() {
    requestModel.setStartTime(requestModel.getStartTime().plusMonths(10));
    requestModel.setEndTime(requestModel.getStartTime().minusMinutes(10));

    assertThrows(
        IncorrectTimeRangeException.class,
        () -> sessionService.addSession(requestModel, movie.getId(), hall.getId()));
  }

  @Test
  public void addSession_shouldThrowException_whenMovieNotFound() {

    when(movieRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(
        ResourceNotFoundException.class,
        () -> sessionService.addSession(requestModel, movie.getId(), hall.getId()));
  }

  @Test
  public void addSession_shouldThrowException_whenHallNotFound() {
    when(hallRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class,
        () -> sessionService.addSession(requestModel, movie.getId(), hall.getId()));
  }

  @Test
  public void addSession_shouldThrowException_whenTimeOverlapping() {
    List<Session> sessionList = List.of(SessionMockData.getSession());

    when(sessionRepository.findByMovieId(any(Long.class))).thenReturn(sessionList);

    assertThrows(ResourceNotFoundException.class,
        () -> sessionService.addSession(requestModel, movie.getId(), hall.getId()));
  }

  @Test
  public void givenSessionId_whenGetSessionById_thenReturnSessionResponseModel() {
    // given
    Long sessionId = 1L;

    when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));

    // when
    SessionResponseModel responseModel = sessionService.getSessionById(sessionId);

    // then
    assertNotNull(responseModel);
    assertEquals(session.getId(), responseModel.getId());
    assertEquals(session.getStartTime(), responseModel.getStartTime());
    assertEquals(session.getEndTime(), responseModel.getEndTime());
  }

  @Test
  public void givenExistingSessionId_whenDeleteSessionById_thenSessionIsDeletedFromRepository() {
    // Given
    Long sessionId = 1L;

    given(sessionRepository.findById(sessionId))
        .willReturn(Optional.of(session));

    // When
    sessionService.deleteSession(sessionId);

    // Then
    verify(sessionRepository, times(1)).deleteById(sessionId);
  }

  @Test
  public void givenNonExistingSessionId_whenDeleteSessionById_thenResourceNotFoundExceptionIsThrown() {
    // Given
    Long sessionId = 1L;
    given(sessionRepository.findById(sessionId)).willReturn(Optional.empty());

    // When and then
    assertThrows(ResourceNotFoundException.class, () -> sessionService.deleteSession(sessionId));
  }

  @Test
  public void givenNullSessionId_whenDeleteSessionById_thenIllegalArgumentExceptionIsThrown() {
    // Given
    Long sessionId = null;

    // When and then
    assertThrows(ResourceNotFoundException.class, () -> sessionService.deleteSession(sessionId));
  }

  @Test
  public void givenSessionWithNullId_whenDeleteSession_thenIllegalArgumentExceptionIsThrown() {

    // When and then
    assertThrows(ResourceNotFoundException.class,
        () -> sessionService.deleteSession(session.getId()));
  }

  @Test
  public void givenSessionWithNegativeId_whenDeleteSession_thenIllegalArgumentExceptionIsThrown() {
    // Given
    Session sessionNegative = Session.builder().id(-1L).build();

    // When and then
    assertThrows(ResourceNotFoundException.class,
        () -> sessionService.deleteSession(sessionNegative.getId()));
  }

  @Test
  public void givenSessionWithZeroId_whenDeleteSession_thenIllegalArgumentExceptionIsThrown() {
    // Given
    Session sessionZero = Session.builder().id(0L).build();

    // When and then
    assertThrows(ResourceNotFoundException.class,
        () -> sessionService.deleteSession(sessionZero.getId()));
  }

  @Test
  public void givenExistingMovieId_whenGetSessionsByMovieId_thenReturnsSessionResponseModelList() {
    // Given
    Long movieId = 1L;
    List<Session> sessionList = new ArrayList<>();
    sessionList.add(session);
    sessionList.add(Session.builder().id(2L).movie(SessionMockData.getMovie()).build());
    given(sessionRepository.findByMovieId(movieId)).willReturn(sessionList);

    // When
    List<SessionResponseModel> result = sessionService.getSessionsByMovieId(movieId);

    // Then
    assertEquals(result.size(), sessionList.size());

  }

  @Test
  public void givenNonExistingMovieId_whenGetSessionsByMovieId_thenReturnsEmptyList() {
    // Given
    Long movieId = 1L;
    given(sessionRepository.findByMovieId(movieId)).willReturn(Collections.emptyList());

    // When
    List<SessionResponseModel> result = sessionService.getSessionsByMovieId(movieId);

    // Then
    assertEquals(result, List.of());
  }

}
