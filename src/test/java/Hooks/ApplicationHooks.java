package Hooks;

import com.google.common.net.MediaType;
import driverfactory.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import utils.ConfigReader;

import java.awt.*;
import java.io.File;
import java.util.Properties;

public class ApplicationHooks {

    private DriverFactory driverFactory;

    private WebDriver driver;
    private Properties properties;
    private ConfigReader configReader;

    @Before(order = 1)
    public void loadProperies() {
        configReader = new ConfigReader();
        properties = configReader.getProperties();
        System.out.println("browesrrname "+ properties.getProperty("browser"));
    }

    @Before(order = 3)
    public void initializeBrowser() {
        driverFactory = new DriverFactory();
        driverFactory.initialize_driver(properties.getProperty("browser"));
    }

    @After(order = 0)
    public void quitDriver() {

        DriverFactory.getDriver().quit();
    }

    @After(order = 1)
    public void takeSceenShotOfFailedScenarioes(Scenario scenario) {

        if (scenario.isFailed()) {
            String screnShotName = scenario.getName().replace(" ", "-");
            System.out.println("scenario name" + screnShotName);
            TakesScreenshot screenshot = (TakesScreenshot) DriverFactory.getDriver();
            byte[] byteArray = screenshot.getScreenshotAs(OutputType.BYTES);
           // scenario.attach(byteArray, "image/png", "Scenario On failure");




            System.out.println("Screenshot byte array size: " + byteArray.length); // Debugging line

            if (byteArray.length > 0) {
                scenario.attach(byteArray, "image/png", "Scenario On Failure");
            } else {
                System.out.println("Failed to capture screenshot, byte array is empty.");
            }

        }


    }

}
