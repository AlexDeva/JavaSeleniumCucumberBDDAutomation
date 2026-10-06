package com.example.ui.pages;

import com.example.ui.config.FrameworkConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.math.BigDecimal;
import java.util.List;

public class SwagLabsPage extends BasePage {
    private static final By USERNAME = By.id("user-name");
    private static final By PASSWORD = By.id("password");
    private static final By LOGIN_BUTTON = By.id("login-button");
    private static final By LOGIN_ERROR = By.cssSelector("[data-test='error']");
    private static final By INVENTORY_ITEM = By.className("inventory_item");
    private static final By INVENTORY_ITEM_NAME = By.className("inventory_item_name");
    private static final By INVENTORY_ITEM_PRICE = By.className("inventory_item_price");
    private static final By SORT_CONTROL = By.className("product_sort_container");
    private static final By CART_LINK = By.className("shopping_cart_link");
    private static final By CART_BADGE = By.className("shopping_cart_badge");
    private static final By CHECKOUT_BUTTON = By.id("checkout");
    private static final By CHECKOUT_TITLE = By.className("title");
    private static final By FIRST_NAME = By.id("first-name");
    private static final By LAST_NAME = By.id("last-name");
    private static final By POSTAL_CODE = By.id("postal-code");
    private static final By CONTINUE_BUTTON = By.id("continue");
    private static final By FINISH_BUTTON = By.id("finish");
    private static final By COMPLETE_HEADER = By.className("complete-header");

    public void open() {
        open(FrameworkConfig.get("swag.labs.url"));
    }

    public void login(String username, String password) {
        enterText(USERNAME, username);
        enterText(PASSWORD, password);
        click(LOGIN_BUTTON);
    }

    public int inventoryCount() {
        waitForVisibility(INVENTORY_ITEM);
        return driver().findElements(INVENTORY_ITEM).size();
    }

    public String loginError() {
        return text(LOGIN_ERROR);
    }

    public void sortPriceLowToHigh() {
        new Select(waitForVisibility(SORT_CONTROL)).selectByValue("lohi");
        waitFor().until(driver -> {
            List<WebElement> prices = driver.findElements(INVENTORY_ITEM_PRICE);
            if (prices.size() < 2) {
                return false;
            }
            BigDecimal previousPrice = new BigDecimal(prices.get(0).getText().replace("$", "").trim());
            for (int index = 1; index < prices.size(); index++) {
                BigDecimal currentPrice = new BigDecimal(
                        prices.get(index).getText().replace("$", "").trim());
                if (previousPrice.compareTo(currentPrice) > 0) {
                    return false;
                }
                previousPrice = currentPrice;
            }
            return true;
        });
    }

    public String firstInventoryItemName() {
        return driver().findElements(INVENTORY_ITEM_NAME).get(0).getText().trim();
    }

    public void addToCart(String productName) {
        By addButton = By.xpath("//div[contains(@class,'inventory_item')]" +
                "[.//div[contains(@class,'inventory_item_name') and normalize-space()='" + productName + "']]" +
                "//button");
        click(addButton);
    }

    public String cartItemCount() {
        return text(CART_BADGE);
    }

    public void openCart() {
        click(CART_LINK);
    }

    public List<String> cartItemNames() {
        List<WebElement> items = driver().findElements(INVENTORY_ITEM_NAME);
        return items.stream().map(item -> item.getText().trim()).toList();
    }

    public void startCheckout() {
        clickWithJavaScript(CHECKOUT_BUTTON);
        waitFor().until(ExpectedConditions.textToBe(CHECKOUT_TITLE, "Checkout: Your Information"));
    }

    public void enterCheckoutInformation(String firstName, String lastName, String postalCode) {
        enterText(FIRST_NAME, firstName);
        waitFor().until(ExpectedConditions.textToBePresentInElementValue(FIRST_NAME, firstName));
        enterText(LAST_NAME, lastName);
        waitFor().until(ExpectedConditions.textToBePresentInElementValue(LAST_NAME, lastName));
        enterText(POSTAL_CODE, postalCode);
        waitFor().until(ExpectedConditions.textToBePresentInElementValue(POSTAL_CODE, postalCode));
        clickWithJavaScript(CONTINUE_BUTTON);
        waitFor().until(ExpectedConditions.textToBe(CHECKOUT_TITLE, "Checkout: Overview"));
    }

    public void finishCheckout() {
        clickWithJavaScript(FINISH_BUTTON);
        waitFor().until(ExpectedConditions.urlContains("checkout-complete.html"));
    }

    public String orderConfirmation() {
        return text(COMPLETE_HEADER);
    }
}
