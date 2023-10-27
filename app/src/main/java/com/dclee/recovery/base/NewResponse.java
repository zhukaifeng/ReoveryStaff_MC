package com.dclee.recovery.base;

public class NewResponse {

    /**
     * status : success
     * code : 200
     * data : http://mayihs.oss-cn-beijing.aliyuncs.com/images/Screenshot_2021-10-05-10-14-45-093_com.aramco.myCommunity.sandbox.jpg
     * message : success
     */

    public String status;
    public Integer code;
    public String data;
    public String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
