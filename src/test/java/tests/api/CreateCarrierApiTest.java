package tests.api;

import elar.api.base.TestBaseApi;
import elar.api.client.CarrierClient;
import elar.api.dataproviders.CarrierDataProviders;
import elar.api.pojos.CreateCarrierRequest;
import elar.api.utils.CarrierDataGenerator;
import elar.api.utils.CarrierRequestFactory;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

public class CreateCarrierApiTest extends TestBaseApi {

    @Test(dataProvider = "statusFieldData", dataProviderClass = CarrierDataProviders.class,
            groups = {"regression", "api", "carrier"})
    public void validateStatusFieldInCreateCarrierPositive(String status) {

        CreateCarrierRequest createCarrierRequest = CarrierRequestFactory.defaultCarrier();
        createCarrierRequest.setStatus(status);

        CarrierClient carrierClient = new CarrierClient();
        Response postResponse = carrierClient.createCarrier(jsonSpec, createCarrierRequest);

        postResponse.then().log().all();
        postResponse.then().statusCode(200)
                .body("status", Matchers.equalTo(status));
    }

    @Test(groups = {"regression", "api", "carrier"})
    public void validateCreateCarrierApiWithoutStatusField() {

        CreateCarrierRequest createCarrierRequest = CarrierRequestFactory.defaultCarrier();
        createCarrierRequest.setStatus(null);  // @JsonInclude(JsonInclude.Include.NON_NULL) will skip status field completely

        CarrierClient carrierClient = new CarrierClient();
        Response postResponse = carrierClient.createCarrier(jsonSpec, createCarrierRequest);

        postResponse.then().log().all();
        postResponse.then().statusCode(422)
                .body("detail[0].msg", Matchers.equalTo("Field required"));
    }

    @Test(dataProvider = "invalidStatusData", dataProviderClass = CarrierDataProviders.class,
            groups = {"regression", "api", "carrier"})
    public void validateCreateCarrierApiWithInvalidStatus(String status, String expectedErrorMessage) {

        CreateCarrierRequest createCarrierRequest = CarrierRequestFactory.defaultCarrier();
        createCarrierRequest.setStatus(status);

        CarrierClient carrierClient = new CarrierClient();
        Response postResponse = carrierClient.createCarrier(jsonSpec, createCarrierRequest);

        postResponse.then().log().all();
        postResponse.then().statusCode(422)
                .body("detail[0].msg", Matchers.equalTo(expectedErrorMessage));
    }

    @Test(groups = {"regression", "api", "carrier"})
    public void validateCreateCarrierApiWithValidMcNumber() {

        CreateCarrierRequest createCarrierRequest = CarrierRequestFactory.defaultCarrier();
        String expectedMcNum = createCarrierRequest.getMc_number();

        CarrierClient carrierClient = new CarrierClient();
        Response postResponse = carrierClient.createCarrier(jsonSpec, createCarrierRequest);

        postResponse.then().log().all();
        postResponse.then().statusCode(200)
                .body("mc_number", Matchers.equalTo(expectedMcNum));
    }

    @Test(dataProvider = "mcNumPositiveLengthData", dataProviderClass = CarrierDataProviders.class,
            groups = {"regression", "api", "carrier"})
    public void validateMcNumberLengthInCreateCarrierApiPositive(Integer mcLength) {

        CreateCarrierRequest createCarrierRequest = CarrierRequestFactory.defaultCarrier();
        String mcNum = CarrierDataGenerator.uniqueDigits(mcLength);
        createCarrierRequest.setMc_number(mcNum);

        CarrierClient carrierClient = new CarrierClient();
        Response postResponse = carrierClient.createCarrier(jsonSpec, createCarrierRequest);

        postResponse.then().log().all();
        postResponse.then().statusCode(200)
                .body("mc_number", Matchers.equalTo(mcNum));
    }

    @Test(dataProvider = "mcNumNegativeLengthData", dataProviderClass = CarrierDataProviders.class,
            groups = {"regression", "api", "carrier", "knownBug"})
    public void validateMcNumberLengthInCreateCarrierApiNegative(Integer mcLength, String expectedErrorMsg) {

        CreateCarrierRequest createCarrierRequest = CarrierRequestFactory.defaultCarrier();
        String mcNum = CarrierDataGenerator.uniqueDigits(mcLength);
        createCarrierRequest.setMc_number(mcNum);

        CarrierClient carrierClient = new CarrierClient();
        Response postResponse = carrierClient.createCarrier(jsonSpec, createCarrierRequest);

        postResponse.then().log().all();
        postResponse.then().statusCode(422)
                .body("detail[0].msg", Matchers.equalTo(expectedErrorMsg));
    }

    @Test(groups = {"regression", "api", "carrier"})
    public void validateCreateCarrierApiWithoutMcField() {

        CreateCarrierRequest createCarrierRequest = CarrierRequestFactory.defaultCarrier();
        createCarrierRequest.setMc_number(null);   // @JsonInclude(JsonInclude.Include.NON_NULL) will skip status field completely

        CarrierClient carrierClient = new CarrierClient();
        Response postResponse = carrierClient.createCarrier(jsonSpec, createCarrierRequest);

        postResponse.then().log().all();
        postResponse.then().statusCode(422)
                .body("detail[0].msg", Matchers.equalTo("Field required"));

    }

    @Test(dataProvider = "invalidMcData", dataProviderClass = CarrierDataProviders.class,
            groups = {"regression", "api", "carrier", "knownBug"})
    public void validateCreateCarrierApiWithInvalidMcValue(String mcNum, String expectedErrorMessage) {

        CreateCarrierRequest createCarrierRequest = CarrierRequestFactory.defaultCarrier();
        createCarrierRequest.setMc_number(mcNum);

        CarrierClient carrierClient = new CarrierClient();
        Response postResponse = carrierClient.createCarrier(jsonSpec, createCarrierRequest);

        postResponse.then().log().all();
        postResponse.then().statusCode(422)
                .body("detail[0].msg", Matchers.equalTo(expectedErrorMessage));
    }

    @Test(groups = {"regression", "api", "carrier"})
    public void validateCreateCarrierApiWithValidDotNumber() {

        CreateCarrierRequest createCarrierRequest = CarrierRequestFactory.defaultCarrier();
        String expectedDotNum = createCarrierRequest.getDot_number();

        CarrierClient carrierClient = new CarrierClient();
        Response postResponse = carrierClient.createCarrier(jsonSpec, createCarrierRequest);

        postResponse.then().log().all();
        postResponse.then().statusCode(200)
                .body("dot_number", Matchers.equalTo(expectedDotNum));
    }

    @Test(dataProvider = "dotNumPositiveLengthData", dataProviderClass = CarrierDataProviders.class,
            groups = {"regression", "api", "carrier"})
    public void validateDotNumberLengthInCreateCarrierApiPositive(Integer dotLength) {

        CreateCarrierRequest createCarrierRequest = CarrierRequestFactory.defaultCarrier();
        String dotNum = CarrierDataGenerator.uniqueDigits(dotLength);
        createCarrierRequest.setDot_number(dotNum);

        CarrierClient carrierClient = new CarrierClient();
        Response postResponse = carrierClient.createCarrier(jsonSpec, createCarrierRequest);

        postResponse.then().log().all();
        postResponse.then().statusCode(200)
                .body("dot_number", Matchers.equalTo(dotNum));
    }

    @Test(dataProvider = "dotNumNegativeLengthData", dataProviderClass = CarrierDataProviders.class,
            groups = {"regression", "api", "carrier", "knownBug"})
    public void validateDotNumberLengthInCreateCarrierApiNegative(Integer dotLength, String expectedErrorMsg) {

        CreateCarrierRequest createCarrierRequest = CarrierRequestFactory.defaultCarrier();
        String dotNum = CarrierDataGenerator.uniqueDigits(dotLength);
        createCarrierRequest.setDot_number(dotNum);

        CarrierClient carrierClient = new CarrierClient();
        Response postResponse = carrierClient.createCarrier(jsonSpec, createCarrierRequest);

        postResponse.then().log().all();
        postResponse.then().statusCode(422)
                .body("detail[0].msg", Matchers.equalTo(expectedErrorMsg));
    }

    @Test(groups = {"regression", "api", "carrier"})
    public void validateCreateCarrierApiWithoutDotField() {

        CreateCarrierRequest createCarrierRequest = CarrierRequestFactory.defaultCarrier();
        createCarrierRequest.setDot_number(null);   // @JsonInclude(JsonInclude.Include.NON_NULL) will skip status field completely

        CarrierClient carrierClient = new CarrierClient();
        Response postResponse = carrierClient.createCarrier(jsonSpec, createCarrierRequest);

        postResponse.then().log().all();
        postResponse.then().statusCode(422)
                .body("detail[0].msg", Matchers.equalTo("Field required"));
    }

    @Test(dataProvider = "invalidDotData", dataProviderClass = CarrierDataProviders.class,
            groups = {"regression", "api", "carrier", "knownBug"})
    public void validateCreateCarrierApiWithInvalidDotValue(String dotNum, String expectedErrorMessage) {

        CreateCarrierRequest createCarrierRequest = CarrierRequestFactory.defaultCarrier();
        createCarrierRequest.setDot_number(dotNum);

        CarrierClient carrierClient = new CarrierClient();
        Response postResponse = carrierClient.createCarrier(jsonSpec, createCarrierRequest);

        postResponse.then().log().all();
        postResponse.then().statusCode(422)
                .body("detail[0].msg", Matchers.equalTo(expectedErrorMessage));
    }

}