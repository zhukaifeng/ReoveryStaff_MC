package com.dclee.recovery.pojo;

public class Product {

    /**
     * id : 5
     * name : 椅子
     * image : products/201910/0c13ed848ec3d92833a0657975aef2fa.jpeg
     * min : 0.00
     * max : 0.00
     */

    private int id;
    private String name;
    private String image;
    private String min;
    private String max;
    private boolean isSelected;
    private int type;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
