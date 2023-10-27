package com.dclee.recovery.bean.net;

public class OrderCheckEntity {
    public String order_id;
    public String msg;

    public OrderCheckEntity(String order_id, String msg) {
        this.order_id = order_id;
        this.msg = msg;
    }
}
