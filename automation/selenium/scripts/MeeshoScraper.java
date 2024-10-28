package com.priceprize.selenium.scripts;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class MeeshoScraper {

    private SeleniumConfig config;
    private WebDriver driver;

    public MeeshoScraper() {
        config = new SeleniumConfig();
        driver = config.getDriver();
    }

    public void close() {
        config.closeDriver();
    }

    public List<String> searchProduct(String productName) {
        List<String> prices = new ArrayList<>();
        try {
            driver.get("https://www.meesho.com");

            // Wait for the search box to be visible and input product name
            WebElement searchBox = new WebDriverUtils(driver).waitForElementToBeVisible(By.name("search"), 10);
            searchBox.sendKeys(productName);
            searchBox.submit();

            // Wait for the results to load and get prices
            prices = getProductPrices();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prices;
    }

    private List<String> getProductPrices() {
        List<String> prices = new ArrayList<>();
        List<WebElement> priceElements = driver.findElements(By.cssSelector(".price")); // Example selector for Meesho prices
        for (WebElement priceElement : priceElements) {
            prices.add(priceElement.getText());
        }
        return prices;
    }

    public static void main(String[] args) {
        MeeshoScraper scraper = new MeeshoScraper();
        try {
            List<String> prices = scraper.searchProduct("dress");
            System.out.println(prices);
        } finally {
            scraper.close();
        }
    }
}
