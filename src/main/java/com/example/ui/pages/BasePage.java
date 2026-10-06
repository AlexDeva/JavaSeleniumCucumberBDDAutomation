package com.example.ui.pages;

import com.example.ui.config.FrameworkConfig;
import com.example.ui.driver.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.logging.Logger;

public abstract class BasePage {
    private static final Logger LOGGER = Logger.getLogger(BasePage.class.getName());

    protected WebDriver driver() {
        return DriverManager.getDriver();
    }

    protected WebDriverWait waitFor() {
        return new WebDriverWait(driver(), Duration.ofSeconds(FrameworkConfig.timeoutSeconds()));
    }

    protected void open(String url) {
        driver().get(url);
    }

    protected WebElement waitForVisibility(By locator) {
        return waitFor().until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void click(By locator) {
        waitFor().until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    protected void clickWithJavaScript(By locator) {
        WebElement element = waitForVisibility(locator);
        ((JavascriptExecutor) driver()).executeScript("arguments[0].click();", element);
    }

    protected void enterText(By locator, String value) {
        WebElement element = waitForVisibility(locator);
        element.click();
        element.clear();
        driver().findElement(locator).sendKeys(value);
        if (!value.equals(driver().findElement(locator).getAttribute("value"))) {
            LOGGER.warning("WebDriver sendKeys did not populate " + locator +
                    "; using the native input value setter as a fallback");
            ((JavascriptExecutor) driver()).executeScript(
                    "const input = arguments[0];" +
                            "const setValue = Object.getOwnPropertyDescriptor(" +
                            "window.HTMLInputElement.prototype, 'value').set;" +
                            "setValue.call(input, arguments[1]);" +
                            "input.dispatchEvent(new Event('input', { bubbles: true }));" +
                            "input.dispatchEvent(new Event('change', { bubbles: true }));",
                    driver().findElement(locator), value);
        }
        waitFor().until(ExpectedConditions.attributeToBe(locator, "value", value));
    }

    protected String text(By locator) {
        return waitForVisibility(locator).getText().trim();
    }

    protected boolean waitForText(By locator, String expectedText) {
        return waitFor().until(ExpectedConditions.textToBePresentInElementLocated(locator, expectedText));
    }

    protected void scrollTo(By locator) {
        WebElement element = waitForVisibility(locator);
        ((JavascriptExecutor) driver()).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
    }
}
