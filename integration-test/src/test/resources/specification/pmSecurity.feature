Feature: test authorized access in project management environment

 Background:
    Given user is not authenticated

  Scenario: Unauthorized Access
    When mallory logs in with no-password
    Then response is returned with code 401

  Scenario: User logs in with wrong password
    When amber-user logs in with no-password
    Then response is returned with code 401

  Scenario: Admin logs in with wrong password
    When amber-admin logs in with no-password
    Then response is returned with code 401

  Scenario: mallory accesses resource without authentication
    When mallory logs in with no-password
    And calls /api/v1/pm/get-content from pm-management-service
    Then response is returned with code 401


  Scenario: amber-user accesses amber-admins resource without permission
    When amber-user logs in with password
    And  calls /api/v1/pm/get-content-for-admin from pm-management-service
    Then response is returned with code 403

  Scenario: amber-user accesses successfully his resource
    When amber-user logs in with password
    And  calls /api/v1/pm/get-content from pm-management-service
    Then response is returned with code 200

  Scenario: amber-admin accesses successfully amber-users resource
    When amber-admin logs in with password2
    And  calls /api/v1/pm/get-content from pm-management-service
    Then response is returned with code 200

  Scenario: authorized Access
    Given user is not authenticated
    When amber-user logs in with password
    Then response is returned with code 200

    Given user is not authenticated
    When amber-admin logs in with password2
    Then response is returned with code 200

  Scenario: amber-admin accesses successfully his resource
    When amber-admin logs in with password2
    And  calls /api/v1/pm/get-content-for-admin from pm-management-service
    Then response is returned with code 200



