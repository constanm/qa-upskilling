package loadable.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.LoadableComponent;

import static loadable.pages.BasePage.myElementIsClickable;
import static org.testng.Assert.assertTrue;

public class HomePage extends LoadableComponent<HomePage> {

    private String baseURL = "http://automationpractice.com/";
    private WebDriver driver;
    private BasePage basePage;
    private By signInLinkBy = By.linkText("Sign in");
    private By signInButtonBy = By.className("login");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        basePage = new BasePage(driver);
    }

    //We need to go to the page at load method
    @Override
    protected void load() {
        this.driver.get(baseURL);
    }

    //We need to check that the page has been loaded.
    @Override
    protected void isLoaded() throws Error {
        assertTrue(myElementIsClickable(signInLinkBy), "HomePage is not loaded!");
    }

    //Go to LoginPage method
    public LoginPage goToLoginPage() {
        basePage.click(signInButtonBy);
        return new LoginPage(this.driver, this);
    }

}
