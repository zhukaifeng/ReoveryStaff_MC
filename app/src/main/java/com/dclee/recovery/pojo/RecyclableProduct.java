package com.dclee.recovery.pojo;

import java.util.List;

public class RecyclableProduct {
    private int id;
    private String name;
    private List<Product> item;

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

    public List<Product> getItem() {
        return item;
    }

    public void setItem(List<Product> item) {
        this.item = item;
    }
}
