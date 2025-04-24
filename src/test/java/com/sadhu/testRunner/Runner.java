package com.sadhu.testRunner;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.*;

@CucumberOptions(
        features = "classpath:features",
        glue = {"com.sadhu.stepDef", "com.sadhu.hooks", "com.sadhu.listeners"},
        plugin = {"pretty",
                "html:target/cucumber-html-report.html",
                "json:target/cucumber.json",
                "junit:target/cucumber.xml",
                "timeline:target/timeline",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
                "com.sadhu.listeners.CucumberExtentListener"
        },
        monochrome = true

)
public class Runner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }


}