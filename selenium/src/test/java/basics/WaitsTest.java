package basics;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.DriverBase;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WaitsTest extends DriverBase {
    private static WebDriver driver;

    @BeforeAll
    public static void beforeAll() {
        driver = DriverBase.getDriver();
    }

    @BeforeEach
    public void beforeEach() {
        driver.navigate().to("http://automationpractice.com");
    }

    @Test
    public void testExistenceOfElementUsingImplicitWaits() {
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        boolean exists = driver.findElements(By.id("search_query_top")).size() != 0;
        assertTrue(exists);
    }

    @Test
    public void testDelayedExecutionViaJavascript() throws InterruptedException {
        // Be generous with the implicit timeout
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

        // Set a 2 seconds timeout on script execution to see this fail
//        driver.manage().timeouts().setScriptTimeout(2, TimeUnit.SECONDS);

        // calculate start millis
        long start = System.currentTimeMillis();

        System.out.println("Start: " + start);
        ((JavascriptExecutor) driver).executeAsyncScript(
                // javascript code to introduce a 5 seconds delay
                "window.onload = setTimeout(function(){\n" +
                        "    alert('Hello bing');\n" +
                        "    window.location = 'http://www.bing.com';\n" +
                        "}, 5000);");

        System.out.println("Stop: " + System.currentTimeMillis());
        Thread.sleep(1000);
        driver.switchTo().alert().accept();
        System.out.println("Elapsed seconds: " + (System.currentTimeMillis() - start) / 1000);
    }

    @Test
    public void testExplicitWaitsOnAjaxCall() {
        driver.findElement(By.name("search_query")).sendKeys("dress");
        By locatorOfFirstResultOfAjaxQuery = By.cssSelector("li.ac_even:nth-child(1)");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(locatorOfFirstResultOfAjaxQuery));
        assertEquals("Summer Dresses > Printed Summer Dress", driver.findElement(locatorOfFirstResultOfAjaxQuery).getText());
    }

    @Test
    public void testWaitUsingJQueryActiveFlag() throws InterruptedException {
        driver.get("http://fiddle.jshell.net/GRMule/WQXXT/show/");

        WebDriverWait wait = new WebDriverWait(driver, 10);

        // Wait for DOM load
        wait.until(driver1 -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));

        driver.switchTo().frame(0);

        wait.until(d -> d.findElement(By.partialLinkText("success"))).click();

        //Wait for ajax complete
        while (true) {
            boolean ajaxIsComplete = (boolean) ((JavascriptExecutor) driver).executeScript("return jQuery.active === 0");
            if (ajaxIsComplete)
                break;
            Thread.sleep(100);
        }

        By locator = By.id("output");
        assertEquals("hello world!", driver.findElement(locator).getText());
    }

    @AfterEach
    public void AfterEach() {
        // Reset back to 10s timeout
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
}
