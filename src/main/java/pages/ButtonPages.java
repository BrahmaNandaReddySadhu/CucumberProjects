package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ButtonPages {

    private WebDriver driver;

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
            driver.findElement(doubleClickMe).click();
        }
    }

    public String getClickMeText() {
        return driver.findElement(clickMetext).getText();
    }

    public String getDoubleClickMeText() {
        return driver.findElement(doubleClickText).getText();
    }


}
