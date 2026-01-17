package elar.api.base;

import elar.api.auth.AuthContext;
import elar.api.auth.CookieAuthProvider;
import elar.api.specs.RequestSpecFactory;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

public class TestBaseApi {
    protected static AuthContext auth;
    protected RequestSpecification jsonSpec;

    @BeforeClass(alwaysRun = true)
    public void setupApi() {
        if (auth == null) {
            auth = CookieAuthProvider.loginAndGetCookies();
        }
        jsonSpec = RequestSpecFactory.jsonSpec(auth);
    }
}
