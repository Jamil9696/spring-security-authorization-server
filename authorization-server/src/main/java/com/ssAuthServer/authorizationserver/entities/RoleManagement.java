package com.ssAuthServer.authorizationserver.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Table(name = "role_management")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleManagement{

  @Id
  @SequenceGenerator(
      name = "role_management_id_seq",
      sequenceName = "role_management_id_seq",
      allocationSize = 1)
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "role_management_id_seq")
  @Column(name = "role_management_id")
  private long roleManagementId;

  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinColumn(name = "user_id")
  private ResourceUser user;

  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinColumn(name = "authority_id")
  private Authority authority;



}
