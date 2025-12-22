package tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.ElarbridgesCasesPage;
import pages.ElarbridgesCreateDriverPage;
import pages.ElarbridgesDriversListPage;
import pages.ElarbridgesLoginPage;
import utilities.BrowserUtils;
import utilities.ConfigReader;
import utilities.TestBase;

public class FullNameFieldValidationTest extends TestBase {

    String driversLicenseExp = "10/10/2029";
    String medicalLicenseExp = "12/31/2029";
    String requiredErrorMessage = "Required";

    ElarbridgesLoginPage elarbridgesLoginPage = new ElarbridgesLoginPage();
    ElarbridgesCasesPage elarbridgesCasesPage = new ElarbridgesCasesPage();
    ElarbridgesDriversListPage elarbridgesDriversListPage = new ElarbridgesDriversListPage();
    ElarbridgesCreateDriverPage elarbridgesCreateDriverPage = new ElarbridgesCreateDriverPage();


    @Test(groups = {"regression", "smoke", "elarbridges", "fullName"})
    public void validateFullNameFieldIsRequired() {
        driver.get(ConfigReader.getProperty("elarbridgesURL"));
        elarbridgesLoginPage.login(ConfigReader.getProperty("ebUsername"), ConfigReader.getProperty("ebPassword"));

        elarbridgesCasesPage.clickDriversModuleLink();

        elarbridgesDriversListPage.addDriverBtn.click();

        BrowserUtils.typeAndTab(elarbridgesCreateDriverPage.driversLicenseExpInput, driversLicenseExp);
        BrowserUtils.typeAndTab(elarbridgesCreateDriverPage.medicalLicenseExpInput, medicalLicenseExp);
        elarbridgesCreateDriverPage.createNewButtonClick();

        Assert.assertEquals(elarbridgesCreateDriverPage.getRequiredErrorMessage(), requiredErrorMessage);


    }

    @DataProvider(name = "createDriverData")
    public static Object[][] testData() {
        return new Object[][]{
                {"N", "10/10/2029", "12/31/2029"},
                {"Asdfghjklzxcvbnmqwertyuiopasdfghjkzxcvbnqscdfghjko", "10/10/2029", "12/31/2029"}
        };
    }

    @Test(groups = {"regression", "smoke", "elarbridges", "fullName"}, dataProvider = "createDriverData")
    public void validateFullNameAcceptsValidMinAndMaxLength(
            String fullName,
            String driversLicenseExp,
            String medicalLicenseExp
    ) {
        System.out.println(fullName.length());
        driver.get(ConfigReader.getProperty("elarbridgesURL"));
        elarbridgesLoginPage.login(ConfigReader.getProperty("ebUsername"), ConfigReader.getProperty("ebPassword"));

        elarbridgesCasesPage.clickDriversModuleLink();

        elarbridgesDriversListPage.addDriverBtn.click();

        elarbridgesCreateDriverPage.fullNameInput.sendKeys(fullName);
        BrowserUtils.typeAndTab(elarbridgesCreateDriverPage.driversLicenseExpInput, driversLicenseExp);
        BrowserUtils.typeAndTab(elarbridgesCreateDriverPage.medicalLicenseExpInput, medicalLicenseExp);
        elarbridgesCreateDriverPage.createNewButtonClick();

        elarbridgesCreateDriverPage.handleDriverCreatedPopup();

        elarbridgesCasesPage.clickDriversModuleLink();

        BrowserUtils.clickSafelyBy(elarbridgesDriversListPage.allTableBtnBy);
        elarbridgesDriversListPage.waitUntilDriverAppears(fullName);
        Assert.assertTrue(elarbridgesDriversListPage.isDriverDisplayed(fullName));
    }

}
