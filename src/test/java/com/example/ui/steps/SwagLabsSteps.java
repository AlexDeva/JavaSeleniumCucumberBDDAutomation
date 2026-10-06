package com.example.ui.steps;

import com.example.ui.pages.SwagLabsPage;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

import java.util.List;

public class SwagLabsSteps {
    private final SwagLabsPage swagLabsPage;

    public SwagLabsSteps(SwagLabsPage swagLabsPage) {
        this.swagLabsPage = swagLabsPage;
    }

    @Given("I open Swag Labs")
    public void openSwagLabs() {
        swagLabsPage.open();
    }

    @When("I log in to Swag Labs as {string} with password {string}")
    public void logIn(String username, String password) {
        swagLabsPage.login(username, password);
    }

    @Then("I should see the {string} login result")
    public void verifyLoginResult(String result) {
        switch (result) {
            case "inventory" -> Assertions.assertEquals(6, swagLabsPage.inventoryCount(),
                    "The standard user should see all six inventory products");
            case "locked out" -> Assertions.assertTrue(
                    swagLabsPage.loginError().contains("locked out"),
                    "The locked-out user should receive a login error");
            default -> throw new IllegalArgumentException("Unknown expected login result: " + result);
        }
    }

    @When("I sort inventory by price from low to high")
    public void sortInventoryByPrice() {
        swagLabsPage.sortPriceLowToHigh();
    }

    @Then("the first inventory item should be {string}")
    public void verifyFirstInventoryItem(String productName) {
        Assertions.assertEquals(productName, swagLabsPage.firstInventoryItemName());
    }

    @When("I add the following products to the cart:")
    public void addProductsToCart(DataTable products) {
        List<String> productNames = products.asList(String.class);
        productNames.forEach(swagLabsPage::addToCart);
        Assertions.assertEquals(String.valueOf(productNames.size()), swagLabsPage.cartItemCount(),
                "The cart badge should reflect the number of added products");
    }

    @When("I add {string} to the cart")
    public void addProductToCart(String productName) {
        swagLabsPage.addToCart(productName);
    }

    @When("I open the shopping cart")
    public void openShoppingCart() {
        swagLabsPage.openCart();
    }

    @Then("the cart should contain:")
    public void verifyCartContents(DataTable products) {
        List<String> expectedProducts = products.asList(String.class);
        Assertions.assertEquals(expectedProducts.size(), swagLabsPage.cartItemNames().size(),
                "The cart should contain exactly the requested products");
        Assertions.assertTrue(swagLabsPage.cartItemNames().containsAll(expectedProducts),
                "The cart should contain every requested product");
    }

    @When("I check out as {string} {string} with postal code {string}")
    public void checkOut(String firstName, String lastName, String postalCode) {
        swagLabsPage.startCheckout();
        swagLabsPage.enterCheckoutInformation(firstName, lastName, postalCode);
        Assertions.assertTrue(swagLabsPage.cartItemNames().contains("Sauce Labs Backpack"),
                "The checkout overview should retain the selected item");
        swagLabsPage.finishCheckout();
    }

    @Then("I should see the order confirmation")
    public void verifyOrderConfirmation() {
        Assertions.assertEquals("Thank you for your order!", swagLabsPage.orderConfirmation());
    }
}
