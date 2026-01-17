package elar.api.client;

import elar.ui.utilities.ConfigReader;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class DriverClient {
    private final String DRIVERSPATH = ConfigReader.getProperty("driversPath");

    public Response createDriver(RequestSpecification spec, Object body) {
        return given()
                .spec(spec)
                .body(body)
                .log().all()
                .when().post(DRIVERSPATH);
    }
}
