package com.priceprize.selenium.scripts;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;
import java.util.List;

public class PriceScraper {

    private SeleniumConfig config;
    private WebDriverUtils utils;

    public PriceScraper() {
        config = new SeleniumConfig();
        utils = new WebDriverUtils(config.getDriver());
    }

    public void close() {
        config.closeDriver();
    }

    public List<String> searchProduct(String url, String productName) {
        WebDriver driver = config.getDriver();
        List<String> prices = new ArrayList<>();
        try {
            driver.get(url);
            // Wait for the search box to be visible
            WebElement searchBox = utils.waitForElementToBeVisible(By.name("q"), 10);
            searchBox.sendKeys(productName);
            searchBox.submit();

            // Wait for results to load and get prices
            prices = getProductPrices(driver);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prices;
    }

    private List<String> getProductPrices(WebDriver driver) {
        List<String> prices = new ArrayList<>();
        List<WebElement> productElements = driver.findElements(By.cssSelector(".product-price")); // Example selector
        for (WebElement product : productElements) {
            prices.add(product.getText());
        }
        return prices;
    }

    public static void main(String[] args) {
        PriceScraper scraper = new PriceScraper();
        try {
            List<String> prices = scraper.searchProduct("https://www.flipkart.com", "laptop"); // Example for Flipkart
            System.out.println(prices);
        } finally {
            scraper.close();
        }
    }
}

