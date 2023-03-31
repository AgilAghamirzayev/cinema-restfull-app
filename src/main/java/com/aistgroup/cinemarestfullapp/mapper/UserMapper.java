package com.aistgroup.cinemarestfullapp.mapper;

import com.aistgroup.cinemarestfullapp.dto.request.UserRegisterRequestModel;
import com.aistgroup.cinemarestfullapp.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserMapper {

  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  User mapToUserEntity(UserRegisterRequestModel requestModel);
}
