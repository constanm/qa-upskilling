package com.selenium.test.testng.tests;

import com.selenium.test.pages.GoogleSearchPage;
import com.selenium.test.pages.GoogleSearchResultsPage;
import com.selenium.test.pages.PageGenerator;
import com.selenium.test.pages.SeleniumHqPage;
import com.selenium.test.testng.tests.listeners.ScreenShotOnFailListener;
import com.selenium.test.webtestsbase.WebDriverFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Uses TestNG test framework
 * Test demonstrates work with Page Factory Pattern(https://github.com/SeleniumHQ/selenium/wiki/PageFactory)
 * Test demonstrates work with Page Object Pattern(https://github.com/SeleniumHQ/selenium/wiki/PageObjects)
 */
@Listeners({ScreenShotOnFailListener.class})
public class PageObjectAndPageFactoryTest {
    @BeforeTest
    public void beforeTest() {
        WebDriverFactory.startBrowser(true);
    }

    @Test
    public void testSearchResults() {
        String toSearch = "Selenium";

        // initializes a new Google Page
        GoogleSearchPage googleSearchPage = new PageGenerator(WebDriverFactory.getDriver()).getInstance(GoogleSearchPage.class);
        assertTrue(false);
        // initializes the Google page results
        GoogleSearchResultsPage googleSearchResultsPage = googleSearchPage.searchFor(toSearch);
        // clicks on first hit and checks page title
        SeleniumHqPage seleniumHqPage = googleSearchResultsPage.clickOnFirstHit();

        //checks that the submit button's text is Go
        assertThat("Unexpected first hit Go button", seleniumHqPage.getSubmit().getAttribute("value"), is(equalTo("Go")));
    }

    @AfterTest
    public void afterTest() {
        WebDriverFactory.finishBrowser();
    }
}
