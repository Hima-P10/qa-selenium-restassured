package com.objevsoft.qa;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class DemoSiteTest extends BaseTest {

    @Test
    public void testFormSubmission() throws InterruptedException {
        driver.get("https://demoqa.com/automation-practice-form");

        // Fill the form
        driver.findElement(By.id("firstName")).sendKeys("John");
        driver.findElement(By.id("lastName")).sendKeys("Doe");
        driver.findElement(By.id("userEmail")).sendKeys("john@example.com");
        driver.findElement(By.cssSelector("label[for='gender-radio-1']")).click(); // Male
        driver.findElement(By.id("userNumber")).sendKeys("9876543210");

        // Scroll to Submit button to avoid iframe interference
        WebElement submitButton = driver.findElement(By.id("submit"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitButton);
        Thread.sleep(1000); // wait for scroll

        // Click using JS to avoid click interception by ad iframe
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);

        // Wait for the modal to appear and validate the submission
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement modalTitle = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.id("example-modal-sizes-title-lg"))
        );

        Assert.assertEquals(modalTitle.getText().trim(), "Thanks for submitting the form");
    }
}

