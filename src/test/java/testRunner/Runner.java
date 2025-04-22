package testRunner;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import listeners.CucumberExtentListener;
import org.testng.annotations.*;

@CucumberOptions(
        features = "classpath:features",
        glue = {"classpath:stepDef", "hooks","listeners"},
        plugin = {"pretty",
                "html:target/cucumber-html-report/report.html",
                "json:target/cucumber-json-report/report.json",
                "junit:target/cucumber-junit-report/report.xml",
               // "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
                "listeners.CucumberExtentListener"
        },
        monochrome = true

)
public class Runner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }


}

