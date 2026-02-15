package elar.api.client;

import elar.ui.utilities.ConfigReader;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class AddressClient {

    private final String ADDRESSESPATH = ConfigReader.getProperty("addressesPath");

    public Response createAddress(RequestSpecification spec, Object body) {
        return given()
                .spec(spec)
                .body(body)
                .log().all()
                .when().post(ADDRESSESPATH);
    }
}
