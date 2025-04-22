package utils;

import com.aventstack.extentreports.ExtentTest;

public class ExtentTestManager {

    private  static ThreadLocal<ExtentTest> extentTestThreadLocal = new ThreadLocal<>();

    public static ExtentTest getTest(){
        return extentTestThreadLocal.get();
    }

    public static void setTest(ExtentTest testName){
        extentTestThreadLocal.set(testName);
    }

    public static void unload(){
        extentTestThreadLocal.remove();
    }
}
