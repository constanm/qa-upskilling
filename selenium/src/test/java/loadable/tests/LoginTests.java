package loadable.tests;

import loadable.pages.HomePage;
import loadable.pages.LoginPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;


public class LoginTests extends BaseTest {
    //Test Data
    private String wrongUsername = "dummy@email.com";
    private String wrongPassword = "12345";
    private HomePage homePage;
    private LoginPage loginPage;

    @BeforeMethod(description = "Method Level Setup!")
    public void methodLevelSetup() {
        //Instantiate pages for tests
        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver, homePage);

        //Navigate to login page
        loginPage.get();
    }

    @Test(priority = 2, description = "Invalid Login Scenario with wrong username and password.")
    public void whenWrongCredentialsErrorMessageFailedAuthenticationIsDisplayed() {
        //Try to sign in
        loginPage.signInWith(wrongUsername, wrongPassword);

        //Assertions
        assertThat(loginPage.readError(), containsString("Authentication failed"));
    }

    @Test(priority = 1, description = "Invalid Login Scenario with no username and password.")
    public void whenNoCredentialsErrorMessageRequiredPasswordIsDisplayed() {
        //Try to sign in
        loginPage.signInWith("", "");

        //Assertions
        assertThat(loginPage.readError(), containsString("An email address required."));
    }
}
