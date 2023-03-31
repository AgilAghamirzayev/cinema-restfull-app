package com.aistgroup.cinemarestfullapp.dto.response;

import com.aistgroup.cinemarestfullapp.entity.Ticket;
import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseModel {

  private String id;
  private String name;
  private String surname;
  private String email;
  private String password;
  private BigDecimal balance;
  private List<Ticket> tickets;
}
