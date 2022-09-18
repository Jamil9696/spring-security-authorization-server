package com.ssAuthServer.authorizationserver.repository;

import com.ssAuthServer.authorizationserver.entities.ResourceUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ResourceUserRepo extends JpaRepository<ResourceUser, Integer> {

   Optional<ResourceUser> findByEmail(String email);


}
