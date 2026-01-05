package pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.Driver;

public class ElarbridgesCreateDriverPage {

    WebDriver driver;
    WebDriverWait wait;

    public ElarbridgesCreateDriverPage() {
        driver = Driver.getDriver();
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "input[name='full_name']")
    public WebElement fullNameInput;

    @FindBy(xpath = "//p[contains(@class,'MuiFormHelperText') and contains(., 'Required') " +
            "or contains(., 'String must contain at most 50 character(s)') " +
            "or contains(.,'Input must contain only alphanumeric and specific punctuation characters')]")
    public WebElement nameErrorMessage;

    @FindBy(css = "input[name='is_staff']")
    public WebElement staffCheckbox;

    @FindBy(css = "input[name='driving_license_exp']")
    public WebElement driversLicenseExpInput;

    @FindBy(css = "input[name='medical_certification_exp']")
    public WebElement medicalLicenseExpInput;

    @FindBy(xpath = "//button[contains(., 'Create new')]")
    public WebElement createNewBtn;

    @FindBy(xpath = "//button[contains(text(), 'Go to Edit')]")
    public WebElement gotoEditBtn;

    @FindBy(xpath = "//button[normalize-space()='Back to list']")
    public WebElement backToListBtn;


    public void createNewButtonClick(){
        wait = new WebDriverWait(Driver.getDriver(), 10);
        wait.until(ExpectedConditions.elementToBeClickable(createNewBtn));
        createNewBtn.sendKeys(Keys.ENTER);

    }

    public void handleDriverCreatedPopup() {
        wait = new WebDriverWait(Driver.getDriver(), 20);
        wait.until(ExpectedConditions.visibilityOf(gotoEditBtn));
        gotoEditBtn.click();
    }

    public String getNameErrorMessage() {
        wait = new WebDriverWait(Driver.getDriver(), 10);
        wait.until(ExpectedConditions.visibilityOf(nameErrorMessage));
        return nameErrorMessage.getText().trim();
    }




}
