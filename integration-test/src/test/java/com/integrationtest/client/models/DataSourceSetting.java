package com.integrationtest.client.models;


import lombok.Value;

@Value
public class DataSourceSetting {
  TestUser pmDbo;
  TestUser pmDbu;
  String schema;
  String url;
}
