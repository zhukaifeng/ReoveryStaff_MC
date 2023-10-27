package com.dclee.recovery.pojo;

import java.util.List;

public class PurchaseBean {

    /**
     * id : 1
     * order_sn : 2019091814041285
     * user_id : 2
     * order_status : 0
     * order_money : 222.50
     * weight : 0
     * created_at : 2019-09-17 14:04:59
     * details : [{"order_id":1,"product_id":1,"product_name":"纸板","product_price":"2.00","weight":0},{"order_id":1,"product_id":2,"product_name":"塑料","product_price":"1.50","weight":0}]
     * username : 等等a
     */

    private int id;
    private long order_sn;
    private int user_id;
    private int order_status;
    private String order_money;
    private int weight;
    private String created_at;
    private String username;
    private List<OrderProd> details;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(long order_sn) {
        this.order_sn = order_sn;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getOrder_status() {
        return order_status;
    }

    public void setOrder_status(int order_status) {
        this.order_status = order_status;
    }

    public String getOrder_money() {
        return order_money;
    }

    public void setOrder_money(String order_money) {
        this.order_money = order_money;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<OrderProd> getDetails() {
        return details;
    }

    public void setDetails(List<OrderProd> details) {
        this.details = details;
    }
}
