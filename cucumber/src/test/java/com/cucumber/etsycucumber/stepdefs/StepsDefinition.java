package com.cucumber.etsycucumber.stepdefs;

import com.cucumber.etsycucumber.pageobjects.EtsyComPageObject;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.stringContainsInOrder;

public class StepsDefinition {

    private WebDriver webDriver;
    private EtsyComPageObject etsyPage;

    @Before
    public void initializeTest() {
        System.out.println("Starting our Cucumber tests");
        ChromeOptions options = new ChromeOptions();
        boolean isHeadless = Boolean.valueOf(System.getProperty("isHeadless"));
        options.setHeadless(isHeadless);
        System.setProperty("webdriver.chrome.driver", System.getProperty("chromeDriverBinaryPath") + "/chromedriver.exe");
        webDriver = new ChromeDriver(options);
    }
    //Given

    @Given("^I am on the main page$")
    public void goToMainPage() {
        etsyPage = new EtsyComPageObject(webDriver);
    }

    // When section

    @When("^I accept terms and conditions$")
    public void i_accept_terms_and_conditons() {
        etsyPage.acceptTermsAndConditions();
    }

    @When("^I search for \"(.*?)\" items$")
    public void search_for_items(String query) {
        etsyPage.searchForItem(query);
    }

    @When("^I search for items and apply filters:$")
    public void search_for_items_and_apply_filters(DataTable table) {
        List<List<String>> values = table.asLists();
        String searchQuery = values.get(1).get(0);
        String category = values.get(1).get(1);
        String filter = values.get(1).get(2);
        etsyPage.searchForItem(searchQuery);
        if (etsyPage.isSearchResultsVisible()) {
            etsyPage.applyFilterFromCategory(category, filter);
        }
    }

    @When("^I select \"(.*?)\" as a shop location$")
    public void selectShopLocationForSearchResults(String location) {
        etsyPage.selectShopLocation(location);
    }

    @When("^I apply \"(.*?)\" shipping filter$")
    public void applyShippingFilterOnSearchResults(String filter) {
        etsyPage.applyFilterFromCategory("Shipping", filter);
    }

    @When("^I apply \"(.*?)\" shop location filter$")
    public void applyShopLocationFilterOnSearchResults(String filter) {
        //implement new custom shop location
        etsyPage.applyFilterFromCategory("Shop location", filter);
    }

    @When("^I apply \"(.*?)\" item type filter$")
    public void applyItemTypeFilterOnSearchResults(String filter) {
        etsyPage.applyFilterFromCategory("Item type", filter);
    }

    @When("^I apply \"(.*?)\" special offers filter$")
    public void applySpecialOffersFilterOnSearchResults(String filter) {
        etsyPage.applyFilterFromCategory("Special offers", filter);
    }

    //Then section
    @Then("^I see search results$")
    public void isSearchResultsTableVisible() {
        assertThat("Search results is visible", etsyPage.isSearchResultsVisible());
    }

    @Then("^I get search results for \"(.*?)\" items$")
    public void i_get_results_page_for_search_query(String items) {
        assertThat(etsyPage.getTitle(), stringContainsInOrder(Arrays.asList(items.split(" "))));
    }

    @Then("^next filter tags are visible:$")
    public void checkAppliedFilterTagsOnSearchResults(DataTable table) {
        List<String> expectedTags = table.rows(1).asList();
        //expectedTags.forEach(System.out::println);
        List<String> actualTags = etsyPage.getAppliedFilterTagsForSearchResults();
        for (String tag : actualTags) {
            Assertions.assertTrue(expectedTags.contains(tag), "Checking tag " + tag);
        }
    }


    @After
    public void tearDown(Scenario scenario) {
        System.out.println("Cucumber tests ended");
        if (scenario.isFailed()) {
            etsyPage.captureScreenShot();
        }
        webDriver.quit();
    }

}
