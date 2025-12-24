package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ElarbridgesCasesPage;
import pages.ElarbridgesCreateDriverPage;
import pages.ElarbridgesDriversListPage;
import pages.ElarbridgesLoginPage;
import utilities.BrowserUtils;
import utilities.ConfigReader;
import utilities.TestBase;

public class CreateDriverTest extends TestBase {

    String fullName = "John Doe";
    String driversLicenseExp = "10/10/2029";
    String medicalLicenseExp = "12/31/2029";


    @Test(groups = {"regression", "smoke", "elarbridges"})
    public void validateDriverCreatedWithValidData() {
        ElarbridgesLoginPage elarbridgesLoginPage = new ElarbridgesLoginPage();
        ElarbridgesCasesPage elarbridgesCasesPage = new ElarbridgesCasesPage();
        ElarbridgesDriversListPage elarbridgesDriversListPage = new ElarbridgesDriversListPage();
        ElarbridgesCreateDriverPage elarbridgesCreateDriverPage = new ElarbridgesCreateDriverPage();

        driver.get(ConfigReader.getProperty("elarbridgesURL"));
        elarbridgesLoginPage.login(ConfigReader.getProperty("ebUsername"), ConfigReader.getProperty("ebPassword"));

        elarbridgesCasesPage.clickDriversModuleLink();

        elarbridgesDriversListPage.addDriverBtn.click();

        elarbridgesCreateDriverPage.fullNameInput.sendKeys(fullName);
        elarbridgesCreateDriverPage.staffCheckbox.click();
        BrowserUtils.typeAndTab(elarbridgesCreateDriverPage.driversLicenseExpInput, driversLicenseExp);
        BrowserUtils.typeAndTab(elarbridgesCreateDriverPage.medicalLicenseExpInput, medicalLicenseExp);
        elarbridgesCreateDriverPage.createNewButtonClick();

        elarbridgesCreateDriverPage.handleDriverCreatedPopup();

        elarbridgesCasesPage.clickDriversModuleLink();

        BrowserUtils.clickSafely(elarbridgesDriversListPage.staffTableBtn);
        elarbridgesDriversListPage.waitUntilDriverAppears(fullName);
        Assert.assertTrue(elarbridgesDriversListPage.isDriverDisplayed(fullName));
        BrowserUtils.clickSafely(elarbridgesDriversListPage.allTableBtn);
        elarbridgesDriversListPage.waitUntilDriverAppears(fullName);
        Assert.assertTrue(elarbridgesDriversListPage.isDriverDisplayed(fullName));
    }

    @Test(groups = {"regression", "smoke", "elarbridges", "staffcheckbox"})
    public void validateStaffCheckboxDefaultAndBehavior() {
        driver.get(ConfigReader.getProperty("elarbridgesURL"));
        ElarbridgesLoginPage elarbridgesLoginPage = new ElarbridgesLoginPage();
        elarbridgesLoginPage.login(ConfigReader.getProperty("ebUsername"), ConfigReader.getProperty("ebPassword"));
        ElarbridgesCasesPage elarbridgesCasesPage = new ElarbridgesCasesPage();
        elarbridgesCasesPage.clickDriversModuleLink();
        ElarbridgesDriversListPage elarbridgesDriversListPage = new ElarbridgesDriversListPage();
        elarbridgesDriversListPage.addDriverBtn.click();
        ElarbridgesCreateDriverPage elarbridgesCreateDriverPage = new ElarbridgesCreateDriverPage();
        Assert.assertFalse(elarbridgesCreateDriverPage.staffCheckbox.isSelected(), "Staff checkbox is checked by default");
        elarbridgesCreateDriverPage.staffCheckbox.click();
        Assert.assertTrue(elarbridgesCreateDriverPage.staffCheckbox.isSelected(), "Staff checkbox is unchecked after clicking");
        elarbridgesCreateDriverPage.staffCheckbox.click();
        Assert.assertFalse(elarbridgesCreateDriverPage.staffCheckbox.isSelected(), "Staff checkbox is checked after unchecking");

    }

    @Test(groups = {"regression", "smoke", "elarbridges", "backtolistbtn"})
    public void validateBackToListButtonBehavior() {
        driver.get(ConfigReader.getProperty("elarbridgesURL"));
        ElarbridgesLoginPage elarbridgesLoginPage = new ElarbridgesLoginPage();
        elarbridgesLoginPage.login(ConfigReader.getProperty("ebUsername"), ConfigReader.getProperty("ebPassword"));
        ElarbridgesCasesPage elarbridgesCasesPage = new ElarbridgesCasesPage();
        elarbridgesCasesPage.clickDriversModuleLink();
        ElarbridgesDriversListPage elarbridgesDriversListPage = new ElarbridgesDriversListPage();
        elarbridgesDriversListPage.addDriverBtn.click();
        ElarbridgesCreateDriverPage elarbridgesCreateDriverPage = new ElarbridgesCreateDriverPage();
        elarbridgesCreateDriverPage.backToListBtn.click();
        Assert.assertTrue(elarbridgesDriversListPage.addDriverBtn.isDisplayed());
    }
}
