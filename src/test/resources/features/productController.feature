Feature: User want to use product API

  Scenario: User can retrieve product with API
    Given user has a valid product id
    When user makes GET request
    Then system returns product data and 200 status

  Scenario: User want to save product with API
    Given user has a valid product data
    When user makes POST request
    Then system returns 201 status