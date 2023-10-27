package com.dclee.recovery.pojo;

import java.util.ArrayList;
import java.util.List;

public class OrderProd {

    /**
     * id : 27
     * product_name : 椅子
     * order_id : 15
     */

    private int id;
    private String product_name;
    private int order_id;
    private double max_price;
    private double min_price;
    /**
     * product_id : 1
     * product_price : 2.00
     * weight : 0
     */

    private int product_id;
    private String product_price;
    private String weight;
    private String total;
    private int type;
    private List<Double> weightList = new ArrayList<>();

    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Double> getWeightList() {
        return weightList;
    }

    public void setWeightList(List<Double> weightList) {
        this.weightList = weightList;
        double weightNum = 0;
        for(int i = 0 ; i < weightList.size() ; i++){
            weightNum =weightNum+weightList.get(i);
        }
        this.weight = weightNum+"";
    }

    public void addWeight(double weight) {
        if(weightList == null ){
            weightList = new ArrayList<>();
        }
        weightList.add(weight);
        double weightNum = 0;
        for(int i = 0 ; i < weightList.size() ; i++){
            weightNum =weightNum+weightList.get(i);
        }
        this.weight = weightNum+"";
    }

    public double getMax_price() {
        return max_price;
    }

    public void setMax_price(double max_price) {
        this.max_price = max_price;
    }

    public double getMin_price() {
        return min_price;
    }

    public void setMin_price(double min_price) {
        this.min_price = min_price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public OrderProd setSimpleDatas(String product_name) {
        this.product_name = product_name;
        return this;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "OrderProd{" +
                "id=" + id +
                ", product_name='" + product_name + '\'' +
                ", order_id=" + order_id +
                ", max_price=" + max_price +
                ", min_price=" + min_price +
                ", product_id=" + product_id +
                ", product_price='" + product_price + '\'' +
                ", weight='" + weight + '\'' +
                ", type=" + type +
                ", isSelected=" + isSelected +
                '}';
    }
}
