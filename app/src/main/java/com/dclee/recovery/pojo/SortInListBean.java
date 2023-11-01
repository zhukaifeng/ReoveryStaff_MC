package com.dclee.recovery.pojo;

import java.util.List;

public class SortInListBean {


    private int total;
    private List<RowsBean> rows;
    private int code;
    private String msg;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class RowsBean {
        private String receiveId;
        private String deptId;
        private String deptIdText;
        private String deptName;
        private String createTime;
        private String receiveNo;
        private String realTimeOutBound;
        private String realTimeOutBoundText;
        private String orderNo;
        private String customerName;
        private String categoryName;
        private String productName;
        private String categoryId;
        private String categoryIdParentText;
        private int categoryIdParentId;
        private String categoryIdText;
        private String productId;
        private String productIdParentText;
        private String productIdParentId;
        private String productIdText;
        private String stockAddressId;
        private String stockAddressIdText;
        private String stockAddressName;
        private String receiveWeight;
        private String intoStorehouseWeight;
        private String differenceWeight;
        private String orderId;
        private List<?> productList;
        private String receiveStatus;
        private String receiveStatusText;
        private String createBy;
        private String createByText;
        private Object removeSkinList;
        private Object orderReceiveInVoList;
        private Object sysUserList;
        private Object sysProductTypeChooseList;
        private Object sysProductTypeParentChooseList;
        private String receiveType;

        public String getReceiveId() {
            return receiveId;
        }

        public void setReceiveId(String receiveId) {
            this.receiveId = receiveId;
        }

        public String getDeptId() {
            return deptId;
        }

        public void setDeptId(String deptId) {
            this.deptId = deptId;
        }

        public String getDeptIdText() {
            return deptIdText;
        }

        public void setDeptIdText(String deptIdText) {
            this.deptIdText = deptIdText;
        }

        public String getDeptName() {
            return deptName;
        }

        public void setDeptName(String deptName) {
            this.deptName = deptName;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getReceiveNo() {
            return receiveNo;
        }

        public void setReceiveNo(String receiveNo) {
            this.receiveNo = receiveNo;
        }

        public String getRealTimeOutBound() {
            return realTimeOutBound;
        }

        public void setRealTimeOutBound(String realTimeOutBound) {
            this.realTimeOutBound = realTimeOutBound;
        }

        public String getRealTimeOutBoundText() {
            return realTimeOutBoundText;
        }

        public void setRealTimeOutBoundText(String realTimeOutBoundText) {
            this.realTimeOutBoundText = realTimeOutBoundText;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
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

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getCategoryIdParentText() {
            return categoryIdParentText;
        }

        public void setCategoryIdParentText(String categoryIdParentText) {
            this.categoryIdParentText = categoryIdParentText;
        }

        public int getCategoryIdParentId() {
            return categoryIdParentId;
        }

        public void setCategoryIdParentId(int categoryIdParentId) {
            this.categoryIdParentId = categoryIdParentId;
        }

        public String getCategoryIdText() {
            return categoryIdText;
        }

        public void setCategoryIdText(String categoryIdText) {
            this.categoryIdText = categoryIdText;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getProductIdParentText() {
            return productIdParentText;
        }

        public void setProductIdParentText(String productIdParentText) {
            this.productIdParentText = productIdParentText;
        }

        public String getProductIdParentId() {
            return productIdParentId;
        }

        public void setProductIdParentId(String productIdParentId) {
            this.productIdParentId = productIdParentId;
        }

        public String getProductIdText() {
            return productIdText;
        }

        public void setProductIdText(String productIdText) {
            this.productIdText = productIdText;
        }

        public String getStockAddressId() {
            return stockAddressId;
        }

        public void setStockAddressId(String stockAddressId) {
            this.stockAddressId = stockAddressId;
        }

        public String getStockAddressIdText() {
            return stockAddressIdText;
        }

        public void setStockAddressIdText(String stockAddressIdText) {
            this.stockAddressIdText = stockAddressIdText;
        }

        public String getStockAddressName() {
            return stockAddressName;
        }

        public void setStockAddressName(String stockAddressName) {
            this.stockAddressName = stockAddressName;
        }

        public String getReceiveWeight() {
            return receiveWeight;
        }

        public void setReceiveWeight(String receiveWeight) {
            this.receiveWeight = receiveWeight;
        }

        public String getIntoStorehouseWeight() {
            return intoStorehouseWeight;
        }

        public void setIntoStorehouseWeight(String intoStorehouseWeight) {
            this.intoStorehouseWeight = intoStorehouseWeight;
        }

        public String getDifferenceWeight() {
            return differenceWeight;
        }

        public void setDifferenceWeight(String differenceWeight) {
            this.differenceWeight = differenceWeight;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public List<?> getProductList() {
            return productList;
        }

        public void setProductList(List<?> productList) {
            this.productList = productList;
        }

        public String getReceiveStatus() {
            return receiveStatus;
        }

        public void setReceiveStatus(String receiveStatus) {
            this.receiveStatus = receiveStatus;
        }

        public String getReceiveStatusText() {
            return receiveStatusText;
        }

        public void setReceiveStatusText(String receiveStatusText) {
            this.receiveStatusText = receiveStatusText;
        }

        public String getCreateBy() {
            return createBy;
        }

        public void setCreateBy(String createBy) {
            this.createBy = createBy;
        }

        public String getCreateByText() {
            return createByText;
        }

        public void setCreateByText(String createByText) {
            this.createByText = createByText;
        }

        public Object getRemoveSkinList() {
            return removeSkinList;
        }

        public void setRemoveSkinList(Object removeSkinList) {
            this.removeSkinList = removeSkinList;
        }

        public Object getOrderReceiveInVoList() {
            return orderReceiveInVoList;
        }

        public void setOrderReceiveInVoList(Object orderReceiveInVoList) {
            this.orderReceiveInVoList = orderReceiveInVoList;
        }

        public Object getSysUserList() {
            return sysUserList;
        }

        public void setSysUserList(Object sysUserList) {
            this.sysUserList = sysUserList;
        }

        public Object getSysProductTypeChooseList() {
            return sysProductTypeChooseList;
        }

        public void setSysProductTypeChooseList(Object sysProductTypeChooseList) {
            this.sysProductTypeChooseList = sysProductTypeChooseList;
        }

        public Object getSysProductTypeParentChooseList() {
            return sysProductTypeParentChooseList;
        }

        public void setSysProductTypeParentChooseList(Object sysProductTypeParentChooseList) {
            this.sysProductTypeParentChooseList = sysProductTypeParentChooseList;
        }

        public String getReceiveType() {
            return receiveType;
        }

        public void setReceiveType(String receiveType) {
            this.receiveType = receiveType;
        }
    }
}
