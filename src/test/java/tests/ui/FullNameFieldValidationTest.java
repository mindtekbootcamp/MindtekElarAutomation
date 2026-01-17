package tests.ui;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import elar.ui.pages.ElarbridgesCasesPage;
import elar.ui.pages.ElarbridgesCreateDriverPage;
import elar.ui.pages.ElarbridgesDriversListPage;
import elar.ui.pages.ElarbridgesLoginPage;
import elar.ui.utilities.BrowserUtils;
import elar.ui.utilities.ConfigReader;
import elar.ui.utilities.TestBase;

public class FullNameFieldValidationTest extends TestBase {

    String driversLicenseExp = "10/10/2029";
    String medicalLicenseExp = "12/31/2029";
    String requiredErrorMessage = "Required";

    @Test(groups = {"regression", "smoke", "elarbridges", "fullName"})
    public void validateFullNameFieldIsRequired() {
        ElarbridgesLoginPage elarbridgesLoginPage = new ElarbridgesLoginPage();
        ElarbridgesCasesPage elarbridgesCasesPage = new ElarbridgesCasesPage();
        ElarbridgesDriversListPage elarbridgesDriversListPage = new ElarbridgesDriversListPage();
        ElarbridgesCreateDriverPage elarbridgesCreateDriverPage = new ElarbridgesCreateDriverPage();

        driver.get(ConfigReader.getProperty("elarbridgesURL"));
        elarbridgesLoginPage.login(ConfigReader.getProperty("ebUsername"), ConfigReader.getProperty("ebPassword"));

        elarbridgesCasesPage.clickDriversModuleLink();

        elarbridgesDriversListPage.addDriverBtn.click();

        BrowserUtils.typeAndTab(elarbridgesCreateDriverPage.driversLicenseExpInput, driversLicenseExp);
        BrowserUtils.typeAndTab(elarbridgesCreateDriverPage.medicalLicenseExpInput, medicalLicenseExp);
        elarbridgesCreateDriverPage.createNewButtonClick();

        Assert.assertEquals(elarbridgesCreateDriverPage.getNameErrorMessage(), requiredErrorMessage);


    }

    @DataProvider(name = "createDriverData")
    public static Object[][] testData() {
        return new Object[][]{
                {"positive scenario", "N", "10/10/2029", "12/31/2029"},
                {"positive scenario", "Asdfghjklzxcvbnmqwertyuiopasdfghjkzxcvbnqscdfghjko", "10/10/2029", "12/31/2029"},
                {"negative scenario", "ABdfghjklzxcvbnmqwertyuiopasdfghjkzxcvbnqscdfghjkoA", "10/10/2029", "12/31/2029"}
        };
    }

    @Test(groups = {"regression", "smoke", "elarbridges", "fullName"}, dataProvider = "createDriverData")
    public void validateFullNameEnforcesMinAndMaxLength(
            String scenario,
            String fullName,
            String driversLicenseExp,
            String medicalLicenseExp
    ) {
        String expectedErrorMessage ="String must contain at most 50 character(s)";

        ElarbridgesLoginPage elarbridgesLoginPage = new ElarbridgesLoginPage();
        ElarbridgesCasesPage elarbridgesCasesPage = new ElarbridgesCasesPage();
        ElarbridgesDriversListPage elarbridgesDriversListPage = new ElarbridgesDriversListPage();
        ElarbridgesCreateDriverPage elarbridgesCreateDriverPage = new ElarbridgesCreateDriverPage();

        driver.get(ConfigReader.getProperty("elarbridgesURL"));
        elarbridgesLoginPage.login(ConfigReader.getProperty("ebUsername"), ConfigReader.getProperty("ebPassword"));

        elarbridgesCasesPage.clickDriversModuleLink();

        elarbridgesDriversListPage.addDriverBtn.click();

        elarbridgesCreateDriverPage.fullNameInput.sendKeys(fullName);
        BrowserUtils.typeAndTab(elarbridgesCreateDriverPage.driversLicenseExpInput, driversLicenseExp);
        BrowserUtils.typeAndTab(elarbridgesCreateDriverPage.medicalLicenseExpInput, medicalLicenseExp);
        elarbridgesCreateDriverPage.createNewButtonClick();

        if (scenario.equals("positive scenario")) {
            elarbridgesCreateDriverPage.handleDriverCreatedPopup();
            elarbridgesCasesPage.clickDriversModuleLink();
            BrowserUtils.clickSafelyBy(elarbridgesDriversListPage.allTableBtnBy);
            elarbridgesDriversListPage.waitUntilDriverAppears(fullName);
            Assert.assertTrue(elarbridgesDriversListPage.isDriverDisplayed(fullName));
        } else if (scenario.equals("negative scenario")) {
            Assert.assertEquals(elarbridgesCreateDriverPage.getNameErrorMessage(), expectedErrorMessage);
        }
    }


    @DataProvider(name = "validCharName")
    public static Object[][] validCharName() {
        return new Object[][]{
                {"Anna-Maria", "10/10/2029", "12/31/2029"},
                {"J .C. Connor", "10/10/2029", "12/31/2029"},
                {"John O'Neil", "10/10/2029", "12/31/2029"}
        };
    }
    @Test(groups = {"regression", "smoke", "elarbridges", "fullName"}, dataProvider = "validCharName")
    public void validateFullNameAcceptsValidCharacters(String fullName, String driversLicenseExp, String medicalLicenseExp) {
        ElarbridgesLoginPage elarbridgesLoginPage = new ElarbridgesLoginPage();
        ElarbridgesCasesPage elarbridgesCasesPage = new ElarbridgesCasesPage();
        ElarbridgesDriversListPage elarbridgesDriversListPage = new ElarbridgesDriversListPage();
        ElarbridgesCreateDriverPage elarbridgesCreateDriverPage = new ElarbridgesCreateDriverPage();

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

    @DataProvider(name = "invalidCharName")
    public static Object[][] invalidCharName() {
        return new Object[][]{
                {"John@Doe", "10/10/2029", "12/31/2029"},
                {"Anna<Smith>", "10/10/2029", "12/31/2029"},
                {"Connor$McGregor", "10/10/2029", "12/31/2029"}
        };
    }
    @Test(groups = {"regression", "smoke", "elarbridges", "fullName"}, dataProvider = "invalidCharName")
    public void validateFullNameRejectsInvalidCharacters(String fullName, String driversLicenseExp, String medicalLicenseExp) {
        String expectedErrorMessage = "Input must contain only alphanumeric and specific punctuation characters";
        ElarbridgesLoginPage elarbridgesLoginPage = new ElarbridgesLoginPage();
        ElarbridgesCasesPage elarbridgesCasesPage = new ElarbridgesCasesPage();
        ElarbridgesDriversListPage elarbridgesDriversListPage = new ElarbridgesDriversListPage();
        ElarbridgesCreateDriverPage elarbridgesCreateDriverPage = new ElarbridgesCreateDriverPage();

        driver.get(ConfigReader.getProperty("elarbridgesURL"));
        elarbridgesLoginPage.login(ConfigReader.getProperty("ebUsername"), ConfigReader.getProperty("ebPassword"));

        elarbridgesCasesPage.clickDriversModuleLink();

        elarbridgesDriversListPage.addDriverBtn.click();

        elarbridgesCreateDriverPage.fullNameInput.sendKeys(fullName);
        BrowserUtils.typeAndTab(elarbridgesCreateDriverPage.driversLicenseExpInput, driversLicenseExp);
        BrowserUtils.typeAndTab(elarbridgesCreateDriverPage.medicalLicenseExpInput, medicalLicenseExp);
        elarbridgesCreateDriverPage.createNewButtonClick();
        Assert.assertEquals(elarbridgesCreateDriverPage.getNameErrorMessage(), expectedErrorMessage);
    }

}
