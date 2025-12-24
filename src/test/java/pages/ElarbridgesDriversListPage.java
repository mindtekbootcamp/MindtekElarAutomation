package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utilities.Driver;

import java.util.List;

public class ElarbridgesDriversListPage {

    WebDriver driver;

    public ElarbridgesDriversListPage() {
        driver = Driver.getDriver();
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//button[contains(text(), 'Add driver')]")
    public WebElement addDriverBtn;

    @FindBy(xpath = "//button[@role='tab' and contains(., 'Staff')]")
    public WebElement staffTableBtn;

    @FindBy(xpath = "//button[@role='tab' and contains(., 'All')]")
    public WebElement allTableBtn;

    public By staffTableBtnBy = (By.xpath("//button[@role='tab' and contains(., 'Staff')]"));

    public By allTableBtnBy = (By.xpath("//button[@role='tab' and contains(., 'All')]"));

    private final By gridScroller = By.cssSelector(".MuiDataGrid-virtualScroller");

    private By driverCellByName(String driverName) {
        return By.xpath(
                "//div[contains(@class,'MuiDataGrid-cell') and @data-field='full_name']" +
                        "//div[@title=\"" + driverName + "\"]"
        );
    }

    public boolean isDriverDisplayed(String driverName) {
        return scrollUntilFound(driverCellByName(driverName));
    }

    public void waitUntilDriverAppears(String driverName) {
        boolean found = scrollUntilFound(driverCellByName(driverName));
        Assert.assertTrue(found, "Driver not found in grid: " + driverName);
    }

    private boolean scrollUntilFound(By targetBy) {
        driver = Driver.getDriver();
        JavascriptExecutor jse = (JavascriptExecutor) driver;

        WebElement scroller = driver.findElement(gridScroller);

        for (int i = 0; i < 25; i++) {

            List<WebElement> matches = driver.findElements(targetBy);
            if (!matches.isEmpty()) {
                WebElement el = matches.getFirst();

                jse.executeScript("arguments[0].scrollIntoView({block:'center'});", el);

                new WebDriverWait(driver, 5)
                        .until(d -> !d.findElements(targetBy).isEmpty());
                return true;
            }
            jse.executeScript("arguments[0].scrollTop += 300;", scroller);
        }
        return false;
    }

}
