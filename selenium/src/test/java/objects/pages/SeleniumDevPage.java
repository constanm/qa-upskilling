package objects.pages;

import objects.exceptions.WrongPageException;
import objects.webtestsbase.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SeleniumDevPage extends BasePage {

    @FindBy(linkText = "Blog")
    private WebElement blogLink;

    public SeleniumDevPage(WebDriver driver) {
        super(driver);

        if (!WebDriverFactory.getDriver().getTitle().contains("SeleniumHQ Browser Automation")) {
            throw new WrongPageException("Incorrect page title for Selenium Dev page");
        }
    }

    public WebElement getBlogLink() {
        return blogLink;
    }
}
