package com.priceprize.selenium.scripts;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class NykaaScraper {

    private static final Logger logger = LogManager.getLogger(NykaaScraper.class);
    private SeleniumConfig config;
    private WebDriver driver;

    public NykaaScraper() {
        config = new SeleniumConfig();
        driver = config.getDriver();
    }

    public void close() {
        config.closeDriver();
    }

    public List<String> searchProduct(String productName) {
        List<String> prices = new ArrayList<>();
        try {
            logger.info("Searching for product: {}", productName);
            driver.get("https://www.nykaa.com");

            WebElement searchBox = new WebDriverUtils(driver).waitForElementToBeVisible(By.name("search"), 10);
            searchBox.sendKeys(productName);
            searchBox.submit();

            prices = getProductPrices();
            logger.info("Found prices: {}", prices);
        } catch (Exception e) {
            logger.error("Error while searching for product: {}", productName, e);
        }
        return prices;
    }

    private List<String> getProductPrices() {
        List<String> prices = new ArrayList<>();
        List<WebElement> priceElements = driver.findElements(By.cssSelector(".css-1d0x6or .css-1m4n1n3"));
        for (WebElement priceElement : priceElements) {
            prices.add(priceElement.getText());
        }
        return prices;
    }

    public static void main(String[] args) {
        NykaaScraper scraper = new NykaaScraper();
        try {
            List<String> prices = scraper.searchProduct("lipstick");
            System.out.println(prices);
        } finally {
            scraper.close();
        }
    }
}
