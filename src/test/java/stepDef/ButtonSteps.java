package stepDef;

import driverfactory.DriverFactory;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.testng.Assert;
import pages.ButtonPages;
import utils.CommonUtils;

public class ButtonSteps {


    ButtonPages buttonPages = new ButtonPages(DriverFactory.getDriver());
    CommonUtils waitUtils= new CommonUtils();

    @Given("i launch {string} website")
    public void i_launch_website(String url) {
        DriverFactory.getDriver().get(url);
    }

    @When("i click on {string} button")
    public void iClickOnButton(String buttonName) {
        buttonPages.clickElementBasedOnText(buttonName);
    }

    @Then("i should see message as {string} for {string} button")
    public void iShouldSeeMessageAsForButton(String message, String buttonName) {
        Assert.assertEquals(message,buttonPages.getButtonText(buttonName));
    }

    @Then("i should not see message as {string} for {string} button")
    public void iShouldNotSeeMessageAsForButton(String message, String buttonName) {
        System.out.println("text -->"+buttonPages.getButtonText(buttonName));
        Assert.assertNotEquals(message,buttonPages.getButtonText(buttonName));
    }

    @And("i upload the file with the customerName")
    public void iUploadTheFileWithTheCustomerName() {

    }
}
