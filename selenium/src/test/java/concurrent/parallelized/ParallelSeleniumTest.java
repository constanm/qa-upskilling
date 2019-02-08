package concurrent.parallelized;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@RunWith(Parallelized.class)
public class ParallelSeleniumTest {

    enum WebDriverType {
        CHROME,
        FIREFOX
    }

    static class WebDriverFactory {
        static WebDriver create(WebDriverType type) {
            WebDriver driver;
            switch (type) {
                case FIREFOX:
                    driver = new FirefoxDriver();
                    break;
                case CHROME:
                    driver = new ChromeDriver();
                    break;
                default:
                    throw new IllegalStateException();
            }
            log(driver, "created");
            return driver;
        }
    }

    @Parameterized.Parameter
    public WebDriverType currentDriverType;

    // test case naming requires junit 4.12
    @Parameterized.Parameters(name = "{0}")
    public static Collection<Object[]> driverTypes() {
        return Arrays.asList(new Object[][]{
                {WebDriverType.CHROME},
                {WebDriverType.FIREFOX}
        });
    }

    private static ThreadLocal<WebDriver> currentDriver = new ThreadLocal<>();
    private static List<WebDriver> driversToCleanup = Collections.synchronizedList(new ArrayList<>());

    @BeforeClass
    public static void initBrowserProperties() {
        System.setProperty("webdriver.chrome.driver", "D:\\WORK\\SELENIUM\\chromedriver.exe");

        System.setProperty("webdriver.gecko.driver","D:\\WORK\\SELENIUM\\geckodriver.exe");
        System.setProperty("webdriver.firefox.bin", "D:\\PROGRAME\\ff5010\\firefox.exe");
    }

    @Before
    public void driverInit() {
        if (currentDriver.get() == null) {
            WebDriver driver = WebDriverFactory.create(currentDriverType);
            driversToCleanup.add(driver);
            currentDriver.set(driver);
        }
    }

    private WebDriver getDriver() {
        return currentDriver.get();
    }

    @Test
    public void searchForChromeDriver() throws InterruptedException {
        openAndSearch(getDriver(), "chromedriver");
    }

    @Test
    public void searchForJunit() throws InterruptedException {
        openAndSearch(getDriver(), "junit");
    }

    @Test
    public void searchForYoutube() throws InterruptedException {
        openAndSearch(getDriver(), "youtube");
    }

    private void openAndSearch(WebDriver driver, String phraseToSearch) throws InterruptedException {
        log(driver, "search for: " + phraseToSearch);
        driver.get("http://www.google.com");
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys(phraseToSearch);
        searchBox.submit();
        Thread.sleep(10000);
    }

    @AfterClass
    public static void driverCleanup() {
        Iterator<WebDriver> iterator = driversToCleanup.iterator();
        while (iterator.hasNext()) {
            WebDriver driver = iterator.next();
            log(driver, "about to quit");
            driver.quit();
            iterator.remove();
        }
    }

    private static void log(WebDriver driver, String message) {
        System.out.println(String.format("%15s, %15s: %s", Thread.currentThread().getName(), driver.getClass().getName(), message));
    }

}
