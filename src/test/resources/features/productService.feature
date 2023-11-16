Feature: Using product service

  # Ten scenariusz można by zmienić bo jest trochę słaby ale mi się nie chce
  Scenario: I want to retrieve product data
    Given Product id
    When I retrieve data
    Then I receive data