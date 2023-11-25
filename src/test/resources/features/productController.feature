Feature: User want to use product API

  Scenario: User can retrieve product with API
    Given user has a page, size and sort
    When user makes GET paginated request
    Then system returns success status
    And system returns list of products data

  Scenario: User can retrieve list of products with API
    Given user has a valid product id
    When user makes GET request
    Then system returns success status
    And system returns product data

  Scenario: User want to save product with API
    Given user has a valid product data
    When user makes POST request
    Then system returns success status

  Scenario: User want to edit product with API
    Given user has a valid product data
    When user makes PUT request
    Then system returns success status

  Scenario: User want to patch product with API
    Given user has a valid product data
    When user makes PATCH request
    Then system returns success status

  Scenario: User want to delete product with API
    Given user has a valid product id
    When user makes DELETE request
    Then system returns success status

  Scenario: User want to retrieve non-existing product with API
    Given user has an invalid product id
    When user makes bad GET request
    Then system returns failure status

  Scenario: User want to save invalid product with API
    Given user has an invalid product data
    When user makes POST request
    Then system returns failure status

  Scenario: User want to delete non-existing product with API
    Given user has an invalid product id
    When user makes bad DELETE request
    Then system returns failure status

  Scenario: User want to edit invalid product with API
    Given user has an invalid product data
    When user makes PUT request
    Then system returns failure status

  Scenario: User want to patch invalid product with API
    Given user has an invalid product data
    When user makes PATCH request
    Then system returns failure status

