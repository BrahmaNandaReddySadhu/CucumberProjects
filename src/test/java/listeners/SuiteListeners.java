package listeners;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestListener;
import utils.ConfigReader;
import utils.ExtentReportManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class SuiteListeners implements ISuiteListener {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");

    @Override
    public void onStart(ISuite iSuite){
        System.out.println("=== TEST SUITE STARTING ===");
        System.out.println("Execution timestamp: "+ LocalDateTime.now().format(DATE_FORMATTER));

        // 1. initialize the configuration
        ConfigReader configReader = new ConfigReader();
        if(!configReader.getProperties().containsKey("browser")){
            throw  new RuntimeException("Browser Configuration missing");
        }
        // initilaize the externt reports
        ExtentReportManager.getExtentReports();

    }

    @Override
    public void onFinish(ISuite suite){
        ExtentReportManager.flushReport();
        System.out.println("=== TEST SUITE COMPLETED ===");
        System.out.println("Reports available at: "+System.getProperty("user.dir")+"/target/cucumber-json-report");
    }
}
