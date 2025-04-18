package hooks;

import driverfactory.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import utils.ConfigReader;
import utils.ExtentReportManager;

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
        System.out.println("Browser name " + properties.getProperty("browser"));
        System.out.println("1----->");
    }

    @Before(order = 2)
    public void initializeBrowser() {
        driverFactory = new DriverFactory();
        driverFactory.initialize_driver(properties.getProperty("browser"));
        System.out.println("2nd ------>");
    }

    @Before(order = 3)
    public void beforeScenario(Scenario scenario) {
        ExtentReportManager.createTest(scenario.getName());
        System.out.println("3 ------->");
        System.out.println("creating the test cases as"+ scenario.getName());
    }

    @After(order = 0)
    public void quitDriver() {
        DriverFactory.getDriver().quit();
        System.out.println("clsing driver ---->");
    }

    @After(order = 1)
    public void takeScreenshotOnFailedCases(Scenario scenario) {

        if (scenario.isFailed()) {
            String screenShotName = scenario.getName().replace(" ", "-") + "_" + System.currentTimeMillis();
            System.out.println("Scenario Name: " + screenShotName);
            TakesScreenshot takesScreenshot = (TakesScreenshot) DriverFactory.getDriver();

            byte[] screenShouSourceByteArray = takesScreenshot.getScreenshotAs(OutputType.BYTES);

            if (screenShouSourceByteArray.length > 0) {
                scenario.attach(screenShouSourceByteArray, "image/png", screenShotName);
            } else {
                System.out.println("Failed to take the screenshot, byte array is empty");
            }

            ExtentReportManager.getTest().fail("Scenario Failed").addScreenCaptureFromBase64String(new String(screenShouSourceByteArray));
        }
        System.out.println(" failed taling screenshoy--->");

    }

    @After(order = 2)
    public void unloadExtent() {
        ExtentReportManager.flushReport();
    }

}
