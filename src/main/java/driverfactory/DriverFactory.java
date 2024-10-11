package driverfactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {

    private WebDriver driver;

    public static ThreadLocal<WebDriver> tl = new ThreadLocal<>();

    public WebDriver initialize_driver(String browerName){
        if(browerName.equalsIgnoreCase("chrome")){
            tl.set(new ChromeDriver());
        }else if (browerName.equalsIgnoreCase("firefox")){
            tl.set(new FirefoxDriver());
        } else if (browerName.equalsIgnoreCase("edge")) {
            tl.set(new EdgeDriver());
        }

        getDriver().manage().deleteAllCookies();
        getDriver().manage().window().maximize();

        return getDriver();

    }


    public static synchronized WebDriver getDriver(){
        return tl.get();
    }


}
