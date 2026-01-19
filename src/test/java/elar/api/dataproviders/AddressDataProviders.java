package elar.api.dataproviders;

import org.testng.annotations.DataProvider;

public class AddressDataProviders {

    @DataProvider(name = "missingRequiredField")
    public Object[][] missingRequiredField() {
        return new Object[][] {
                {"address"},
                {"city"},
                {"state"},
                {"zip_code"},
                {"name"}
        };
    }

}
