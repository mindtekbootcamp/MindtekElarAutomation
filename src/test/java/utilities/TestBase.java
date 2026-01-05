package utilities;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class TestBase {

    protected WebDriver driver;

    @BeforeMethod(groups = {"testbase"}, alwaysRun = true)
    public void setup() {
        driver = Driver.getDriver();
    }

    @AfterMethod(groups = {"testbase"}, alwaysRun = true)
    public void teardown() {
        driver.quit();
    }
}
