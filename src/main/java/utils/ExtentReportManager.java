package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportManager {

    private static ExtentReports extentReports;
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    public static ExtentReports getExtentReports() {
        if (extentReports == null) {
            try {
                //1. creating file directory if doed not exists

                File reportDirectory = new File(System.getProperty("user.dir") + "/target/test-output");
                if (!reportDirectory.exists()) {
                    reportDirectory.mkdir();
                }

                //2 . generating the time stamp

                String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());

                String reportPath = reportDirectory.getAbsolutePath() + "/ExtentReport_" + timeStamp + ".html";
                System.out.println("Extent Report Path will be saved at this location:" + reportPath);


                //3. Configure the Spark Report

                ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
                sparkReporter.config().setDocumentTitle("Automation Test Report");
                sparkReporter.config().setReportName("Cucumber Test Execution Report");
                sparkReporter.config().setTheme(Theme.DARK);
                sparkReporter.config().setEncoding("utf-8");

                //4 Initialize the Extent Reports

                extentReports = new ExtentReports();
                extentReports = new ExtentReports();
                extentReports.attachReporter(sparkReporter);
                extentReports.setSystemInfo("OS", System.getProperty("os.name"));
                extentReports.setSystemInfo("Java Version", System.getProperty("java.version"));
                extentReports.setSystemInfo("Tester", "Brahmananda Reddy");
                extentReports.setSystemInfo("Environment", "QA");

            } catch (Exception exception) {
                System.err.println("Error while initializing Extent Report: " + exception.getMessage());
            }
        }
        return extentReports;
    }

    public static synchronized void createTest(String testName) {
        try {
            System.out.println("creating the test by passing the scenario name");
            ExtentTest test = getExtentReports().createTest(testName);
            extentTest.set(test);
            ScenarioContext.setCurrentTest(test);
        } catch (Exception e) {
            System.err.println("Error while creating test" + e.getMessage());
        }
    }

    public static ExtentTest getTest() {
        return extentTest.get();
    }

    public static synchronized void flushReport() {
        if (extentReports != null) {
            try {
                System.out.println("Flushing ExtentReports....");
                extentReports.flush();
            } catch (Exception e) {
                System.err.println("Error while flushing Extent Report:" + e.getMessage());
            }
        } else {
            System.out.println("Extent Report is null at the flush...");
        }
    }

    public static void unload() {
        extentTest.remove();
    }

}
