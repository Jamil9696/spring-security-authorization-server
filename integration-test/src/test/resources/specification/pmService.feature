Feature: test pm-service functionality

  Background:
    Given user is not authenticated

  Scenario: all projects are displayed
    When amber-user logs in with password
    And  calls /api/v1/pm/projects from pm-management-service
    Then response contains list with 40 projects
    And response is returned with code 200
