package com.ssAuthServer.authorizationserver.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "otp")
public class Otp {

  @Id
  @SequenceGenerator(
      name = "otp_id_seq",
      sequenceName = "otp_id_seq",
      allocationSize = 1)
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "otp_id_seq")
  @Column(name = "otp_id")
  private long otpId;



  @Column(name = "otp")
  private String otp;

  @Column(name = "created_at")
  private String createdAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private ResourceUser user;


}
