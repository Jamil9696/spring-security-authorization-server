package com.ssAuthServer.resourceserver.repositories;

import com.ssAuthServer.resourceserver.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepo extends JpaRepository<Project, Integer> {


}
