package objects.pages;

import objects.exceptions.WrongPageException;
import objects.webtestsbase.WebDriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class GoogleSearchResultsPage extends BasePage {
    @FindBy(partialLinkText = "Selenium WebDriver")
    private WebElement firstHit;

    public GoogleSearchResultsPage(WebDriver driver) {
        super(driver);
        if (!WebDriverFactory.getDriver().getTitle().contains("Google")) {
            throw new WrongPageException("Incorrect page title for Google Search Results");
        }
    }

    public SeleniumDevPage clickOnFirstHit() {
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Selenium WebDriver")));
        firstHit.click();
        return PageFactory.initElements(WebDriverFactory.getDriver(), SeleniumDevPage.class);
    }
}
