package com.aistgroup.cinemarestfullapp.service;

import com.aistgroup.cinemarestfullapp.dto.request.UserLoginRequestModel;
import com.aistgroup.cinemarestfullapp.dto.request.UserRefreshTokenRequestModel;
import com.aistgroup.cinemarestfullapp.dto.request.UserRegisterRequestModel;
import com.aistgroup.cinemarestfullapp.dto.response.UserRefreshTokenResponseModel;
import com.aistgroup.cinemarestfullapp.dto.response.UserTokenResponseModel;

public interface UserService {

  UserRefreshTokenResponseModel register(UserRegisterRequestModel requestModel);

  UserTokenResponseModel login(UserLoginRequestModel requestModel);

  UserRefreshTokenResponseModel refreshToken(UserRefreshTokenRequestModel requestModel);
}
