package elar.api.specs;

import elar.api.auth.AuthContext;
import elar.ui.utilities.ConfigReader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RequestSpecFactory {
    public static RequestSpecification jsonSpec(AuthContext auth) {
        return new RequestSpecBuilder()
                .setBaseUri(ConfigReader.getProperty("elarAPIBaseURL"))
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .addCookie("Access", auth.getAccess())
                .addCookie("Refresh", auth.getRefresh())
                .build();
    }
}
