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

    String fullName = "Test Test";
    String driversLicenseExp = "10/10/2029";
    String medicalLicenseExp = "12/31/2029";


    ElarbridgesLoginPage elarbridgesLoginPage = new ElarbridgesLoginPage();
    ElarbridgesCasesPage elarbridgesCasesPage = new ElarbridgesCasesPage();
    ElarbridgesDriversListPage elarbridgesDriversListPage = new ElarbridgesDriversListPage();
    ElarbridgesCreateDriverPage elarbridgesCreateDriverPage = new ElarbridgesCreateDriverPage();

    @Test(groups = {"regression", "smoke", "elarbridges"})
    public void validateDriverCreatedWithValidData() {
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
}
