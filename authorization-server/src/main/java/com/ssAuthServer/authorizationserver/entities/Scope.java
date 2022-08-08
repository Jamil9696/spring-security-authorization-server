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
@AllArgsConstructor
@NoArgsConstructor
public class Scope {

  @Id
  @SequenceGenerator(
      name = "scope_id_seq",
      sequenceName = "scope_id_seq",
      allocationSize = 1)
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "scope_id_seq")
  @Column(name = "scope_id")
  private long scopeId;

  @Column(name = "scope_name")
  private String scopeName;

  @OneToMany(mappedBy = "scope", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private Set<RoleManagement> roleManagements = new HashSet<>();
}
