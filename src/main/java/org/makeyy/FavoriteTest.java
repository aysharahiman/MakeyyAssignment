package org.makeyy;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class FavoriteTest {

    public static void main(String[] args) {
        // Test in Chrome
        System.out.println("Testing in Chrome:");
        WebDriver chromeDriver = new ChromeDriver();
        runTest(chromeDriver);

        // Test in Firefox
        System.out.println("Testing in Firefox:");
        WebDriver firefoxDriver = new FirefoxDriver();
        runTest(firefoxDriver);

        // Test in Edge
        System.out.println("Testing in Edge:");
        WebDriver edgeDriver = new EdgeDriver();
        runTest(edgeDriver);
    }

    public static void runTest(WebDriver driver) {
        try {
            // Open the URL
            driver.get("https://Makyee.com");

            // Create an explicit wait
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Wait for the property containers to be visible
            List<WebElement> propertyContainers = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("body > div:nth-child(2) > div:nth-child(1) > section:nth-child(10) > div:nth-child(1) > div:nth-child(3) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2)")));
            if (propertyContainers.isEmpty()) {
                System.out.println("No property containers found.");
                return;
            }

            // Click the first property container to go to the details page
            WebElement firstContainer = propertyContainers.get(0);
            firstContainer.click();

            // Wait for the details page to load by checking the presence of an element on the details page
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".col-lg-1")));

            // Wait for the favorite button to be present
            WebElement favoriteButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html[1]/body[1]/div[1]/div[1]/section[1]/div[1]/div[2]/div[1]/div[2]/div[1]/div[4]/div[1]/div[1]/div[1]/div[3]/div[4]/div[1]")));

            // Click the favorite button
            favoriteButton.click();

            // Handle the dialogue message
            try {
                WebDriverWait dialogWait = new WebDriverWait(driver, Duration.ofSeconds(10));
                WebElement dialog = dialogWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@role='dialog']")));
                String dialogText = dialog.getText();

                // Assert that the dialog message contains the expected text
                String expectedErrorMessage = "You need to login first";
                if (dialogText.contains(expectedErrorMessage)) {
                    System.out.println("Favorite functionality test passed!");
                } else {
                    System.out.println("Favorite functionality test failed! Expected message: " + expectedErrorMessage + ", Actual message: " + dialogText);
                }
            } catch (TimeoutException e) {
                System.out.println("Error: Dialog box did not appear within the timeout period.");
            } catch (Exception e) {
                System.out.println("Error: Exception while handling the dialog box.");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            // Close the browser
            System.out.println("Finished testing");
            driver.quit();
        }
    }
}
