package com.aistgroup.cinemarestfullapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor(staticName = "create")
public class UserTokenResponseModel extends RepresentationModel<UserTokenResponseModel> {

  private String token;
}
