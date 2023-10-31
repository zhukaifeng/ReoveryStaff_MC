package com.dclee.recovery.pojo;

import java.util.List;

public class OrderCreateBean {

    private Integer code;
    private String msg;
    private List<DataDTO> data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataDTO> getData() {
        return data;
    }

    public void setData(List<DataDTO> data) {
        this.data = data;
    }

    public static class DataDTO {
        private String orderId;
        private String orderNo;
        private Object stockAddressId;
        private Object dispatchId;
        private Object dispatchNo;
        private String deptId;
        private String deptName;
        private Object carNo;
        private Object driverName;
        private Object icCard;
        private Object followPeopleName;
        private Object customerId;
        private Object pickCustomerName;
        private String customerName;
        private Object dispatchTime;
        private String categoryName;
        private String productName;
        private String productId;
        private Object productUrl;
        private String categoryId;
        private Object orderStatus;
        private Object grossTime;
        private Object tareTime;
        private Object grossWeight;
        private Object tareWeight;
        private String netWeight;
        private Object changeCoefficient;
        private Object assessWeight;
        private Object orderSource;
        private Object remark;
        private Object qualityRemarks;
        private Object hasReceive;
        private Object stock;
        private Object orderType;
        private Object createTime;
        private Object orderTime;
        private String stockAddressName;
        private Object productList;
        private Object dispatchOrderVoList;
        private Object sysCustomerVoList;
        private Object sysStockAddressVoList;
        private Object icCardNo;
        private Object grossList;
        private Object tareList;
        private Object unloadList;
        private Object grossPicList;
        private Object picList;
        private Object grossOrTare;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public Object getStockAddressId() {
            return stockAddressId;
        }

        public void setStockAddressId(Object stockAddressId) {
            this.stockAddressId = stockAddressId;
        }

        public Object getDispatchId() {
            return dispatchId;
        }

        public void setDispatchId(Object dispatchId) {
            this.dispatchId = dispatchId;
        }

        public Object getDispatchNo() {
            return dispatchNo;
        }

        public void setDispatchNo(Object dispatchNo) {
            this.dispatchNo = dispatchNo;
        }

        public String getDeptId() {
            return deptId;
        }

        public void setDeptId(String deptId) {
            this.deptId = deptId;
        }

        public String getDeptName() {
            return deptName;
        }

        public void setDeptName(String deptName) {
            this.deptName = deptName;
        }

        public Object getCarNo() {
            return carNo;
        }

        public void setCarNo(Object carNo) {
            this.carNo = carNo;
        }

        public Object getDriverName() {
            return driverName;
        }

        public void setDriverName(Object driverName) {
            this.driverName = driverName;
        }

        public Object getIcCard() {
            return icCard;
        }

        public void setIcCard(Object icCard) {
            this.icCard = icCard;
        }

        public Object getFollowPeopleName() {
            return followPeopleName;
        }

        public void setFollowPeopleName(Object followPeopleName) {
            this.followPeopleName = followPeopleName;
        }

        public Object getCustomerId() {
            return customerId;
        }

        public void setCustomerId(Object customerId) {
            this.customerId = customerId;
        }

        public Object getPickCustomerName() {
            return pickCustomerName;
        }

        public void setPickCustomerName(Object pickCustomerName) {
            this.pickCustomerName = pickCustomerName;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public Object getDispatchTime() {
            return dispatchTime;
        }

        public void setDispatchTime(Object dispatchTime) {
            this.dispatchTime = dispatchTime;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public Object getProductUrl() {
            return productUrl;
        }

        public void setProductUrl(Object productUrl) {
            this.productUrl = productUrl;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public Object getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(Object orderStatus) {
            this.orderStatus = orderStatus;
        }

        public Object getGrossTime() {
            return grossTime;
        }

        public void setGrossTime(Object grossTime) {
            this.grossTime = grossTime;
        }

        public Object getTareTime() {
            return tareTime;
        }

        public void setTareTime(Object tareTime) {
            this.tareTime = tareTime;
        }

        public Object getGrossWeight() {
            return grossWeight;
        }

        public void setGrossWeight(Object grossWeight) {
            this.grossWeight = grossWeight;
        }

        public Object getTareWeight() {
            return tareWeight;
        }

        public void setTareWeight(Object tareWeight) {
            this.tareWeight = tareWeight;
        }

        public String getNetWeight() {
            return netWeight;
        }

        public void setNetWeight(String netWeight) {
            this.netWeight = netWeight;
        }

        public Object getChangeCoefficient() {
            return changeCoefficient;
        }

        public void setChangeCoefficient(Object changeCoefficient) {
            this.changeCoefficient = changeCoefficient;
        }

        public Object getAssessWeight() {
            return assessWeight;
        }

        public void setAssessWeight(Object assessWeight) {
            this.assessWeight = assessWeight;
        }

        public Object getOrderSource() {
            return orderSource;
        }

        public void setOrderSource(Object orderSource) {
            this.orderSource = orderSource;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public Object getQualityRemarks() {
            return qualityRemarks;
        }

        public void setQualityRemarks(Object qualityRemarks) {
            this.qualityRemarks = qualityRemarks;
        }

        public Object getHasReceive() {
            return hasReceive;
        }

        public void setHasReceive(Object hasReceive) {
            this.hasReceive = hasReceive;
        }

        public Object getStock() {
            return stock;
        }

        public void setStock(Object stock) {
            this.stock = stock;
        }

        public Object getOrderType() {
            return orderType;
        }

        public void setOrderType(Object orderType) {
            this.orderType = orderType;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }

        public Object getOrderTime() {
            return orderTime;
        }

        public void setOrderTime(Object orderTime) {
            this.orderTime = orderTime;
        }

        public String getStockAddressName() {
            return stockAddressName;
        }

        public void setStockAddressName(String stockAddressName) {
            this.stockAddressName = stockAddressName;
        }

        public Object getProductList() {
            return productList;
        }

        public void setProductList(Object productList) {
            this.productList = productList;
        }

        public Object getDispatchOrderVoList() {
            return dispatchOrderVoList;
        }

        public void setDispatchOrderVoList(Object dispatchOrderVoList) {
            this.dispatchOrderVoList = dispatchOrderVoList;
        }

        public Object getSysCustomerVoList() {
            return sysCustomerVoList;
        }

        public void setSysCustomerVoList(Object sysCustomerVoList) {
            this.sysCustomerVoList = sysCustomerVoList;
        }

        public Object getSysStockAddressVoList() {
            return sysStockAddressVoList;
        }

        public void setSysStockAddressVoList(Object sysStockAddressVoList) {
            this.sysStockAddressVoList = sysStockAddressVoList;
        }

        public Object getIcCardNo() {
            return icCardNo;
        }

        public void setIcCardNo(Object icCardNo) {
            this.icCardNo = icCardNo;
        }

        public Object getGrossList() {
            return grossList;
        }

        public void setGrossList(Object grossList) {
            this.grossList = grossList;
        }

        public Object getTareList() {
            return tareList;
        }

        public void setTareList(Object tareList) {
            this.tareList = tareList;
        }

        public Object getUnloadList() {
            return unloadList;
        }

        public void setUnloadList(Object unloadList) {
            this.unloadList = unloadList;
        }

        public Object getGrossPicList() {
            return grossPicList;
        }

        public void setGrossPicList(Object grossPicList) {
            this.grossPicList = grossPicList;
        }

        public Object getPicList() {
            return picList;
        }

        public void setPicList(Object picList) {
            this.picList = picList;
        }

        public Object getGrossOrTare() {
            return grossOrTare;
        }

        public void setGrossOrTare(Object grossOrTare) {
            this.grossOrTare = grossOrTare;
        }
    }
}
