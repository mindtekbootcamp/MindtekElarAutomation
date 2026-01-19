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

    public static RequestSpecification jsonSpecNoContentType(AuthContext auth) {
        return new RequestSpecBuilder()
                .setBaseUri(ConfigReader.getProperty("elarAPIBaseURL"))
                .setAccept(ContentType.JSON)
                .addCookie("Access", auth.getAccess())
                .addCookie("Refresh", auth.getRefresh())
                .build();
    }

    public static RequestSpecification jsonSpecNoAccept(AuthContext auth) {
        return new RequestSpecBuilder()
                .setBaseUri(ConfigReader.getProperty("elarAPIBaseURL"))
                .setContentType(ContentType.JSON)
                .addCookie("Access", auth.getAccess())
                .addCookie("Refresh", auth.getRefresh())
                .build();
    }

    public static RequestSpecification jsonSpecOverrideHeader(AuthContext auth, String headerName, String headerValue) {
        return new RequestSpecBuilder()
                .setBaseUri(ConfigReader.getProperty("elarAPIBaseURL"))
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .addCookie("Access", auth.getAccess())
                .addCookie("Refresh", auth.getRefresh())
                .addHeader(headerName, headerValue)
                .build();
    }

    public static RequestSpecification jsonSpecNoAuth() {
        return new RequestSpecBuilder()
                .setBaseUri(ConfigReader.getProperty("elarAPIBaseURL"))
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .build();
    }

    public static RequestSpecification jsonSpecOverrideCookies(String access, String refresh) {
        return new RequestSpecBuilder()
                .setBaseUri(ConfigReader.getProperty("elarAPIBaseURL"))
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .addCookie("Access", access)
                .addCookie("Refresh", refresh)
                .build();
    }
}