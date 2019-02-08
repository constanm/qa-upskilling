package loadable.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.testng.Assert.assertTrue;

public class LoginPage extends LoadableComponent<LoginPage> {

    private WebDriver driver;
    private WebDriverWait wait;
    private BasePage page;
    private LoadableComponent<HomePage> parent;
    private final String loginURL = "http://automationpractice.com/index.php?controller=authentication&back=my-account";

    public LoginPage(WebDriver driver, LoadableComponent<HomePage> parent) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
        page = new BasePage(this.driver);
        this.parent = parent;
    }

    By usernameBy = By.id("email");
    By passwordBy = By.id("passwd");
    By loginButtonBy = By.id("SubmitLogin");
    By errorMessage = By.cssSelector("#center_column > div.alert.alert-danger > ol > li");

    @Override
    protected void load() {
        parent.get().goToLoginPage();
    }

    //We need to check that the page has been loaded.
    @Override
    protected void isLoaded() throws Error {
        assertTrue(driver.getCurrentUrl().contains(loginURL), "LoginPage is not loaded!");
    }

    //How to improve with Fluent API?
    public void signInWith(String username, String password) {
        //Enter Username(Email)
        page.writeText(usernameBy, username);
        //Enter Password
        page.writeText(passwordBy, password);
        //Click Login Button
        page.click(loginButtonBy);
    }

    public String readError() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage));
        return page.readText(errorMessage);
    }
}
