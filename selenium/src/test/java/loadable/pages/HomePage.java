package loadable.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.testng.Assert.assertTrue;

public class HomePage extends LoadableComponent<HomePage> {

    private String baseURL = "http://automationpractice.com/";
    private WebDriver driver;
    private WebDriverWait wait;
    private BasePage basePage;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
        basePage = new BasePage(driver);
    }

    By signInButtonBy = By.className("login");

    //We need to go to the page at load method
    @Override
    protected void load() {
        this.driver.get(baseURL);
    }

    //We need to check that the page has been loaded.
    @Override
    protected void isLoaded() throws Error {
        assertTrue(driver.getCurrentUrl().contains(baseURL), "HomePage is not loaded!");
    }

    //Go to LoginPage method
    public LoginPage goToLoginPage() {
        basePage.click(signInButtonBy);
        return new LoginPage(this.driver, this);
    }

}
