package utilities;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class TestBase {

    protected WebDriver driver;

    @BeforeMethod
    public void setup() {
        driver = Driver.getDriver();
    }

    @AfterMethod
    public void teardown() {
        driver.quit();
    }
}
