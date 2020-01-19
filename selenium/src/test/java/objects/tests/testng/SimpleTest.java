package objects.tests.testng;

import objects.tests.testng.listeners.ScreenShotOnFailListener;
import objects.webtestsbase.WebDriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.junit.Assert.assertEquals;

/**
 * Uses TestNG test framework
 * Test demonstrates simple webdriver functions : how to start browser, open url, insert some text and check that this text was inserted
 */
@Listeners({ScreenShotOnFailListener.class})
public class SimpleTest {

    @BeforeTest
    public void beforeTest() {
        WebDriverFactory.startBrowser(true);
    }

    @Test
    public void testFillForm() throws InterruptedException {
        String toSearch = "Selenium";
        WebDriverFactory.getDriver().get("http://www.google.com");
        WebElement searchBox = WebDriverFactory.getDriver().findElement(By.name("q"));
        searchBox.sendKeys(toSearch);
        searchBox.sendKeys(Keys.ENTER);

        Thread.sleep(2000);

        assertEquals("Kaboom!", toSearch + " - Google Search", WebDriverFactory.getDriver().getTitle());
    }

    @AfterTest
    public void afterTest() {
        WebDriverFactory.finishBrowser();
    }

}
