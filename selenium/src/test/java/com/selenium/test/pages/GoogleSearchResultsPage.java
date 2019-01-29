package com.selenium.test.pages;

import com.selenium.test.exceptions.WrongPageException;
import com.selenium.test.webtestsbase.WebDriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class GoogleSearchResultsPage extends BasePage {
    @FindBy(partialLinkText = "Web Browser Automation")
    private WebElement firstHit;

    public GoogleSearchResultsPage(WebDriver driver) {
        super(driver);
        if (!WebDriverFactory.getDriver().getTitle().contains("Google")) {
            throw new WrongPageException("Incorrect page title for Google Search Results");
        }
    }

    public SeleniumHqPage clickOnFirstHit() {
        wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("Web Browser Automation")));
        firstHit.click();
        return PageFactory.initElements(WebDriverFactory.getDriver(), SeleniumHqPage.class);
    }
}
