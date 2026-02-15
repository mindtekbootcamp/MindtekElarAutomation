package tests.api;

import elar.api.base.TestBaseApi;
import elar.api.client.AddressClient;
import elar.api.dataproviders.AddressDataProviders;
import elar.api.pojos.CreateAddressRequest;
import elar.api.pojos.CreateAddressResponse;
import elar.api.specs.RequestSpecFactory;
import elar.api.utils.JsonUtils;
import elar.ui.utilities.ConfigReader;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class CreateAddressApiTest extends TestBaseApi {

    @Test(groups = {"regression", "smoke", "api", "address"})
    public void validateCreateAddressPositive() {

        CreateAddressRequest createAddressRequest = new CreateAddressRequest();
        createAddressRequest.setDefaultValues();

        AddressClient addressClient = new AddressClient();
        Response postResponse = addressClient.createAddress(jsonSpec, createAddressRequest);
        postResponse.then().log().all();
        CreateAddressResponse createAddressResponse = postResponse.body().as(CreateAddressResponse.class);
        postResponse.then().statusCode(200);
        Assert.assertTrue(createAddressResponse.getId().describeConstable().isPresent());
        Assert.assertNotNull(createAddressResponse.getId());
        Assert.assertEquals(createAddressResponse.getAddress(), createAddressRequest.getAddress());
        Assert.assertEquals(createAddressResponse.getCity(), createAddressRequest.getCity());
        Assert.assertEquals(createAddressResponse.getState(), createAddressRequest.getState());
        Assert.assertEquals(createAddressResponse.getZip_code(), createAddressRequest.getZip_code());
        Assert.assertEquals(createAddressResponse.getName(), createAddressRequest.getName());
    }

    /**
     * HAVE A QUESTION HERE!!!!!!!
     */
    @Test(groups = {"regression", "api", "address"})
    public void validateCreateAddressWithNoContentTypeHeader() {

        String createAddressRequest = JsonUtils.getJson("CreateAddressBaseRequest");

        RequestSpecification requestSpecification = RequestSpecFactory.jsonSpecNoContentType(auth);

        AddressClient addressClient = new AddressClient();
        Response postResponse = addressClient.createAddress(requestSpecification, createAddressRequest);

//        Response postResponse = given().baseUri(ConfigReader.getProperty("elarAPIBaseURL"))
//                .basePath("/services/elar-saas/api/v3")
//                .header("Accept", "application/json")
//                .cookie("Access", auth.getAccess())
//                .cookie("Refresh", auth.getRefresh())
//                .body(createAddressRequest)
//                .and().log().all()
//                .when().post("/addresses");

        postResponse.then().log().all();
        postResponse.then().statusCode(422);
    }

    @Test(groups = {"regression", "api", "address"})
    public void validateCreateAddressWithInvalidContentType() {

        String createAddressRequest = JsonUtils.getJson("CreateAddressBaseRequest");

        String headerName = "Content-Type";
        String headerValue = "text/plain";
        RequestSpecification requestSpecification = RequestSpecFactory.jsonSpecOverrideHeader(auth, headerName, headerValue);

        AddressClient addressClient = new AddressClient();
        Response postResponse = addressClient.createAddress(requestSpecification, createAddressRequest);

        postResponse.then().log().all();
        postResponse.then().statusCode(422)
                .body("detail[0].msg", Matchers.equalTo("Input should be a valid dictionary or object to extract fields from"));
    }

    @Ignore
    @Test(groups = {"regression", "api", "address", "knownBug"})
    public void validateCreateAddressWithNoAcceptHeader() {

        CreateAddressRequest createAddressRequest = new CreateAddressRequest();
        createAddressRequest.setDefaultValues();

        RequestSpecification requestSpecification = RequestSpecFactory.jsonSpecNoAccept(auth);

        AddressClient addressClient = new AddressClient();
        Response postResponse = addressClient.createAddress(requestSpecification, createAddressRequest);

        postResponse.then().log().all();
        postResponse.then().statusCode(422);
    }

    @Ignore
    @Test(groups = {"regression", "api", "address", "knownBug"})
    public void validateCreateAddressWithInvalidAccept() {

        CreateAddressRequest createAddressRequest = new CreateAddressRequest();
        createAddressRequest.setDefaultValues();

        String headerName = "Accept";
        String headerValue = "whatever";
        RequestSpecification requestSpecification = RequestSpecFactory.jsonSpecOverrideHeader(auth, headerName, headerValue);

        AddressClient addressClient = new AddressClient();
        Response postResponse = addressClient.createAddress(requestSpecification, createAddressRequest);

        postResponse.then().log().all();
        postResponse.then().statusCode(422);
    }

    @Test(groups = {"regression", "smoke", "api", "address"})
    public void validateCreateAddressWithoutAuthentification() {

        CreateAddressRequest createAddressRequest = new CreateAddressRequest();
        createAddressRequest.setDefaultValues();

        RequestSpecification requestSpecification = RequestSpecFactory.jsonSpecNoAuth();

        AddressClient addressClient = new AddressClient();
        Response postResponse = addressClient.createAddress(requestSpecification, createAddressRequest);

        postResponse.then().log().all();
        postResponse.then().statusCode(401)
                .body("message", Matchers.equalTo("Unauthorized"));
    }

    @Test(groups = {"regression", "smoke", "api", "address"})
    public void validateCreateAddressWithInvalidCookie() {

        CreateAddressRequest createAddressRequest = new CreateAddressRequest();
        createAddressRequest.setDefaultValues();

        String access = "invalid_cookie";
        String refresh = "invalid_refresh";
        RequestSpecification requestSpecification = RequestSpecFactory.jsonSpecOverrideCookies(access, refresh);

        AddressClient addressClient = new AddressClient();
        Response postResponse = addressClient.createAddress(requestSpecification, createAddressRequest);

        postResponse.then().log().all();
        postResponse.then().statusCode(401)
                .body("message", Matchers.equalTo("Unauthorized"));
    }

    @Test(groups = {"regression", "smoke", "api", "address"})
    public void validateCreateAddressWithExpiredCookie() {

        CreateAddressRequest createAddressRequest = new CreateAddressRequest();
        createAddressRequest.setDefaultValues();

        String expiredAccess = ConfigReader.getProperty("expiredAccessCookie");
        String expiredRefresh = ConfigReader.getProperty("expiredRefreshCookie");
        RequestSpecification requestSpecification = RequestSpecFactory.jsonSpecOverrideCookies(expiredAccess, expiredRefresh);

        AddressClient addressClient = new AddressClient();
        Response postResponse = addressClient.createAddress(requestSpecification, createAddressRequest);

        postResponse.then().log().all();
        postResponse.then().statusCode(401)
                .body("message", Matchers.equalTo("Unauthorized"));
    }

    @Ignore
    @Test(groups = {"regression", "api", "address", "knownBug"})
    public void validateCreateAddressWithEmptyRequestPayload() {

        String createAddressEmptyPayloadRequest = JsonUtils.getJson("CreateAddressEmptyBodyRequest");

        AddressClient addressClient = new AddressClient();
        Response postResponse = addressClient.createAddress(jsonSpec, createAddressEmptyPayloadRequest);

        postResponse.then().log().all();
        postResponse.then().statusCode(422);
    }

    @Ignore
    @Test(dataProvider = "missingRequiredField", dataProviderClass = AddressDataProviders.class,
            groups = {"regression", "api", "address", "knownBug"})
    public void validateCreateAddressWithMissingRequiredField(String missingField) {

        Map<String, Object> requestPayload = new HashMap<>();
        requestPayload.put("address", "123 abc");
        requestPayload.put("city", "Chicago");
        requestPayload.put("state", "IL");
        requestPayload.put("zip_code", "60656");
        requestPayload.put("name", "Abc");
        requestPayload.remove(missingField);

        AddressClient addressClient = new AddressClient();
        Response postResponse = addressClient.createAddress(jsonSpec, requestPayload);

        postResponse.then().log().all();
        postResponse.then().statusCode(422);
    }
}