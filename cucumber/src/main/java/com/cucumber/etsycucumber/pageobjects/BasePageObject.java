package com.cucumber.etsycucumber.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BasePageObject {

    protected WebDriver webDriver;

    protected final int MAX_WAIT_TIME = 60;

    public BasePageObject(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    protected WebElement findElementWithWait(By locator) {
        return new WebDriverWait(webDriver, MAX_WAIT_TIME)
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected List<WebElement> findElementsWithWait(By locator) {
        return new WebDriverWait(webDriver, MAX_WAIT_TIME).
                until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    public void captureScreenShot() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
        String time = LocalDateTime.now().format(dtf);
        File screenshot1 = new File("target/screenshot"+time+".png");
        File outFile = ((TakesScreenshot)webDriver).getScreenshotAs(OutputType.FILE);
        try {
            Files.copy(outFile.toPath(), screenshot1.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
