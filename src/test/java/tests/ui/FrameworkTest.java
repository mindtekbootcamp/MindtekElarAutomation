package tests.ui;

import org.testng.Assert;
import org.testng.annotations.Test;
import elar.ui.utilities.ConfigReader;
import elar.ui.utilities.TestBase;

public class FrameworkTest extends TestBase {

    @Test(groups = {"regression"})
    public void frameworkTest(){
        driver.get(ConfigReader.getProperty("elarbridgesURL"));
        Assert.assertEquals(driver.getTitle(), "Elar app");
    }
}
