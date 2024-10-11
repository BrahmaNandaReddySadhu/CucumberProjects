package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotCode {

    public void takeScreenShotCode(WebDriver driver){

        String currentDateTimeFormat=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;

        File source = takesScreenshot.getScreenshotAs(OutputType.FILE);

        File destination = new File("target/screenshots/"+currentDateTimeFormat+".png");

        try {
            FileUtils.copyFile(source,destination);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


}
