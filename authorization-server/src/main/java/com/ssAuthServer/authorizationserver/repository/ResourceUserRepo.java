package com.ssAuthServer.authorizationserver.repository;

import com.ssAuthServer.authorizationserver.entities.ResourceUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface ResourceUserRepo extends JpaRepository<ResourceUser, Long> {

   Optional<ResourceUser> findByEmail(String email);

}
