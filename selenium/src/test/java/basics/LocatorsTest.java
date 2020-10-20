package basics;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.DriverBase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

//Note: some tests may fail. Come back after Waits..
public class LocatorsTest extends DriverBase {
    private static WebDriver driver;

    @BeforeAll
    public static void beforeAll() {
        driver = DriverBase.getDriver();
    }

    @BeforeEach
    public void beforeEach() {
        driver.navigate().to("http://automationpractice.com");
    }

    @Test
    public void testProperties() {
        assertEquals("My Store", driver.getTitle());
        assertTrue(driver.getPageSource().contains("Ecommerce software"));
    }

    @Test
    public void testByID() {
        driver.findElement(By.id("search_query_top")).sendKeys("dress");
    }

    @Test
    public void testByName() {
        driver.findElement(By.name("search_query")).sendKeys("dress");

        // Take a screenshot as proof
        DriverBase.takesScreenShot("testByName", "DressWasSearched");
    }

    @Test
    public void testByPartialLinkedText() throws InterruptedException {
        driver.findElement(By.partialLinkText("Contact")).click();

        // Pause execution of program up to 5 seconds for the page to load
        Thread.sleep(5000);

        DriverBase.takesScreenShot("testByPartialLinkedText", "Contact");
    }

    @Test
    public void testByLinkedText() {
        driver.findElement(By.linkText("Sign in")).click();

        // Wait up to 5 seconds for the page to load
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(d -> d.findElement(By.id("email_create")));

        DriverBase.takesScreenShot("testByLinkedText", "SignIn");
    }

    @Test
    public void testByXPath() {
        driver.findElement(By.xpath("id('search_query_top')")).sendKeys("dress");
        driver.findElement(By.xpath("//*[@name='submit_search']")).click();

        // Test that the expected text is visible
        assertTrue(driver.findElement(By.xpath("//span[contains(text(),'7 results have been found')]")).isDisplayed());
    }

    @Test
    public void testByXPathVariations() throws InterruptedException {
        // navigate to Sign in page
        driver.navigate().to("http://automationpractice.com/index.php?controller=authentication");
        driver.findElement(By.linkText("Sign in")).click();
        Thread.sleep(5000);
        driver.findElement(By.id("email_create")).clear();
        driver.findElement(By.id("email_create")).sendKeys("dymmy@email.com");
        driver.findElement(By.id("SubmitCreate")).click();
        Thread.sleep(5000);

        driver.findElement(By.xpath("//input[@name='id_gender' and @value='1']")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//input[@name='id_gender' and @value='2']")).click();
        Thread.sleep(2000);
        // click again - no unselect option!!
        driver.findElement(By.xpath("//input[@name='id_gender' and @value='2']")).click();
        Thread.sleep(2000);
        // click on second radio button
        driver.findElements(By.xpath("//input[@name='id_gender']")).get(1).click();

        driver.findElements(By.xpath("//input[@name='id_gender']"))
                .stream()
                .filter(radioButton -> radioButton.getAttribute("value").equals("1"))
                .forEach(WebElement::click);

        // Check that first radio button is selected
        assertTrue(driver.findElement(By.xpath("//input[@name='id_gender' and @value='1']")).isSelected());

    }

    @Test
    public void testByTag() {
        assertTrue(driver.findElement(By.tagName("body")).getText().contains("empty"));
    }


    @Test
    public void testByClassName() {
        assertEquals("a", driver.findElement(By.className("sf-with-ul")).getTagName());

        // the below will return error "Compound class names not permitted"
        // assertTrue(driver.findElement(By.className("sf-menu clearfix menu-content sf-js-enabled sf-arrows")).tagName.equals("a"));
    }

    @Test
    public void testByCSSSelector() {
        driver.findElement(By.cssSelector(".sf-menu > li:nth-child(1) > a:nth-child(1)")).click();
    }

    @Test
    public void testFindMultipleElements() {
        driver.findElements(By.tagName("a")).forEach(element -> System.out.println(element.getText()));

    }

    @Test
    public void testChainfindElement() {
        WebElement element = driver.findElement(By.id("page"))
                .findElement(By.className("header-container"))
                .findElement(By.tagName("header"));

        assertEquals("header", element.getTagName());
    }

    @AfterEach
    public void AfterEach() {
    }

    @AfterAll
    public static void AfterAll() {
        driver.quit();
    }
}
