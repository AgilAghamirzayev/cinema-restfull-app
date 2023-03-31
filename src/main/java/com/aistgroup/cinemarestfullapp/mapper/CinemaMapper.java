package com.aistgroup.cinemarestfullapp.mapper;

import com.aistgroup.cinemarestfullapp.dto.response.CinemaInfoResponseModel;
import com.aistgroup.cinemarestfullapp.dto.response.CinemaResponseModel;
import com.aistgroup.cinemarestfullapp.entity.Cinema;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CinemaMapper {

  CinemaMapper INSTANCE = Mappers.getMapper(CinemaMapper.class);


  CinemaResponseModel mapToCinemaResponseModel(Cinema cinema);

  List<CinemaResponseModel> mapToCinemaResponseModelList(List<Cinema> cinemas);

  List<CinemaInfoResponseModel> mapToCinemaInfoResponseModelList(List<Cinema> cinemas);
}
