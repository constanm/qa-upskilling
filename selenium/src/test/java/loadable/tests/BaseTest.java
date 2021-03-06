package loadable.tests;

import objects.configuration.Configuration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseTest {
    public WebDriver driver;
    public WebDriverWait wait;

    @BeforeClass(description = "Class Level Setup!")
    public void classLevelSetup() {
        //Create a Chrome driver. All test classes from this package use this.
        System.setProperty("webdriver.chrome.driver", Configuration.getConfig().getChromedriver());
        driver = new ChromeDriver();

        //Create a wait. All test classes use this.
        wait = new WebDriverWait(driver, 15);

        //Maximize Window
        driver.manage().window().maximize();
    }

    @AfterClass(description = "Class Level Teardown!")
    public void classLevelTeardown() {
        driver.quit();
    }

}
