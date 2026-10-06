package com.example.ui.pages;

import com.example.ui.config.FrameworkConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PlaygroundPage extends BasePage {
    private static final By DYNAMIC_ID_BUTTON = By.xpath("//button[normalize-space()='Button with Dynamic ID']");
    private static final By AJAX_BUTTON = By.id("ajaxButton");
    private static final By SUCCESS_MESSAGE = By.cssSelector("#content p.bg-success");
    private static final By BLUE_BUTTON = By.id("blueButton");
    private static final By GREEN_BUTTON = By.id("greenButton");
    private static final By OBSTRUCTED_BUTTON = By.id("badButton");
    private static final By HIDING_BUTTON = By.id("hidingButton");

    public void openPage(String page) {
        String path = switch (page) {
            case "Dynamic ID" -> "/dynamicid";
            case "AJAX" -> "/ajax";
            case "Hidden Layers" -> "/hiddenlayers";
            case "Client Side Delay" -> "/clientdelay";
            case "Click" -> "/click";
            case "Scrollbars" -> "/scrollbars";
            default -> throw new IllegalArgumentException("Unknown playground page: " + page);
        };
        String baseUrl = FrameworkConfig.get("playground.url");
        open(baseUrl.replaceAll("/+$", "") + path);
    }

    public void clickDynamicIdButton() {
        click(DYNAMIC_ID_BUTTON);
    }

    public boolean dynamicIdButtonAvailable() {
        return waitForVisibility(DYNAMIC_ID_BUTTON).isDisplayed();
    }

    public void triggerAjaxRequest() {
        click(AJAX_BUTTON);
    }

    public String waitForAjaxResponse() {
        waitFor().until(ExpectedConditions.visibilityOfElementLocated(SUCCESS_MESSAGE));
        return text(SUCCESS_MESSAGE);
    }

    public void clickGreenButton() {
        click(GREEN_BUTTON);
    }

    public boolean blueButtonVisible() {
        return waitFor().until(ExpectedConditions.visibilityOfElementLocated(BLUE_BUTTON)).isDisplayed();
    }

    public void triggerClientSideCalculation() {
        click(AJAX_BUTTON);
    }

    public String waitForClientSideResult() {
        waitFor().until(ExpectedConditions.visibilityOfElementLocated(SUCCESS_MESSAGE));
        return text(SUCCESS_MESSAGE);
    }

    public void clickButtonIgnoringDomClick() {
        click(OBSTRUCTED_BUTTON);
    }

    public boolean buttonIgnoringDomClickSucceeded() {
        return waitFor().until(ExpectedConditions.attributeContains(OBSTRUCTED_BUTTON, "class", "btn-success"));
    }

    public void scrollToAndClickHidingButton() {
        scrollTo(HIDING_BUTTON);
        click(HIDING_BUTTON);
    }

    public boolean hidingButtonAvailable() {
        WebElement button = waitForVisibility(HIDING_BUTTON);
        return button.isDisplayed();
    }
}
