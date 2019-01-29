package com.selenium.test.junit.tests;

import com.selenium.test.junit.tests.rules.ScreenShotOnFailRule;
import com.selenium.test.webtestsbase.WebDriverFactory;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.assertEquals;

/**
 * Uses JUnit4 test framework
 * Test demonstrates simple webdriver functions : how to start browser, open url, insert some text and check that this text was inserted
 */
//todo: Junit 5?
public class SimpleTest {
    @Rule
    public ScreenShotOnFailRule screenShotOnFailRule = new ScreenShotOnFailRule();

    @Before
    public void beforeTest() {
        WebDriverFactory.startBrowser(true);
    }

    @Test
    public void testFillForm() {
        // ouch, failing
        String toSearch = "Selenium";
        WebDriverFactory.getDriver().get("http://www.youtube.com");
        WebElement searchString = WebDriverFactory.getDriver().findElement(By.cssSelector("#masthead-search-term"));
        searchString.sendKeys(toSearch);
        String searchStringText = searchString.getAttribute("value");
        assertEquals("Text from page(" + searchStringText + ") not equals to text from test(" + toSearch + ")", searchStringText, toSearch);
    }

    @AfterClass
    public static void afterClass() {
        WebDriverFactory.finishBrowser();
    }

}
