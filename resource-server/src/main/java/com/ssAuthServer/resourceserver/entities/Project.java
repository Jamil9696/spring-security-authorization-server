package com.ssAuthServer.resourceserver.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "project")
public class Project {


  @Id
  @SequenceGenerator(
      name = "project_id_seq",
      sequenceName = "project_id_seq",
      allocationSize = 1)
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "project_id_seq")
  @Column(name = "project_id")
  private int projectId;


  private String description;

}
