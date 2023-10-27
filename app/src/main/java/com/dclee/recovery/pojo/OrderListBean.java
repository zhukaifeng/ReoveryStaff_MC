package com.dclee.recovery.pojo;

public class OrderListBean {

    /**
     * product_name : 黄板纸
     * product_id : 14
     * product_weight : 30.4
     * product_price : 34.2
     */

    private String product_name;
    private int product_id;
    private int num;
    private double product_weight;
    private double product_price;
    private double verify_weight;
    private double verify_price;

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public double getProduct_weight() {
        return product_weight;
    }

    public void setProduct_weight(double product_weight) {
        this.product_weight = product_weight;
    }

    public double getProduct_price() {
        return product_price;
    }

    public void setProduct_price(double product_price) {
        this.product_price = product_price;
    }

    public double getVerify_weight() {
        return verify_weight;
    }

    public void setVerify_weight(double verify_weight) {
        this.verify_weight = verify_weight;
    }

    public double getVerify_price() {
        return verify_price;
    }

    public void setVerify_price(double verify_price) {
        this.verify_price = verify_price;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
