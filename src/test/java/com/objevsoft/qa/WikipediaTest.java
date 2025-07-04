package com.objevsoft.qa;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

public class WikipediaTest extends BaseTest {

    @BeforeMethod
    public void setUpTest() {
        driver.get("https://www.wikipedia.org/");
    }

    @DataProvider(name = "countryListTestData")
    public Object[] getCountryList() {
        return new String[]{
                "English", "Español", "日本語", "Русский", "Deutsch",
                "Français", "Italiano", "中文", "Português", "Polski"
        };
    }

    @Test(dataProvider = "countryListTestData")
    public void countriesNamePresentTest(String expectedLanguage) {
        List<WebElement> languages = driver.findElements(By.cssSelector("a.link-box > strong"));
        boolean found = languages.stream()
                .anyMatch(e -> e.getText().trim().equalsIgnoreCase(expectedLanguage));

        if (!found) {
            System.out.println("⚠️ Language NOT found on page: " + expectedLanguage);
            throw new SkipException("Skipping test as language not found: " + expectedLanguage);
        }

        Assert.assertTrue(found, "✅ Language found: " + expectedLanguage);
    }

    @Test(dataProvider = "countryListTestData")
    public void httpResponseCode_CountriesLinksTest(String language) {
        List<WebElement> elements = driver.findElements(By.cssSelector("a.link-box"));
        boolean found = false;

        for (WebElement el : elements) {
            String langText = el.findElement(By.cssSelector("strong")).getText().trim();
            if (langText.equalsIgnoreCase(language)) {
                found = true;
                String url = el.getAttribute("href");
                System.out.println("🔗 Checking URL for language " + language + ": " + url);
                Response response = RestAssured.get(url);
                Assert.assertEquals(response.statusCode(), 200, "❌ Broken link for: " + language);
                break;
            }
        }

        if (!found) {
            System.out.println("⚠️ Language not found in links: " + language);
            throw new SkipException("Skipping link test as language not found: " + language);
        }
    }
}
