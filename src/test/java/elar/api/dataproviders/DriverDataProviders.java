package elar.api.dataproviders;

import org.testng.annotations.DataProvider;

public class DriverDataProviders {

    @DataProvider(name = "isStaffValues")
    public Object[][] isStaffValues() {
        return new Object[][] {
                {false},
                {true}
        };
    }

    @DataProvider(name = "fullNameLengthPositive")
    public Object[][] fullNameLengthPositive() {
        return new Object[][] {
                {"H"},
                {"hfkalkjdnddasdfghjklcvbnmertyukljhgdsaqwedcvfrtgby"}
        };
    }

    @DataProvider(name = "fullNameLengthNegative")
    public Object[][] fullNameLengthNegative() {
        return new Object[][] {
                {"HfkalkjdnddasdfghjklcvbnmertyukljhgdsaqwedcvfrtgbyA", "String should have at most 50 characters"},
                {"", "String should have at least 1 character"}
        };
    }
}
