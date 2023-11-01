package com.dclee.recovery.bean.db;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * 如果需要添加联合唯一索引则可以在@Table注解加上
 * onCreated = “CREATE UNIQUE INDEX unique_name ON table_name(column1, column2)”)
 * 为表创建column1,column2联合唯一索引
 */
//为表创建NAME,EMAIL联合唯一索引
@Table(name = "sortin",
        onCreated = "CREATE UNIQUE INDEX realative_unique ON sortin(sorter, sorterName,weight,picIdStr,productId,productName,deductWeight,receiveId)")
public class SortInBean implements Serializable {
    @Column(
            name = "ID",
            isId = true,
            autoGen = true
    )
    private int id;
    @Column(name = "sorter")//NAME字段非空
    private String sorter;

    @Column(name = "sorterName")//NAME字段非空
    private String sorterName;

    @Column(name = "weight")
    private String weight;

    @Column(name = "picIdStr")
    private String picIdStr;

    @Column(name = "productId")
    private String productId;

    @Column(name = "productName")
    private String productName;

    @Column(name = "deductWeight")
    private String deductWeight;

    @Column(name = "receiveId")
    private String receiveId;



    public SortInBean(){

    }

    public String getSorterName() {
        return sorterName;
    }

    public void setSorterName(String sorterName) {
        this.sorterName = sorterName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(String receiveId) {
        this.receiveId = receiveId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSorter() {
        return sorter;
    }

    public void setSorter(String sorter) {
        this.sorter = sorter;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getPicIdStr() {
        return picIdStr;
    }

    public void setPicIdStr(String picIdStr) {
        this.picIdStr = picIdStr;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getDeductWeight() {
        return deductWeight;
    }

    public void setDeductWeight(String deductWeight) {
        this.deductWeight = deductWeight;
    }
}
