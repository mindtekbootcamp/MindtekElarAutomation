package elar.ui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import elar.ui.utilities.Driver;

public class ElarbridgesLoginPage {

    WebDriver driver;

    public ElarbridgesLoginPage(){
        driver = Driver.getDriver();
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "login-username")
    public WebElement usernameInput;

    @FindBy(id = "login-password")
    public WebElement passwordInput;

    @FindBy(xpath = "//button[contains(text(),'Login')]")
    public WebElement loginButton;

    public void login(String username, String password) {
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);
        loginButton.click();
    }
}
