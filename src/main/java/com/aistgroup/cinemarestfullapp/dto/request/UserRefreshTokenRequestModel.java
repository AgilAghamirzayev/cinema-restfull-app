package com.aistgroup.cinemarestfullapp.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class UserRefreshTokenRequestModel {

  private String refreshToken;
}
