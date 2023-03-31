package com.aistgroup.cinemarestfullapp.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
public class TicketResponseModel extends RepresentationModel<TicketResponseModel> {

  private Long id;
  private String movieName;
  private String cinemaName;
  private String hallName;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime startTime;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime endTime;
  private SeatResponseModel seat;
}
