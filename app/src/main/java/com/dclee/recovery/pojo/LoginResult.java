package com.dclee.recovery.pojo;

public class LoginResult {

    /**
     * token_type : Bearer
     * expires_in : 1296000
     * access_token : eyJ0eXAiOiJKV1QiLCJhbGciOiJSU...
     */

    private String token_type;
    private int expires_in;
    private String access_token;

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
}
