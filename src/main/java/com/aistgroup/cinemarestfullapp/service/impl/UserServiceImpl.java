package com.aistgroup.cinemarestfullapp.service.impl;

import com.aistgroup.cinemarestfullapp.dto.request.UserLoginRequestModel;
import com.aistgroup.cinemarestfullapp.dto.request.UserRefreshTokenRequestModel;
import com.aistgroup.cinemarestfullapp.dto.request.UserRegisterRequestModel;
import com.aistgroup.cinemarestfullapp.dto.response.UserRefreshTokenResponseModel;
import com.aistgroup.cinemarestfullapp.dto.response.UserTokenResponseModel;
import com.aistgroup.cinemarestfullapp.entity.RefreshToken;
import com.aistgroup.cinemarestfullapp.entity.User;
import com.aistgroup.cinemarestfullapp.mapper.UserMapper;
import com.aistgroup.cinemarestfullapp.repository.RefreshTokenRepository;
import com.aistgroup.cinemarestfullapp.repository.UserRepository;
import com.aistgroup.cinemarestfullapp.security.JwtTokenConfiguration;
import com.aistgroup.cinemarestfullapp.service.UserService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserMapper userMapper = UserMapper.INSTANCE;

  private final UserRepository userRepository;
  private final RefreshTokenRepository refreshTokenRepository;

  private final PasswordEncoder passwordEncoder;
  private final JwtTokenConfiguration tokenConfiguration;
  private final AuthenticationManager authenticationManager;

  @Override
  public UserRefreshTokenResponseModel register(UserRegisterRequestModel requestModel) {
    Optional<User> user = userRepository.findByEmail(requestModel.getEmail());

    if (user.isPresent()) {
      throw new RuntimeException("User already exists by email: " + user.get().getEmail());
    }

    User mappedUser = userMapper.mapToUserEntity(requestModel);
    mappedUser.setPassword(passwordEncoder.encode(requestModel.getPassword()));

    userRepository.save(mappedUser);

    log.info("User saved successfully");

    String accessToken = tokenConfiguration.createToken(mappedUser.getUsername());
    String refreshToken = tokenConfiguration.refreshToken(mappedUser.getUsername());

    RefreshToken refreshedToken = RefreshToken.builder()
        .stringToken(refreshToken)
        .user(mappedUser)
        .build();

    refreshTokenRepository.save(refreshedToken);

    return UserRefreshTokenResponseModel.create(accessToken, refreshToken);
  }

  @Override
  public UserTokenResponseModel login(UserLoginRequestModel requestModel) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            requestModel.getEmail(),
            requestModel.getPassword()
        )
    );

    log.info("User successfully authenticated");

    User user = userRepository.findByEmail(requestModel.getEmail())
        .orElseThrow(() -> new UsernameNotFoundException("User not found."));

    String token = tokenConfiguration.createToken(user.getUsername());

    log.info("User logged in successfully");
    return UserTokenResponseModel.create(token);
  }

  @Override
  public UserRefreshTokenResponseModel refreshToken(UserRefreshTokenRequestModel requestModel) {
    int saltLength = 7;
    String username = tokenConfiguration.extractUsername(requestModel.getRefreshToken())
        .substring(saltLength);

    log.info("Username: " + username);

    RefreshToken refreshToken = refreshTokenRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found."));

    String accessToken = tokenConfiguration.createToken(username);
    String refreshedToken = tokenConfiguration.refreshToken(username);

    refreshToken.setStringToken(refreshedToken);
    refreshTokenRepository.save(refreshToken);

    return UserRefreshTokenResponseModel.create(accessToken, refreshedToken);
  }

}
