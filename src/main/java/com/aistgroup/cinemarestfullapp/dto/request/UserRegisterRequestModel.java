package com.aistgroup.cinemarestfullapp.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterRequestModel {

  private String name;
  private String surname;
  private String email;
  private String password;
}
