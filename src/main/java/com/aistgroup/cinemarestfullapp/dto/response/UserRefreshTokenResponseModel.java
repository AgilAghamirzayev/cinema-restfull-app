package com.aistgroup.cinemarestfullapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class UserRefreshTokenResponseModel extends
    RepresentationModel<UserRefreshTokenResponseModel> {

  private String token;
  private String refreshToken;
}
