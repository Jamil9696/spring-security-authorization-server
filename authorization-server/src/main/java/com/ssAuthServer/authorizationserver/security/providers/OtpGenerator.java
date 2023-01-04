package com.ssAuthServer.authorizationserver.security.providers;

import com.ssAuthServer.authorizationserver.entities.Otp;
import com.ssAuthServer.authorizationserver.repository.OtpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;
import java.util.Base64;

@RequiredArgsConstructor
public class OtpGenerator {



  private final PasswordEncoder passwordEncoder;


  public String generateNewOtp() {

    SecureRandom sr = new SecureRandom();
    byte[] code = new byte[32];
    sr.nextBytes(code);

    String baseEncoded = Base64.getUrlEncoder()
        .withoutPadding()
        .encodeToString(code);

    return passwordEncoder.encode(baseEncoded);

  }
}
