package elar.api.auth;

import lombok.Getter;

@Getter
public class AuthContext {
    private final String access;
    private final String refresh;

    public AuthContext(String access, String refresh) {
        this.access = access;
        this.refresh = refresh;
    }
}
