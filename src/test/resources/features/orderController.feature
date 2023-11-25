Feature: User wants to use order API

  Scenario: User can retrieve order by ID
    Given user has a valid order ID
    When user makes GET request for order by ID
    Then system returns order success status
    And system returns order data

  Scenario: User can retrieve order details by order ID
    Given user has a valid order ID for order details
    When user makes GET request for order details by order ID
    Then system returns order success status
    And system returns list of order details data

  Scenario: User wants to create a new order
    Given user has a valid order data
    When user makes POST request to create a new order
    Then system returns order success status

  Scenario: User wants to delete an order
    Given user has a valid order ID
    When user makes DELETE request to delete an order
    Then system returns order success status

  Scenario: User wants to retrieve a non-existing order
    Given user has an invalid order ID
    When user makes bad GET request for order by ID
    Then system returns order failure status

  Scenario: User wants to delete a non-existing order
    Given user has an invalid order ID
    When user makes bad DELETE request for order by ID
    Then system returns order failure status

  Scenario: User wants to save invalid order with API
    Given user has an invalid order data
    When user makes POST request to create a new order
    Then system returns order failure status
