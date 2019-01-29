@smoke
Feature: Search for items on Etsy
  As a user
  I want to search for different items
  So that I will be able to buy necessary items fast

  Scenario: Search for particular item
    Given I am on the main page
    When I accept terms and conditions
    And I search for "paper toy" items
    Then I see search results
    And I get search results for "paper toy" items