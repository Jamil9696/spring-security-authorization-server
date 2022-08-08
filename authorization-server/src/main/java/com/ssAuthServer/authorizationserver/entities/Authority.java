package com.ssAuthServer.authorizationserver.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "authority")
public class Authority {

  @Id
  @SequenceGenerator(
      name = "authority_id_seq",
      sequenceName = "authority_id_seq",
      allocationSize = 1)
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "authority_id_seq")
  @Column(name = "authority_id")
  private long authorityID;

  @Column(name = "role_name")
  private String roleName;

  @OneToMany(mappedBy = "authority", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private Set<RoleManagement> roleManagements = new HashSet<>();





}
