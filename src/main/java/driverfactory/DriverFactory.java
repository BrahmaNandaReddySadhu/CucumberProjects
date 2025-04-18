package driverfactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class DriverFactory {

    // thread local for thread safe webdriver instance
    public static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    // private constructor for prevention of instantiation
    private DriverFactory() {

    }

    /*
       initialize webdriver based on browserName

     */
    public static WebDriver initialize_driver(String browerName) {
        // remove direct return of the existing driver to allow reinitilization if needed
        if (driverThreadLocal.get() != null) {
            return driverThreadLocal.get();
        }

        WebDriver driver;

        switch (browerName.toLowerCase()) {
            case "chrome":
                driver = ChromeDriverManager.createDriver(false);
                break;
            case "firefox":
                driver = FirefoxDriverManager.createDriver(false);
                break;
            case "edge":
                driver = EdgeDriverManager.createDriver(false);
                break;
            default:
                throw new IllegalArgumentException("unsupported browser:"+ browerName);
        }

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().deleteAllCookies();

        driverThreadLocal.set(driver);
        return  driver;

    }


    public static synchronized WebDriver getDriver() {
        WebDriver driver = driverThreadLocal.get();
        if(driver==null){
            throw  new IllegalStateException("Driver no initialized , call initialize first");
        }
        return driver;
    }

    public static  synchronized  void quitDriver(){
        WebDriver driver = driverThreadLocal.get();
        if(driver!=null){
            try {
                driver.quit();
            }finally {
                driverThreadLocal.remove();
            }

        }
    }

    private static class ChromeDriverManager {
        static WebDriver createDriver(boolean headless) {
            ChromeOptions options = new ChromeOptions();

            // Common options for both headless and headed modes
            options.addArguments("--disable-infobars"); // Disabling infobars
            options.addArguments("--disable-extensions"); // Disabling extensions
            options.addArguments("--no-sandbox"); // Bypass OS security model
            options.addArguments("--disable-dev-shm-usage"); // Overcome limited resource problems
            options.addArguments("--window-size=1920,1080");// Set window size
            options.addArguments("--start-maximized");

            // Headless mode configuration
            if (headless) {
                options.addArguments("--headless=new"); // New headless mode in Chrome 109+
            }

            // Mobile emulation example (optional)
            // Map<String, String> mobileEmulation = new HashMap<>();
            // mobileEmulation.put("deviceName", "iPhone X");
            // options.setExperimentalOption("mobileEmulation", mobileEmulation);

            // Set download directory (optional)
            // Map<String, Object> prefs = new HashMap<>();
            // prefs.put("download.default_directory", "/path/to/download");
            // options.setExperimentalOption("prefs", prefs);

            return new ChromeDriver(options);

        }
    }

    private static class FirefoxDriverManager {
        static WebDriver createDriver(boolean headless) {
            FirefoxOptions options = new FirefoxOptions();

            // Common options for both headless and headed modes
            options.addArguments("--disable-infobars"); // Disabling infobars
            options.addArguments("--disable-extensions"); // Disabling extensions
            options.addArguments("--no-sandbox"); // Bypass OS security model
            options.addArguments("--disable-dev-shm-usage"); // Overcome limited resource problems
            options.addArguments("--window-size=1920,1080"); // Set window size

            // Headless mode configuration
            if (headless) {
                options.addArguments("--headless=new"); // New headless mode in Chrome 109+
            }

            // Mobile emulation example (optional)
            // Map<String, String> mobileEmulation = new HashMap<>();
            // mobileEmulation.put("deviceName", "iPhone X");
            // options.setExperimentalOption("mobileEmulation", mobileEmulation);

            // Set download directory (optional)
            // Map<String, Object> prefs = new HashMap<>();
            // prefs.put("download.default_directory", "/path/to/download");
            // options.setExperimentalOption("prefs", prefs);

            return new FirefoxDriver(options);

        }
    }

    private static class EdgeDriverManager {
        static WebDriver createDriver(boolean headless) {
            EdgeOptions options = new EdgeOptions();

            // Common options for both headless and headed modes
            options.addArguments("--disable-infobars"); // Disabling infobars
            options.addArguments("--disable-extensions"); // Disabling extensions
            options.addArguments("--no-sandbox"); // Bypass OS security model
            options.addArguments("--disable-dev-shm-usage"); // Overcome limited resource problems
            options.addArguments("--window-size=1920,1080"); // Set window size

            // Headless mode configuration
            if (headless) {
                options.addArguments("--headless=new"); // New headless mode in Chrome 109+
            }

            // Mobile emulation example (optional)
            // Map<String, String> mobileEmulation = new HashMap<>();
            // mobileEmulation.put("deviceName", "iPhone X");
            // options.setExperimentalOption("mobileEmulation", mobileEmulation);

            // Set download directory (optional)
            // Map<String, Object> prefs = new HashMap<>();
            // prefs.put("download.default_directory", "/path/to/download");
            // options.setExperimentalOption("prefs", prefs);

            return new EdgeDriver(options);

        }
    }

}
