Feature: Using product service

  Scenario: I want to retrieve product data
    Given Product id
    When I retrieve data
    Then I receive data