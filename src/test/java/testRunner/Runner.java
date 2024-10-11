package testRunner;



import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "classpath:features",
        glue = {"classpath:stepDef","Hooks"},
        plugin = {"pretty",
                "html:target/cucumber-html-report/report.html",
                "json:target/cucumber-json-report/report.json",
                "junit:target/cucumber-junit-report/report.xml"}

)
public class Runner extends AbstractTestNGCucumberTests {

}
