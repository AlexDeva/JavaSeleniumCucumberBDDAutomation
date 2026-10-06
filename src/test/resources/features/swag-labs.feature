@swag-labs
Feature: Swag Labs shopping workflows
  Validate authentication, inventory, cart, and checkout behavior.

  @smoke @regression
  Scenario Outline: Log in and validate the resulting page
    Given I open Swag Labs
    When I log in to Swag Labs as "<username>" with password "<password>"
    Then I should see the "<result>" login result

    Examples:
      | username                | password     | result     |
      | standard_user           | secret_sauce | inventory |
      | locked_out_user         | secret_sauce | locked out |
      | problem_user            | secret_sauce | inventory |

  @regression
  Scenario: Sort inventory by price from low to high
    Given I open Swag Labs
    When I log in to Swag Labs as "standard_user" with password "secret_sauce"
    And I sort inventory by price from low to high
    Then the first inventory item should be "Sauce Labs Onesie"

  @smoke @regression
  Scenario: Add multiple products and validate the cart
    Given I open Swag Labs
    When I log in to Swag Labs as "standard_user" with password "secret_sauce"
    And I add the following products to the cart:
      | Sauce Labs Backpack       |
      | Sauce Labs Bike Light     |
    And I open the shopping cart
    Then the cart should contain:
      | Sauce Labs Backpack   |
      | Sauce Labs Bike Light |

  @smoke @regression @checkout
  Scenario: Complete checkout and validate the confirmation
    Given I open Swag Labs
    When I log in to Swag Labs as "standard_user" with password "secret_sauce"
    And I add "Sauce Labs Backpack" to the cart
    And I open the shopping cart
    And I check out as "Alex" "Tester" with postal code "12345"
    Then I should see the order confirmation
