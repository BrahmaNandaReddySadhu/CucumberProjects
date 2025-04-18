package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportManager {

    private static ExtentReports extentReports;
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    public static ExtentReports getExtentReports(){
        if(extentReports == null){
            String timeStamp= new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String reportPath = System.getProperty("user.dir")+"test-output/ExtentReport_"+timeStamp+".html";
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);

            extentReports = new ExtentReports();
            extentReports.attachReporter(sparkReporter);
            extentReports.setSystemInfo("OS",System.getProperty("os.name"));
            extentReports.setSystemInfo("Tester","Brahmananda Reddy");
        }
        return extentReports;


    }

    public static void createTest(String testName){
        ExtentTest test= getExtentReports().createTest(testName);
        extentTest.set(test);
    }

    public static ExtentTest getTest(){
        return extentTest.get();
    }

    public static void flushReport(){
        if(extentReports!=null){
            extentReports.flush();
        }
    }

}
