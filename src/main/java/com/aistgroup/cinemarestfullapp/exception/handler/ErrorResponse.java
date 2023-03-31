package com.aistgroup.cinemarestfullapp.exception.handler;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatusCode;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {

  private HttpStatusCode status;
  private String message;

  @JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss")
  private LocalDateTime timestamp;

}
