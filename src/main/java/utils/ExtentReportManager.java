package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import driverfactory.DriverFactory;
import io.cucumber.java.Scenario;
import io.cucumber.plugin.event.TestCaseFinished;
import io.cucumber.plugin.event.TestStepFinished;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportManager {

    private static ExtentReports extent;

    public static synchronized ExtentReports getInstance() {
        if (extent == null) {
            createInstance("target/test-out/customExtentReport.html");
        }
        return extent;
    }

    public static ExtentReports createInstance(String fileName) {
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(new File(fileName));
        sparkReporter.config().setReportName("Regression Test Suite");
        sparkReporter.config().setTheme(Theme.STANDARD);
        sparkReporter.config().setDocumentTitle("My Custom Automation Report");
        sparkReporter.config().setEncoding("Utf-8");

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        extent.setSystemInfo("os", System.getProperty("os.name"));
        extent.setSystemInfo("Environonment", "QA");
        extent.setSystemInfo("User", System.getProperty("user.name"));

        return extent;
    }

    public static void takeScreenshot(TestStepFinished event, ExtentTest test) {
        if (DriverFactory.getDriver() == null) {
            test.log(com.aventstack.extentreports.Status.WARNING, "WebDriver is null, cannot capture screenshot");
            return;
        }

        try {
            // Capture screenshot
            File screenshot = ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.FILE);

            // Define screenshot path with timestamp
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String screenshotName = event.getTestStep().getCodeLocation() + "_" + timestamp + ".png";
            String screenshotPath = "target/screenshots/" + screenshotName;

            // Ensure screenshots directory exists
            Files.createDirectories(Paths.get("target/screenshots"));

            // Copy screenshot to target directory
            Files.copy(screenshot.toPath(), new File(screenshotPath).toPath());

            // Attach screenshot to report (relative path for ExtentReports)
            test.addScreenCaptureFromPath("../screenshots/" + screenshotName, "Screenshot on Failure");
        } catch (IOException e) {
            test.log(com.aventstack.extentreports.Status.WARNING, "Failed to capture screenshot: " + e.getMessage());
        }
    }


}
