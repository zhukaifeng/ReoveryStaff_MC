package com.dclee.recovery.base;

public class Response<T> {

    /**
     * status : success
     * code : 200
     * data : {"token_type":"Bearer","expires_in":1296000,"access_token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJSU..."}
     */

    private String status;
    private int code;
    private T data;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
