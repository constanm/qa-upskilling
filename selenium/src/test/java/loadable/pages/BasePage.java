package loadable.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
    private static WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    static boolean myElementIsClickable(By by) {
        try {
            new WebDriverWait(driver, 15).until(ExpectedConditions.elementToBeClickable(by));
        } catch (WebDriverException ex) {
            return false;
        }
        return true;
    }

    //Click Method
    public void click(By elementLocation) {
        driver.findElement(elementLocation).click();
    }

    //Write Text
    public void writeText(By elementLocation, String text) {
        driver.findElement(elementLocation).sendKeys(text);
    }

    //Read Text
    public String readText(By elementLocation) {
        return driver.findElement(elementLocation).getText();
    }
}
