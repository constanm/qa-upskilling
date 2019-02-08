package basics;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.DriverBase;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WebDriverTests extends DriverBase {
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
    public void testProperties() {
        assertEquals("My Store", driver.getTitle());
        assertTrue(driver.getPageSource().contains("Ecommerce software"));
    }

    @Test
    public void testNavigation() {
        driver.navigate().to("http://google.com");
        driver.navigate().back();
        driver.navigate().refresh();
        driver.navigate().forward();
    }

    @Test
    public void testSwitchingMethods() throws InterruptedException {
        String currentWindow = driver.getWindowHandle();
        System.out.println("Current window() handle " + currentWindow);

        // Open a new tab
        driver.findElement(By.partialLinkText("Ecommerce software")).click();
        Thread.sleep(1000);

        // Get initial and last tab
        Set<String> windowHandles = driver.getWindowHandles();
        String firstTab = windowHandles.iterator().next();
        String lastTab = windowHandles.stream().skip(windowHandles.size()-1).findFirst().get();

        // Make sure first tab is our current tab
        assertEquals(currentWindow, firstTab);

        // Switch to last tab
        driver.switchTo().window(lastTab);
        System.out.println("New window() handle " + lastTab);
        Thread.sleep(1000);

        // Close last tab
        driver.close();
        Thread.sleep(1000);

        // Switch to first tab
        driver.switchTo().window(firstTab);
        Thread.sleep(1000);
    }

    @Test
    public void testSizing() {
        // Get initial window() size
        System.out.println(driver.manage().window().getSize());

        // Set window() size
        driver.manage().window().setSize(new Dimension(480, 320));
        System.out.println(driver.manage().window().getSize());

        // Maximize window()
        driver.manage().window().maximize();
        System.out.println(driver.manage().window().getSize());

        // Reposition window()
        driver.manage().window().setPosition(new Point(0, 0));
    }

    @Test
    public void testMinimizeBrowser() throws InterruptedException {
        // Hack to minimize browser
        driver.manage().window().setPosition(new Point(-2000, 0));
        Thread.sleep(2000);

        // Restore position
        driver.manage().window().setPosition(new Point(0, 0));
        Thread.sleep(2000);
    }

    @Test
    public void testMoveBrowser() throws InterruptedException {
        driver.manage().window().setPosition(new Point(100, 100));
        Thread.sleep(1000);
        driver.manage().window().setPosition(new Point(0, 0));
    }

    @Test
    public void testFrameActions() {
        driver.get("https://www.w3schools.com/xml/tryit.asp?filename=tryajax_post");
        WebDriverWait wait = new WebDriverWait(driver, 10);

        // Switch to correct iframe (notice Frame overloads)
        driver.switchTo().frame(wait.until(ExpectedConditions.elementToBeClickable(By.id("iframeResult"))));

        wait = new WebDriverWait(driver, 10);

        // same as By.xpath("//button[contains(text(),'Request data')]")
        wait.until(d -> d.findElement(By.xpath(("//button[contains(.,'Request data')]")))).click();

        By locator = By.cssSelector("#demo > p:nth-child(1)");
        assertEquals("This content was requested using the POST method.", wait.until(d -> d.findElement(locator)).getText());
    }

    @Test
    public void testFramesAvailable() {
        driver.get("https://www.w3schools.com/html/html_iframe.asp");

        List<WebElement> ele = driver.findElements(By.tagName("iframe"));
        System.out.println("Number of frames in a page :" + ele.size());

        ele.forEach(webElement -> {
            //Returns the id of a frame.
            System.out.println("Frame id :" + webElement.getAttribute("id"));
            //Returns the name of a frame.
            System.out.println("Frame name :" + webElement.getAttribute("name"));
        });
    }

    @Test
    public void testSpecificBrowser() {
        Capabilities capabilities = ((RemoteWebDriver) driver).getCapabilities();
        String browserName = capabilities.getBrowserName();
        if ("chrome".equals(browserName)) {
            System.out.println("You're running tests under " + browserName);
            // do something chrome specific
        } else if ("firefox".equals(browserName)) {
            System.out.println("You're running tests under " + browserName);
            // do something } specific
        } else if ("edge".equals(browserName)) {
            System.out.println("You're running tests under " + browserName);
            // do something edge specific
        } else {
            throw new RuntimeException("Unknown browser: " + browserName);
        }
    }

    @AfterEach
    public void AfterEach() {
    }

    @AfterAll
    public static void AfterAll() {
        driver.quit();
    }
}
