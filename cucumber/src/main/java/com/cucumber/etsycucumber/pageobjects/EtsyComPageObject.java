package com.cucumber.etsycucumber.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.stream.Collectors;

public class EtsyComPageObject extends BasePageObject {
    private static final String SEARCH_BUTTON_WRAPPER = ".search-button-wrapper > button:nth-child(1)";
    private final String SHOP_LOCATION_SEARCH_BUTTON = "button.shop-location-submit";
    private final String SHOP_LOCATION_TEXT_FIELD = "#shop-location-input";
    private final String TERMS_AND_CONDITIONS_WRAPPER = "div.alert";
    private final String ACCEPT_POLICY_BUTTON = "button.btn-outline-black";
    private final String SEARCH_FIELD = "#search-query";
    private String FILTER_FOR_CATEGORY_LINK = "/html/body/div[7]/div/div[1]/div/div/div[1]/div/div/form/div[3]/fieldset/div/label/a"; //Check

    public EtsyComPageObject(WebDriver webDriver) {
        super(webDriver);
        webDriver.get("http://www.etsy.com/uk");
    }

    public void acceptTermsAndConditions() {
        findElementWithWait(By.cssSelector(TERMS_AND_CONDITIONS_WRAPPER));
        findElementWithWait(By.cssSelector(ACCEPT_POLICY_BUTTON)).click();
    }

    public void searchForItem(String query) {
        enterSearchQuery(query);
        getSearchButton().click();
    }

    private WebElement getSearchButton() {
        return findElementWithWait(By.cssSelector(SEARCH_BUTTON_WRAPPER));
    }

    private void enterSearchQuery(String query) {
        WebElement searchField = findElementWithWait(By.cssSelector(SEARCH_FIELD));
        searchField.clear();
        searchField.sendKeys(query);
    }

    public String getTitle() {
        return webDriver.getTitle().toLowerCase();
    }

    public boolean isSearchResultsVisible() {
        return findElementWithWait(By.cssSelector("div.search-listings-group")).isDisplayed();
    }

    public void applyFilterFromCategory(String category, String filter) {
        WebElement filterLink = checkFilterForCategorySection(filter, category);
        filterLink.click();
    }

    private WebElement checkFilterForCategorySection(String filter, String category) {
        String xpath = String.format(FILTER_FOR_CATEGORY_LINK, category, filter);
        return new WebDriverWait(webDriver, 10)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }

    public List<String> getAppliedFilterTagsForSearchResults() {
        return findElementsWithWait(By.cssSelector(".tag")).stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public void selectShopLocation(String location) {
        enterLocationToShopLocationField(location);
        //selectFirstItemInShopLocationSuggestionsList();
        clickShopLocationSearchButton();
    }

    private void clickShopLocationSearchButton() {
        findElementWithWait(By.cssSelector(SHOP_LOCATION_SEARCH_BUTTON)).click();
    }

    private void enterLocationToShopLocationField(String location) {
        WebElement shopLocationText = findElementWithWait(By.cssSelector(SHOP_LOCATION_TEXT_FIELD));
        shopLocationText.clear();
        shopLocationText.sendKeys(location);
    }
}
