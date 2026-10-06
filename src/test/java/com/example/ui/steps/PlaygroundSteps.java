package com.example.ui.steps;

import com.example.ui.pages.PlaygroundPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

public class PlaygroundSteps {
    private final PlaygroundPage playgroundPage;

    public PlaygroundSteps(PlaygroundPage playgroundPage) {
        this.playgroundPage = playgroundPage;
    }

    @Given("I open the {string} page")
    public void openPlaygroundPage(String pageName) {
        playgroundPage.openPage(pageName);
    }

    @When("I navigate to the {string} page")
    public void navigateToPlaygroundPage(String pageName) {
        playgroundPage.openPage(pageName);
    }

    @When("I click the button with a dynamic ID using its stable label")
    public void clickDynamicIdButton() {
        playgroundPage.clickDynamicIdButton();
    }

    @Then("the dynamic ID button should remain available")
    public void verifyDynamicIdButton() {
        Assertions.assertTrue(playgroundPage.dynamicIdButtonAvailable());
    }

    @When("I trigger the AJAX request")
    public void triggerAjaxRequest() {
        playgroundPage.triggerAjaxRequest();
    }

    @Then("I should see the AJAX response")
    public void verifyAjaxResponse() {
        Assertions.assertEquals("Data loaded with AJAX get request.", playgroundPage.waitForAjaxResponse());
    }

    @When("I click the green layer button")
    public void clickGreenButton() {
        playgroundPage.clickGreenButton();
    }

    @Then("the blue layer button should be visible")
    public void verifyBlueButton() {
        Assertions.assertTrue(playgroundPage.blueButtonVisible());
    }

    @When("I trigger the client-side calculation")
    public void triggerClientSideCalculation() {
        playgroundPage.triggerClientSideCalculation();
    }

    @Then("I should see the client-side result")
    public void verifyClientSideResult() {
        Assertions.assertEquals("Data calculated on the client side.", playgroundPage.waitForClientSideResult());
    }

    @When("I click the button that ignores DOM click events")
    public void clickButtonIgnoringDomClick() {
        playgroundPage.clickButtonIgnoringDomClick();
    }

    @Then("the button should show a successful click")
    public void verifySuccessfulClick() {
        Assertions.assertTrue(playgroundPage.buttonIgnoringDomClickSucceeded());
    }

    @When("I scroll to and click the hiding button")
    public void clickHidingButton() {
        playgroundPage.scrollToAndClickHidingButton();
    }

    @Then("the hiding button should remain available")
    public void verifyHidingButton() {
        Assertions.assertTrue(playgroundPage.hidingButtonAvailable());
    }
}
