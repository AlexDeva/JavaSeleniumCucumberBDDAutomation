@playground
Feature: UI Testing Playground interactions
  Validate controls with dynamic identifiers, asynchronous behavior, and challenging layouts.

  @smoke @regression
  Scenario: Locate and click the button with a dynamic ID
    Given I open the "Dynamic ID" page
    When I click the button with a dynamic ID using its stable label
    Then the dynamic ID button should remain available

  @regression
  Scenario: Wait for content loaded through AJAX
    Given I open the "AJAX" page
    When I trigger the AJAX request
    Then I should see the AJAX response

  @regression
  Scenario: Reveal a layered button after clicking the green layer
    Given I open the "Hidden Layers" page
    When I click the green layer button
    Then the blue layer button should be visible

  @regression
  Scenario: Wait for client-side delayed content
    Given I open the "Client Side Delay" page
    When I trigger the client-side calculation
    Then I should see the client-side result

  @smoke @regression
  Scenario: Click a button that ignores DOM events and a button inside scrollbars
    Given I open the "Click" page
    When I click the button that ignores DOM click events
    Then the button should show a successful click
    When I navigate to the "Scrollbars" page
    And I scroll to and click the hiding button
    Then the hiding button should remain available
