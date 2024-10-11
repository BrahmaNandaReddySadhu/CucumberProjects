package stepDef;

import driverfactory.DriverFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.testng.Assert;
import pages.ButtonPages;

import java.time.Duration;

public class ButtonSteps {


    ButtonPages buttonPages = new ButtonPages(DriverFactory.getDriver());

    @Given("i launch {string} website")
    public void i_launch_website(String url) {
        DriverFactory.getDriver().get(url);
    }

    @Then("i should see message as {string}")
    public void i_should_see_message_as(String string) throws InterruptedException {
        Thread.sleep(5000);

        Assert.assertEquals(string,buttonPages.getClickMeText());

    }

    @When("i click on {string} button")
    public void iClickOnButton(String buttonName) {
        buttonPages.clickElementBasedOnText(buttonName);
    }
}
