package com.sadhu.hooks;

import com.aventstack.extentreports.MediaEntityBuilder;
import driverfactory.DriverFactory;
import io.cucumber.java.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import utils.ConfigReader;
import utils.ExtentTestManager;

import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class ApplicationHooks {

    private WebDriver driver;

    @AfterStep
    public void afterStep(Scenario scenario) {
        if (scenario.isFailed()) {
            byte[] sceenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

            ExtentTestManager.getTest().fail("Step failed", MediaEntityBuilder.createScreenCaptureFromBase64String(java.util.Base64.getEncoder().encodeToString(sceenshot)).build());
        }
    }

    private ConfigReader configReader;
    private static final ThreadLocal<Properties> properties = new ThreadLocal<>();

    private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-DD HH:mm:ss");

    @Before(order = 1)
    public void loadProperties() {
        try {
            System.out.println("loading the properties ..... from load Properties on Hooks");
            configReader = new ConfigReader();
            Properties props = configReader.getProperties();

            if (!props.containsKey("browser")) {
                throw new IllegalStateException("browser property is missing in configuration");
            }

            properties.set(props);

            System.out.println("browserName-->: " + properties.get().getProperty("browser"));
        } catch (Exception e) {
            System.err.println("Failed to load the properties: " + e.getMessage());
            throw e;
        }


    }

    @Before(order = 2)
    public void initializeBrowser() {
        try {
            System.out.println("Initializing the browser driver....from the hooks");
            String browserName = properties.get().getProperty("browser");
            DriverFactory.initialize_driver(browserName);
        } catch (Exception e) {
            System.err.println("Browser Initialization failed: " + e.getMessage());
            throw e;
        }

    }

    @After(order = 0)
    public void quitDriver() {
        try {
            System.out.println("closing the driver");
            DriverFactory.quitDriver();
        } catch (Exception e) {
            System.err.println("Failed to quit the driver: " + e.getMessage());
        }
    }


}
