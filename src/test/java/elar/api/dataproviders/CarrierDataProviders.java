package elar.api.dataproviders;

import elar.api.utils.CarrierDataGenerator;
import org.testng.annotations.DataProvider;

public class CarrierDataProviders {

    @DataProvider(name = "statusFieldData")
    public Object[][] statusFieldData() {
        return new Object[][] {
                {"Active"},
                {"Non-active"},
                {"Preclosure"}
        };
    }

    @DataProvider(name = "invalidStatusData")
    public Object[][] invalidStatusData() {
        return new Object[][] {
                {"Closed", "Input should be 'Active', 'Non-active' or 'Preclosure'"},
                {"ACTIVE", "Input should be 'Active', 'Non-active' or 'Preclosure'"},
                {"active", "Input should be 'Active', 'Non-active' or 'Preclosure'"},
                {"", "Input should be 'Active', 'Non-active' or 'Preclosure'"}
        };
    }

    @DataProvider(name = "mcNumPositiveLengthData")
    public Object[][] mcNumPositiveLengthData() {
        return new Object[][] {
                {4},
                {10}
        };
    }

    @DataProvider(name = "dotNumPositiveLengthData")
    public Object[][] dotNumPositiveLengthData() {
        return new Object[][] {
                {4},
                {10}
        };
    }

    @DataProvider(name = "mcNumNegativeLengthData")
    public Object[][] mcNumNegativeLengthData() {
        return new Object[][] {
                {3, "String should have at least 4 characters"},
                {11, "String should have at most 10 characters"}
        };
    }

    @DataProvider(name = "dotNumNegativeLengthData")
    public Object[][] dotNumNegativeLengthData() {
        return new Object[][] {
                {3, "String should have at least 4 characters"},
                {11, "String should have at most 10 characters"}
        };
    }

    @DataProvider(name = "invalidMcData")
    public Object[][] invalidMcData() {
        return new Object[][] {
                {"", "String should have at least 1 character"},  // message doesn't align with requirements
                {CarrierDataGenerator.lettersAndDigits(8), "Input must contain numeric values"},
                {CarrierDataGenerator.withSpecialChars(8), "Input must contain numeric values"}
        };
    }

    @DataProvider(name = "invalidDotData")
    public Object[][] invalidDotData() {
        return new Object[][] {
                {"", "String should have at least 1 character"},  // message doesn't align with requirements
                {CarrierDataGenerator.lettersAndDigits(8), "Input must contain numeric values"},
                {CarrierDataGenerator.withSpecialChars(8), "Input must contain numeric values"}
        };
    }

}
