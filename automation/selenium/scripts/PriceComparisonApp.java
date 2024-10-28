package com.priceprize.selenium.scripts;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PriceComparisonApp {

    private static final Logger logger = LogManager.getLogger(PriceComparisonApp.class);

    public static void main(String[] args) {
        String productName = "lipstick"; // Change this to search for different products

        AmazonScraper amazonScraper = new AmazonScraper();
        FlipkartScraper flipkartScraper = new FlipkartScraper();
        MeeshoScraper meeshoScraper = new MeeshoScraper();
        NykaaScraper nykaaScraper = new NykaaScraper();

        Map<String, List<String>> allPrices = new HashMap<>();

        logger.info("Starting price comparison for product: {}", productName);
        allPrices.put("Amazon", amazonScraper.searchProduct(productName));
        allPrices.put("Flipkart", flipkartScraper.searchProduct(productName));
        allPrices.put("Meesho", meeshoScraper.searchProduct(productName));
        allPrices.put("Nykaa", nykaaScraper.searchProduct(productName));

        amazonScraper.close();
        flipkartScraper.close();
        meeshoScraper.close();
        nykaaScraper.close();

        printPrices(allPrices);
    }

    private static void printPrices(Map<String, List<String>> allPrices) {
        System.out.println("Price Comparison:");
        for (Map.Entry<String, List<String>> entry : allPrices.entrySet()) {
            String platform = entry.getKey();
            List<String> prices = entry.getValue();
            System.out.println(platform + ": " + prices);
        }
    }
}
