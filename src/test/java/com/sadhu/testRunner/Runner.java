package com.sadhu.testRunner;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.*;

@CucumberOptions(
        features = "classpath:features",
        glue = {"com.sadhu.stepDef", "com.sadhu.hooks", "com.sadhu.listeners"},
        plugin = {"pretty",
                "html:target/cucumber-reports/cucumber-html-report.html",
                "json:target/cucumber-reports/cucumber.json",
                "junit:target/cucumber-reports/cucumber.xml",
                "timeline:target/cucumber-reports/timeline",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
                "com.sadhu.listeners.CucumberExtentListener"
        },
        monochrome = true,
        publish = true

)
public class Runner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }


}

