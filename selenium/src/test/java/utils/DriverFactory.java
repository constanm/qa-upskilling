package utils;

import objects.configuration.Configuration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverFactory {
    private WebDriver webDriver;

    WebDriver getDriver() {
        if (null == webDriver) {
            System.setProperty("webdriver.chrome.driver", Configuration.getConfig().getChromedriver());
            webDriver = new ChromeDriver();
        }
        return webDriver;
    }

    void quitDriver() {
        if (null != webDriver) {
            webDriver.quit();
            webDriver = null;
        }
    }
}
