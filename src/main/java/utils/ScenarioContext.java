package utils;

import com.aventstack.extentreports.ExtentTest;

public class ScenarioContext {
    private static ThreadLocal<ExtentTest> extentTest= new ThreadLocal<>();

    public static void setCurrentTest(ExtentTest test){
        extentTest.set(test);
    }
    public static ExtentTest getCurrentTest(){
        return extentTest.get();
    }

    public static void unload(){
        extentTest.remove();
    }
}
