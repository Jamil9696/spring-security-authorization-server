package com.ssAuthServer.authorizationserver.repository;

import com.ssAuthServer.authorizationserver.entities.Otp;
import org.hibernate.annotations.SQLUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<Otp,Long> {

  @Query("SELECT r.userId from ResourceUser r where r.email = ?1")
  Optional<Long> findUserById(String email);

  @Query("SELECT o FROM Otp o WHERE o.user.userId = ?1")
  Optional<Otp> findOtpByUserId(Long userId);


  @Transactional
  @Modifying
  @Query("UPDATE Otp o SET o.otp = ?1, o.createdAt = ?2 WHERE o.user.userId = ?3")
  void saveNewOtp(String newOtp, String createdAt, Long otpId);


  @Transactional
  @Modifying
  @Query("UPDATE Otp o SET o.otp = '', o.createdAt = '' WHERE o.user.userId = ?1")
  void resetOtp(Long userId);


}
