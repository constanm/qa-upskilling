package com.selenium.test.junit.tests;

import com.selenium.test.junit.tests.rules.ScreenShotOnFailRule;
import com.selenium.test.pages.GoogleSearchPage;
import com.selenium.test.pages.GoogleSearchResultsPage;
import com.selenium.test.pages.PageGenerator;
import com.selenium.test.pages.SeleniumHqPage;
import com.selenium.test.webtestsbase.WebDriverFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Uses JUnit test framework
 * Test demonstrates work with Page Factory Pattern(https://github.com/SeleniumHQ/selenium/wiki/PageFactory)
 * Test demonstrates work with Page Object Pattern(https://github.com/SeleniumHQ/selenium/wiki/PageObjects)
 */
public class PageObjectTest {
    @Rule
    public ScreenShotOnFailRule screenShotOnFailRule = new ScreenShotOnFailRule();
//todo: why junit 4?
    @BeforeClass
    public static void beforeTest() {
        WebDriverFactory.startBrowser(true);
    }

    @Test
    public void testSearch() {
        String toSearch = "Selenium";

        GoogleSearchPage googleSearchPage = new PageGenerator(WebDriverFactory.getDriver()).getInstance(GoogleSearchPage.class);
        GoogleSearchResultsPage googleSearchResultsPage = googleSearchPage.searchFor("Selenium");
        SeleniumHqPage seleniumHqPage = googleSearchResultsPage.clickOnFirstHit();

        // ouch Submit vs Go!
        assertEquals("Wrong text on Submit button", "Submit", seleniumHqPage.getSubmit().getAttribute("value"));
    }

    @AfterClass
    public static void afterClass() {
        WebDriverFactory.finishBrowser();
    }
}
