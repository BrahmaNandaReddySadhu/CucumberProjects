package hooks;

import com.aventstack.extentreports.MediaEntityBuilder;
import driverfactory.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import utils.ConfigReader;
import utils.ExtentReportManager;
import utils.ScenarioContext;

import java.sql.Driver;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Properties;

public class ApplicationHooks {

    private ConfigReader configReader;
    private static final ThreadLocal<Properties> properties = new ThreadLocal<>();

    private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-DD HH:mm:ss");


//    @BeforeSuite
//    public void globalSetUp(){
//        System.out.println("=== TEST SUITE STARTING ===");
//        System.out.println("Execution timestamp: "+ LocalDateTime.now().format(TIMESTAMP_FORMAT));
//
//        // 1. initialize the configuration
//        configReader = new ConfigReader();
//        if(!configReader.getProperties().contains("browser")){
//            throw  new RuntimeException("Browser Configuration missing");
//        }
//
//        // initilaize the externt reports
//        ExtentReportManager.getExtentReports();
//    }

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

        //properties = configReader.getProperties();
        //System.out.println("Browser name " + properties.getProperty("browser"));


    }

    @BeforeStep
    public void beforeStep(Scenario scenario){
        ScenarioContext.getCurrentTest().info("Steps: "+scenario.getName());
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

    @Before(order = 3)
    public void beforeScenario(Scenario scenario) {
        try {
            System.out.println("setting up test scenario -->:" + scenario.getName());
            ExtentReportManager.createTest(scenario.getName());
            // Adding tags to reports if needed
            scenario.getSourceTagNames().forEach(tag -> ExtentReportManager.getTest().assignCategory(tag));

        } catch (Exception e) {
            System.err.println("Failed to setup Scenarioe:" + e.getMessage());
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

    @After(order = 1)
    public void takeScreenshotOnFailedCases(Scenario scenario) {

        if (scenario.isFailed()) {

            try {
                String screenShotName = scenario.getName().replace(" ", "-") + "_" + System.currentTimeMillis();
                System.out.println("Taking screenshot for failed Scenario Name: " + screenShotName);
                TakesScreenshot takesScreenshot = (TakesScreenshot) DriverFactory.getDriver();
                WebDriver driver = DriverFactory.getDriver();

                if (driver != null) {
                    byte[] screenShouSourceByteArray = takesScreenshot.getScreenshotAs(OutputType.BYTES);
                    if (screenShouSourceByteArray.length > 0) {
                        scenario.attach(screenShouSourceByteArray, "image/png", screenShotName);
                        String base64Screenshot=Base64.getEncoder().encodeToString(screenShouSourceByteArray);
                        ExtentReportManager.getTest().fail("Test failed", MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
                    } else {
                        System.out.println("Screenshot byte array is empty");
                    }
                }
            } catch (Exception e) {
                System.err.println("Failed to take screenshot:" + e.getMessage());
            }
        }

    }
//
//    @After(order = 2)
//    public void flushExtentReports() {
//        try {
//            ExtentReportManager.flushReport();
//            System.out.println("Extent report flushed");
//        } catch (Exception e) {
//            System.err.println("Failed to flush extent report: " + e.getMessage());
//        }
//    }


    @After(order = 2)
    public void afterScenario(Scenario scenario) {
        if(scenario.isFailed()){
            ExtentReportManager.getTest().fail("Scenario failed");
        }else {
            ExtentReportManager.getTest().pass("Scenario passed");
        }
    }


//
//    @AfterSuite
//    public void globalCleanUp(){
//        System.out.println("=== TEST SUITE COMPLETED ===");
//        System.out.println("Reports available at: "+System.getProperty("user.dir")+"/target/cucumber-json-report");
//    }


}
