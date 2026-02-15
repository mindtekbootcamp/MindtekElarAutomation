package tests.api;

import elar.api.base.TestBaseApi;
import elar.api.client.DriverClient;
import elar.api.dataproviders.DriverDataProviders;
import elar.api.pojos.CreateDriverRequest;
import elar.api.utils.JsonUtils;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;


public class CreateDriverApiTest extends TestBaseApi {

    @Test(dataProvider = "isStaffValues", dataProviderClass = DriverDataProviders.class,
            groups = {"regression", "api", "is_staff"})
    public void validateDriverCreationWithIsStaff(boolean isStaff) {

        CreateDriverRequest createDriverRequest = new CreateDriverRequest();
        createDriverRequest.setDefaultValues();
        createDriverRequest.setFull_name("Patel Harsh");
        createDriverRequest.setIs_staff(isStaff);

        DriverClient driverClient = new DriverClient();
        Response postResponse = driverClient.createDriver(jsonSpec, createDriverRequest);
        postResponse.then().log().all();
        postResponse.then().statusCode(200)
                .body("is_staff", Matchers.equalTo(isStaff));
    }

    @Test(groups = {"regression", "api", "is_staff"})
    public void validateDriverCreationWithoutIsStuffField() {

        String createNoIsStuffDriverRequest = JsonUtils.getJson("CreateDriverRequestWithoutIsStuff");

        DriverClient driverClient = new DriverClient();
        Response postResponse = driverClient.createDriver(jsonSpec, createNoIsStuffDriverRequest);
        postResponse.then().log().all();
        postResponse.then().statusCode(200)
                .body("is_staff", Matchers.equalTo(false));
    }

    @Test(groups = {"regression", "api", "is_staff"})
    public void validateDriverCreationWithEmptyIsStaff() {

        String createDriverEmptyIsStuffRequest = JsonUtils.getJson("CreateDriverRequestWithEmptyIsStuff");

        DriverClient driverClient = new DriverClient();
        Response postResponse = driverClient.createDriver(jsonSpec, createDriverEmptyIsStuffRequest);
        postResponse.then().log().all();
        postResponse.then().statusCode(422)
                .body("detail[0].msg", Matchers.equalTo("Input should be a valid boolean, unable to interpret input"));
    }

    @Test(groups = {"regression", "smoke", "api", "full_name"})
    public void validateFullNameInCreateDriverPositive() {

        CreateDriverRequest createDriverRequest = new CreateDriverRequest();
        createDriverRequest.setDefaultValues();
        createDriverRequest.setFull_name("Patel Harsh");

        DriverClient driverClient = new DriverClient();
        Response postResponse = driverClient.createDriver(jsonSpec, createDriverRequest);
        postResponse.then().log().all();
        postResponse.then().statusCode(200)
                .body("full_name", Matchers.equalTo("Patel Harsh"));
    }

    @Test(dataProvider = "fullNameLengthPositive", dataProviderClass = DriverDataProviders.class,
            groups = {"regression", "api", "full_name"})
    public void validateFullNameLengthInCreateDriverPositive(String fullName) {

        CreateDriverRequest createDriverRequest = new CreateDriverRequest();
        createDriverRequest.setDefaultValues();
        createDriverRequest.setFull_name(fullName);

        DriverClient driverClient = new DriverClient();
        Response postResponse = driverClient.createDriver(jsonSpec, createDriverRequest);
        postResponse.then().log().all();
        postResponse.then().statusCode(200)
                .body("full_name", Matchers.equalTo(fullName));
    }

    @Test(dataProvider = "fullNameLengthNegative", dataProviderClass = DriverDataProviders.class,
            groups = {"regression", "api", "full_name"})
    public void validateFullNameLengthInCreateDriverNegative(String fullName, String expectedErrorMsg) {

        CreateDriverRequest createDriverRequest = new CreateDriverRequest();
        createDriverRequest.setDefaultValues();
        createDriverRequest.setFull_name(fullName);

        DriverClient driverClient = new DriverClient();
        Response postResponse = driverClient.createDriver(jsonSpec, createDriverRequest);
        postResponse.then().log().all();
        postResponse.then().statusCode(422)
                .body("detail[0].msg", Matchers.equalTo(expectedErrorMsg));
    }

    @Test(groups = {"regression", "api", "full_name"})
    public void validateFullNameInCreateDriverWithoutFullNameField() {

        String createDriverRequestWithMissingFullNameField = JsonUtils.getJson("CreateDriverRequestWithoutFullName");

        DriverClient driverClient = new DriverClient();
        Response postResponse = driverClient.createDriver(jsonSpec, createDriverRequestWithMissingFullNameField);
        postResponse.then().log().all();
        postResponse.then().statusCode(422)
                .body("detail[0].msg", Matchers.equalTo("Field required"));
    }

    @Ignore
    @Test(groups = {"regression", "api", "full_name", "knownBug"})
    public void validateFullNameInCreateDriverWithInvalidCharacters() {

        CreateDriverRequest createDriverRequest = new CreateDriverRequest();
        createDriverRequest.setDefaultValues();
        createDriverRequest.setFull_name("!@#$%&");

        DriverClient driverClient = new DriverClient();
        Response postResponse = driverClient.createDriver(jsonSpec, createDriverRequest);
        postResponse.then().log().all();
        postResponse.then().statusCode(422)
                .body("detail[0].msg", Matchers.equalTo("Field must contain only alphanumeric and specific punctuation characters"));
    }

    @Test(groups = {"regression", "api", "full_name"})
    public void validateFullNameInCreateDriverWithValidPunctuationCharacters() {

        CreateDriverRequest createDriverRequest = new CreateDriverRequest();
        createDriverRequest.setDefaultValues();
        createDriverRequest.setFull_name("J. P. Connor");

        DriverClient driverClient = new DriverClient();
        Response postResponse = driverClient.createDriver(jsonSpec, createDriverRequest);
        postResponse.then().log().all();
        postResponse.then().statusCode(200)
                .body("full_name", Matchers.equalTo("J. P. Connor"));
    }

    @Test(groups = {"regression", "api", "full_name"})
    public void validateFullNameInCreateDriverWithFullNameNotAString() {

        String createDriverRequestWithIntegerFullName = JsonUtils.getJson("CreateDriverRequestWithFullNameAsInteger");

        DriverClient driverClient = new DriverClient();
        Response postResponse = driverClient.createDriver(jsonSpec, createDriverRequestWithIntegerFullName);
        postResponse.then().log().all();
        postResponse.then().statusCode(422)
                .body("detail[0].msg", Matchers.equalTo("Input should be a valid string"));
    }

}