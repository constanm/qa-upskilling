package com.selenium.test.pages;

import com.selenium.test.exceptions.WrongPageException;
import com.selenium.test.webtestsbase.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SeleniumHqPage extends BasePage {

    @FindBy(id = "submit")
    private WebElement submit;

    public SeleniumHqPage(WebDriver driver) {
        super(driver);

        if (!WebDriverFactory.getDriver().getTitle().contains("Selenium")) {
            throw new WrongPageException("Incorrect page title for Selenium HQ");
        }
    }

    public WebElement getSubmit() {
        return submit;
    }
}
