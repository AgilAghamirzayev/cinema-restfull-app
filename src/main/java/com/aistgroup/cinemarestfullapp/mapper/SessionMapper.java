package com.aistgroup.cinemarestfullapp.mapper;

import com.aistgroup.cinemarestfullapp.dto.request.SessionRequestModel;
import com.aistgroup.cinemarestfullapp.dto.response.SessionResponseModel;
import com.aistgroup.cinemarestfullapp.entity.Session;
import java.util.List;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring",
    collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED, uses = SessionMapper.class)
public interface SessionMapper {

  SessionMapper INSTANCE = Mappers.getMapper(SessionMapper.class);


  List<SessionResponseModel> mapToSessionResponseModelList(List<Session> sessionList);

  SessionResponseModel mapToSessionResponseModel(Session session);

  Session mapToSessionEntity(SessionRequestModel requestModel);
}
