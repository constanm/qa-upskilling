package basics;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.DriverBase;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AdvancedJavascriptInteractionsTest extends DriverBase {
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
    public void testClickButtonViaJavaScript() throws InterruptedException {
        WebElement theButton = driver.findElement(By.name("submit_search"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", theButton);

        Thread.sleep(5000);
        WebElement centerElement = driver.findElement(By.cssSelector("div[id='center_column']"));
        WebElement centerElementParagraph = centerElement.findElement(By.tagName("p"));

        assertTrue(centerElementParagraph.getText().equals("Please enter a search keyword"));
    }

    @Test
    public void testEnabledDisabledButtonViaJavaScript() {
        WebElement button = driver.findElement(By.name("submit_search"));
        assertTrue(button.isEnabled());
        ((JavascriptExecutor) driver).executeScript("arguments[0].disabled = true;", button);
        assertFalse(driver.findElement(By.name("submit_search")).isEnabled());
        ((JavascriptExecutor) driver).executeScript("arguments[0].disabled = false;", button);
        assertTrue(driver.findElement(By.name("submit_search")).isEnabled());
    }

    @Test
    public void testScrollToElement() throws InterruptedException {
        WebElement elem = driver.findElement(By.linkText("Sitemap"));
        int elemPos = elem.getLocation().getY();
        ((JavascriptExecutor) driver).executeScript("window.scroll(0, " + elemPos + ");");
        Thread.sleep(2000);
    }

    @Test
    public void testSetAndAssertHiddenField() {
        WebElement hiddenElement = driver.findElement(By.name("orderby"));
        assertEquals("position", hiddenElement.getAttribute("value"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = 'price';", hiddenElement);
        assertEquals("price", hiddenElement.getAttribute("value"));
    }

    @Test
    public void testAssertTextInElement() {
        WebElement element = driver.findElement(By.className("shop-phone"));
        Object html = ((JavascriptExecutor) driver).executeScript("return arguments[0].innerHTML;", element);
        String htmlStr = (String) html;
        assertTrue(htmlStr.contains("0123-456-789"));
    }

    @Test
    public void testPasteLargeTextInTextArea() {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.linkText("Contact us"))).click();

        long millis = System.currentTimeMillis();
        String longText = String.join("", Collections.nCopies(500, "*"));
        WebElement textArea = driver.findElement(By.id("message"));
        textArea.sendKeys(longText);
        long elapsedSec = System.currentTimeMillis() - millis;
        System.out.println("Time cost by sendKeys: " + elapsedSec + " seconds");

        textArea.clear();
        millis = System.currentTimeMillis();
        ((JavascriptExecutor) driver).executeScript("document.getElementById('message').value = arguments[0];", longText);
        elapsedSec = System.currentTimeMillis() - millis;
        System.out.println("Time cost by JS set: " + elapsedSec + " seconds");
    }

    @Test
    public void testDoubleClick() {
        driver.findElement(By.linkText("Contact us")).click();
        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        WebElement elem = webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("message")));

        Actions builder = new Actions(driver);
        builder.doubleClick(elem).perform();
    }

    @Test
    public void testMouseOver() {
        driver.findElement(By.linkText("Contact us")).click();
        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        WebElement elem = webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("message")));

        Actions builder = new Actions(driver);
        builder.moveToElement(elem).perform();
    }

    @Test
    public void testRightClick() {
        driver.findElement(By.linkText("Contact us")).click();

        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        WebElement elem = webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("message")));

        Capabilities caps = ((RemoteWebDriver) driver).getCapabilities();
        if ("firefox".equals(caps.getBrowserName())) {
            Actions builder = new Actions(driver);
            builder.contextClick(elem)
                    .sendKeys(Keys.DOWN)
                    .sendKeys(Keys.DOWN)
                    .sendKeys(Keys.DOWN)
                    .sendKeys(Keys.DOWN)
                    .sendKeys(Keys.RETURN)
                    .perform();
        } else {
            // ...
        }
    }
}
