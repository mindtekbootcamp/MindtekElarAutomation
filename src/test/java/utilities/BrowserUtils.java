package utilities;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

public class BrowserUtils {
    /**
     * Creates a Select object and selects an option
     * based on provided "value" parameter
     *
     * @param dropdown
     * @param value
     */
    public static void selectByValue(WebElement dropdown, String value) {
        Select select = new Select(dropdown);
        select.selectByValue(value);
    }

    /**
     * Waits for provided WebElement to be clickable
     *
     * @param element
     */
    public static void waitForElementToBeClickable(WebElement element) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), 10);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Scrolls the page until the provided element is in view
     *
     * @param element
     */
    public static void scrollIntoView(WebElement element) {
        JavascriptExecutor jse = (JavascriptExecutor) Driver.getDriver();
        jse.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    /**
     * Takes a screenshot of the browser at the moment of execution
     *
     * @param fileName
     * @throws IOException
     */
    public static void takeScreenshot(String fileName) throws IOException {
        String path = "src/test/resources/screenshots/" + fileName + ".png";
        File screenshot = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.FILE);
        File file = new File(path);
        FileUtils.copyFile(screenshot, file);
    }

    /**
     * Removes all Google Ads from a page
     */
    public static void removeGoogleAds() {
        JavascriptExecutor jse = ((JavascriptExecutor) Driver.getDriver());
        jse.executeScript("const elements = document.getElementByClassName('adsbygoogle adsbygoogle-noablate'); while (elements.length > 0) elements[0].remove()");
    }

    /**
     * Generates a random email based on provided parameters
     *
     * @param firstName
     * @param lastName
     * @return
     */
    public static String randomEmailGenerator(String firstName, String lastName) {
        Random random = new Random();
        int num = random.nextInt(999999);
        return firstName + "." + lastName + num + "gmail.com";
    }

    /**
     * Generates a UUID random email
     *
     * @return
     */
    public static String uuidEmailGenerator() {
        UUID uuid = UUID.randomUUID();
        return "User-" + uuid + "@gmail.com";
    }

    /**
     * Types text into inputs that are masked / React-controlled (ex: MUI date inputs).
     * Works by: click -> select all -> backspace -> type -> TAB (commit/blur).
     *
     * @param element
     * @param text
     */
    public static void typeAndTab(WebElement element, String text) {
        WebDriver driver = Driver.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.elementToBeClickable(element));

        try {
            element.click();
        } catch (Exception e) {
            new Actions(driver).moveToElement(element).click().perform();
        }

        clearInput(element);

        element.sendKeys(text);
        element.sendKeys(Keys.TAB);
    }

    public static void clearInput(WebElement element) {
        try {
            element.sendKeys(Keys.chord(Keys.COMMAND, "a"));
            element.sendKeys(Keys.BACK_SPACE);
            return;
        } catch (Exception ignored) {
            System.out.println("Exception ignored");
        }
    }

    /**
     * This method safely clicks on a WebElement by handling common interaction issues
     * (click interception, dynamic UI overlays, stale elements, and viewport positioning)
     *
     * @param element
     */
    public static void clickSafely(WebElement element) {
        WebDriver driver = Driver.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        By muiOverlay = By.cssSelector(
                ".MuiBackdrop-root, .MuiDialog-root, .MuiPopover-root, .MuiMenu-root"
        );
        for (int i = 0; i < 4; i++) {
            try {
                wait.until(ExpectedConditions.invisibilityOfElementLocated(muiOverlay));

                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].scrollIntoView({block:'center', inline:'nearest'});",
                        element
                );

                wait.until(ExpectedConditions.elementToBeClickable(element));

                new Actions(driver)
                        .moveToElement(element)
                        .click()
                        .perform();
                return;

            } catch (ElementClickInterceptedException e) {
                // Click intercepted by overlay or animation -> wait and retry
                wait.until(ExpectedConditions.invisibilityOfElementLocated(muiOverlay));
            } catch (StaleElementReferenceException e) {
                // DOM refreshed → retry loop
            }
        }
        throw new RuntimeException("Could not click element safely after retries");
    }

    public static void clickSafelyBy(By locator) {
        WebDriver driver = Driver.getDriver();
        WebDriverWait wait = new WebDriverWait(driver,10);

        By muiOverlay = By.cssSelector(".MuiBackdrop-root, .MuiDialog-root, .MuiPopover-root, .MuiMenu-root");

        for (int i = 0; i < 5; i++) {
            try {
                wait.until(ExpectedConditions.invisibilityOfElementLocated(muiOverlay));

                WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));

                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].scrollIntoView({block:'center', inline:'nearest'});",
                        element
                );

                element = wait.until(ExpectedConditions.elementToBeClickable(locator));

                try {
                    element.click();
                } catch (ElementClickInterceptedException e) {
                    // fallback: JS click
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
                }

                return;

            } catch (StaleElementReferenceException | ElementClickInterceptedException e) {
                // React re-render / animation — retry
            }
        }
        throw new RuntimeException("Could not click element safely after retries: " + locator);
    }



}
