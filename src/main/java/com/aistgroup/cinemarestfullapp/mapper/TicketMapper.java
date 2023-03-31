package com.aistgroup.cinemarestfullapp.mapper;

import com.aistgroup.cinemarestfullapp.dto.response.TicketResponseModel;
import com.aistgroup.cinemarestfullapp.entity.Ticket;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface TicketMapper {

  TicketMapper INSTANCE = Mappers.getMapper(TicketMapper.class);

  @Mapping(target = "movieName", source = "ticket.session.movie.name")
  @Mapping(target = "cinemaName", source = "ticket.session.hall.cinema.name")
  @Mapping(target = "hallName", source = "ticket.session.hall.name")
  @Mapping(target = "startTime", source = "ticket.session.startTime")
  @Mapping(target = "endTime", source = "ticket.session.endTime")
  TicketResponseModel mapToTicketResponseModelList(Ticket ticket);

  List<TicketResponseModel> mapToTicketResponseModelList(List<Ticket> tickets);
}
