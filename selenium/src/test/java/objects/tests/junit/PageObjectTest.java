package objects.tests.junit;

import objects.pages.GoogleSearchPage;
import objects.pages.GoogleSearchResultsPage;
import objects.pages.PageGenerator;
import objects.pages.SeleniumDevPage;
import objects.tests.junit.rules.ScreenShotOnFailRule;
import objects.webtestsbase.WebDriverFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

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
        SeleniumDevPage seleniumDevPage = googleSearchResultsPage.clickOnFirstHit();

        assertTrue("Missing Blog link", seleniumDevPage.getBlogLink().isDisplayed());
    }

    @AfterClass
    public static void afterClass() {
        WebDriverFactory.finishBrowser();
    }
}
