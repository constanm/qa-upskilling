package objects.pages;

import objects.exceptions.WrongPageException;
import objects.webtestsbase.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class GoogleSearchPage extends BasePage {
    @FindBy(name = "q")
    private WebElement searchBox;

    public GoogleSearchPage(WebDriver driver) {
        super(driver);
        WebDriverFactory.getDriver().get("http://google.com");
        String title = WebDriverFactory.getDriver().getTitle();
        if (!title.equals("Google")) {
            throw new WrongPageException("Incorrect page title for Google: " + title);
        }
        // todo: fix consent
    }

    public GoogleSearchResultsPage searchFor(String text) {
        searchBox.sendKeys(text);
        searchBox.submit();
        return PageFactory.initElements(WebDriverFactory.getDriver(), GoogleSearchResultsPage.class);
    }

}
