Feature: Using order service

  Scenario: I want to retrieve order data
    Given Order id
    When I retrieve order data
    Then I receive order data