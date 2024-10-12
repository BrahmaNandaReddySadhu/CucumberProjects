package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.CommonUtils;

import java.time.Duration;

public class ButtonPages {

    private WebDriver driver;
    CommonUtils commonUtils = new CommonUtils();

    // constructor
    public ButtonPages(WebDriver driver) {
        this.driver = driver;
    }

    private By clickOnMe = By.xpath("//*[text()='Click Me']");
    private By rightClickMe = By.xpath("//*[text()='Right Click Me']");
    private By doubleClickMe = By.xpath("//*[text()='Double Click Me']");
    private By doubleClickText = By.id("doublec");
    private By clickMetext = By.id("welcomeDiv");


    public void clickElementBasedOnText(String text) {
        if (text.equalsIgnoreCase("Click Me")) {
            driver.findElement(clickOnMe).click();
        } else if (text.equalsIgnoreCase("Right Click Me")) {
            driver.findElement(rightClickMe).click();
        } else {
            Actions actions = new Actions(driver);
            actions.moveToElement(driver.findElement(doubleClickMe)).doubleClick().build().perform();
        }
    }

    public String getButtonText(String buttonName) {
        String buttonText=null;
        if(buttonName.equalsIgnoreCase("click me")) {
            commonUtils.waitForElementToBeVisible(driver, clickMetext, 10);
            buttonText=  driver.findElement(clickMetext).getText();
        } else if (buttonName.equalsIgnoreCase("double click me")) {
            commonUtils.waitForElementToBeVisible(driver, doubleClickText, 10);
            buttonText=  driver.findElement(doubleClickText).getText();
        }
        return buttonText;
    }


}
