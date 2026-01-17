package elar.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import elar.ui.utilities.Driver;

public class ElarbridgesCasesPage {

    WebDriver driver;
    WebDriverWait wait;

    public ElarbridgesCasesPage() {
        driver = Driver.getDriver();
        wait = new WebDriverWait(driver, 10);
        PageFactory.initElements(driver, this);
    }

    public By driversModuleLink = By.cssSelector("a[href='/drivers/list']");

    public void clickDriversModuleLink() {
        wait.until(ExpectedConditions.elementToBeClickable(driversModuleLink)).click();
    }

}
