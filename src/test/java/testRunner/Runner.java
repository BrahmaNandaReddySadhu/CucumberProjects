package testRunner;



import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import utils.ExtentReportManager;

@CucumberOptions(
        features = "classpath:features",
        glue = {"classpath:stepDef","hooks"},
        tags = "@Button_001",
        plugin = {"pretty",
                "html:target/cucumber-html-report/report.html",
                "json:target/cucumber-json-report/report.json",
                "junit:target/cucumber-junit-report/report.xml"}

)
public class Runner extends AbstractTestNGCucumberTests {

    @AfterClass
    public void tearDown() {
        ExtentReportManager.flushReport(); // MUST be here
    }


}
