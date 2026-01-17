package elar.api.auth;

import elar.ui.utilities.ConfigReader;
import elar.ui.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CookieAuthProvider {
    public static AuthContext loginAndGetCookies() {
        WebDriver driver = null;
        try {
            driver = Driver.getDriver();
            driver.get(ConfigReader.getProperty("elarbridgesURL"));

            driver.findElement(By.id("login-username"))
                    .sendKeys(ConfigReader.getProperty("ebUsername"));
            driver.findElement(By.id("login-password"))
                    .sendKeys(ConfigReader.getProperty("ebPassword"));
            driver.findElement(By.xpath("//button[contains(text(),'Login')]")).click();

            new WebDriverWait(driver,15)
                    .until(ExpectedConditions.urlContains("/cases/list"));

            Cookie access = driver.manage().getCookieNamed("Access");
            Cookie refresh = driver.manage().getCookieNamed("Refresh");

            if (access == null || refresh == null) {
                throw new IllegalStateException("Auth cookies not found. Access=" + access + ", Refresh=" + refresh);
            }

            return new AuthContext(access.getValue(), refresh.getValue());
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
