@outline
Feature: Search for items (table mode)
  As a user
  I want to search for different items
  So that I will be able to buy necessary items fast

  Scenario Outline: Search for items
    Given I am on the main page
    And I accept terms and conditions
    When I search for "<query>" items
    Then I see search results
    And I get search results for "<result>" items

    Examples:
      | query       | result      |
      | leather bag | leather bag |
      | paper toy   | paper toy   |