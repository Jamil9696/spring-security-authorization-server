package com.integrationtest.client.resources;

import com.integrationtest.client.models.DataSourceSetting;
import com.integrationtest.client.models.TestUser;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;



public class AuthorizationServerDatabase {

  private final Network network;
  private static final String IMAGE_NAME= "postgres";
  private static final String IMAGE_TAG = ":11-alpine";
  private static final String POSTGRES_USER = "postgres";
  private static final String POSTGRES_PW = "postgres";
  private static final String POSTGRES_DB = "amber_dev";
  private static final String POSTGRES_SCHEMA = "pm";
  private static final Integer POSTGRES_PORT = 5432;
  private static final String NETWORK_ALIAS = "postgres";


  private static final PostgreSQLContainer<?> container =
      new PostgreSQLContainer<>(IMAGE_NAME + IMAGE_TAG);

  public AuthorizationServerDatabase(Network network) {
    this.network = network;
  }

  public void start() {

    container
        .withUsername(POSTGRES_USER)
        .withPassword(POSTGRES_PW)
        .withDatabaseName(POSTGRES_DB)
        .withExposedPorts(POSTGRES_PORT)
        .withNetworkAliases(NETWORK_ALIAS)
        .withNetwork(network)
        .withInitScript("db/init.sql");


    container.start();

  }

  public int getContainerRandomPort() {

    return container.getMappedPort(POSTGRES_PORT);
  }

  public int getExposedPort() {

    return POSTGRES_PORT;
  }

  public String getJdbcUrl() {

    return "jdbc:postgresql://" + NETWORK_ALIAS + ":" + POSTGRES_PORT + "/" + POSTGRES_DB;
  }

  public String getContainerNetworkAlias() {

    return NETWORK_ALIAS;
  }

  public DataSourceSetting getDataSourceSetting() {

    return new DataSourceSetting(
        new TestUser("amber_dev_pm_dbo", "admin"),
        new TestUser("amber_dev_pm_dbu", "user"),
        POSTGRES_SCHEMA,
        getJdbcUrl());
  }




}
