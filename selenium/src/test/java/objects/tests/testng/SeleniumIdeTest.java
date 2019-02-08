package objects.tests.testng;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

/**
 * Uses TestNG test framework
 * Test demonstrates simple Selenium IDE exported test. Will fail unless driver path and Timeout is given
 */

public class SeleniumIdeTest {

    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @BeforeClass(alwaysRun = true)
    public void setUp() throws Exception {
        //System.setProperty("webdriver.gecko.driver", Configuration.getConfig().getWebdriver());
        driver = new FirefoxDriver();
        baseUrl = "http://www.bing.com/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void testSeleniumIdeProj() throws Exception {
        driver.get(baseUrl + "/");
        driver.findElement(By.id("sb_form_q")).click();
        driver.findElement(By.id("sb_form_q")).clear();
        driver.findElement(By.id("sb_form_q")).sendKeys("selenium");
        driver.findElement(By.id("sb_form_go")).click();
        driver.findElement(By.cssSelector("strong")).click();
        try {
            //Thread.sleep(1000);
            assertEquals(driver.getTitle(), "Selenium - Web Browser Automation");
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }
}

