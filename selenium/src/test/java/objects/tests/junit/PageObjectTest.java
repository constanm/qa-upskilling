package objects.tests.junit;

import objects.pages.GoogleSearchPage;
import objects.pages.GoogleSearchResultsPage;
import objects.pages.PageGenerator;
import objects.pages.SeleniumHqPage;
import objects.tests.junit.rules.ScreenShotOnFailRule;
import objects.webtestsbase.WebDriverFactory;
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
        GoogleSearchResultsPage googleSearchResultsPage = googleSearchPage.searchFor(toSearch);
        SeleniumHqPage seleniumHqPage = googleSearchResultsPage.clickOnFirstHit();

        // ouch Submit vs Go!
        assertEquals("Wrong text on Submit button", "Submit", seleniumHqPage.getSubmit().getAttribute("value"));
    }

    @AfterClass
    public static void afterClass() {
        WebDriverFactory.finishBrowser();
    }
}
