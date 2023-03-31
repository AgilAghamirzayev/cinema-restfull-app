package com.aistgroup.cinemarestfullapp.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.aistgroup.cinemarestfullapp.dto.request.UserLoginRequestModel;
import com.aistgroup.cinemarestfullapp.dto.request.UserRefreshTokenRequestModel;
import com.aistgroup.cinemarestfullapp.dto.request.UserRegisterRequestModel;
import com.aistgroup.cinemarestfullapp.dto.response.UserRefreshTokenResponseModel;
import com.aistgroup.cinemarestfullapp.dto.response.UserTokenResponseModel;
import com.aistgroup.cinemarestfullapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

  private final UserService userService;

  @PostMapping("/register")
  public ResponseEntity<UserRefreshTokenResponseModel> register(
      @RequestBody UserRegisterRequestModel requestModel) {
    UserRefreshTokenResponseModel register = userService.register(requestModel);
    register.add(linkTo(methodOn(UserController.class).register(requestModel)).withSelfRel());
    return ResponseEntity.ok(register);
  }

  @PostMapping("/login")
  public ResponseEntity<UserTokenResponseModel> login(
      @RequestBody UserLoginRequestModel requestModel) {
    UserTokenResponseModel login = userService.login(requestModel);
    login.add(linkTo(methodOn(UserController.class).login(requestModel)).withSelfRel());

    return ResponseEntity.ok(login);
  }

  @PostMapping("/refresh")
  public ResponseEntity<UserRefreshTokenResponseModel> refreshToken(
      @RequestBody UserRefreshTokenRequestModel requestModel) {
    UserRefreshTokenResponseModel refreshToken = userService.refreshToken(
        requestModel);
    refreshToken.add(
        linkTo(methodOn(UserController.class).refreshToken(requestModel)).withSelfRel());

    return ResponseEntity.ok(refreshToken);
  }
}
