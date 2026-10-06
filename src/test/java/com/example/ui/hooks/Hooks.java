package com.example.ui.hooks;

import com.example.ui.config.FrameworkConfig;
import com.example.ui.driver.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Hooks {
    @Before
    public void setUp() {
        DriverManager.initDriver(FrameworkConfig.get("browser"));
    }

    @After
    public void tearDown(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                attachFailureScreenshot(scenario);
            }
        } finally {
            DriverManager.quitDriver();
        }
    }

    private void attachFailureScreenshot(Scenario scenario) {
        WebDriver driver = DriverManager.getDriver();
        if (!(driver instanceof TakesScreenshot screenshotDriver)) {
            throw new IllegalStateException("The active WebDriver does not support screenshots");
        }
        byte[] screenshot = screenshotDriver.getScreenshotAs(OutputType.BYTES);
        scenario.attach(screenshot, "image/png", "Failure screenshot");
        try {
            Path screenshotDirectory = Path.of("target", "screenshots");
            Files.createDirectories(screenshotDirectory);
            Path screenshotFile = screenshotDirectory.resolve(
                    scenario.getName().replaceAll("[^a-zA-Z0-9._-]", "_") + "-" +
                            System.currentTimeMillis() + ".png");
            Files.write(screenshotFile, screenshot);
        } catch (IOException exception) {
            throw new IllegalStateException("Could not save failure screenshot", exception);
        }
    }
}
