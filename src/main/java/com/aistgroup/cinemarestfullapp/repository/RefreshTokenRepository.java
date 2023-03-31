package com.aistgroup.cinemarestfullapp.repository;

import com.aistgroup.cinemarestfullapp.entity.RefreshToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

  @Query("SELECT t from RefreshToken t where t.user.email = :email")
  Optional<RefreshToken> findByUsername(@Param("email") String email);
}
