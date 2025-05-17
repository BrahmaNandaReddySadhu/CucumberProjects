package com.sadhu.testRunner;

import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.*;

@CucumberOptions(
        features = "target/failedrerun.txt",
        glue = {"com.sadhu.stepDef", "com.sadhu.hooks", "com.sadhu.listeners"},
        plugin = {"pretty",
                "html:target/cucumber-html-report.html",
                "json:target/cucumber.json",
                "junit:target/cucumber.xml",
                "timeline:target/timeline",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
                "com.sadhu.listeners.CucumberExtentListener",
                "rerun:target/failedrerun.txt"
        },
        monochrome = true
)

public class RerunTestRunner extends AbstractTestNGCucumberTests{

}
