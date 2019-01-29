@real @BDD @OnSale
Feature: Display filter tags on search result page
  As a user
  I want to see tags for applied filters on search results
  So that I can buy items which fit the best for me

  Scenario: Search for leather bag and apply shipping, shop location and item types filters
    Given I am on the main page
    When I accept terms and conditions
    And I search for "leather bags" items
    Then I see search results
    Then I get search results for "leather bags" items
    When I apply "Free shipping" shipping filter
    And I apply "On sale" special offers filter
    And I select "Minsk" as a shop location
    And I apply "Handmade" item type filter
    Then next filter tags are visible:
      | tags                      |
      | Free shipping             |
      | On sale                   |
      | Handmade                  |
      | Items from Minsk, Belarus |