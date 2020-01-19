package objects.tests.junit;

import objects.tests.junit.rules.ScreenShotOnFailRule;
import objects.webtestsbase.WebDriverFactory;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.assertEquals;

/**
 * Uses JUnit4 test framework
 * Test demonstrates simple webdriver functions : how to start browser, open url, insert some text and check that this text was inserted
 */
public class SimpleTest {
    @Rule
    public ScreenShotOnFailRule screenShotOnFailRule = new ScreenShotOnFailRule();

    @AfterClass
    public static void afterClass() {
        WebDriverFactory.finishBrowser();
    }

    @Before
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

}
