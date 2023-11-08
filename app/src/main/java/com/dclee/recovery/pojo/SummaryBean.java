package com.dclee.recovery.pojo;

import java.util.List;

public class SummaryBean {


    private Integer total;
    private List<RowsDTO> rows;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<RowsDTO> getRows() {
        return rows;
    }

    public void setRows(List<RowsDTO> rows) {
        this.rows = rows;
    }

    public static class RowsDTO {
        private Object receiveInId;
        private Object receiveId;
        private Object createTime;
        private Object receiveInNo;
        private Object receiveNo;
        private Object deptId;
        private Object deptIdText;
        private Object deptName;
        private Object categoryId;
        private String productId;
        private String productIdParentText;
        private String productIdParentId;
        private String productIdText;
        private String sorter;
        private String sorterText;
        private String netWeight;
        private Object weight;
        private Object deductWeight;
        private Object receiveInStatus;
        private Object pieceFactor;
        private String pieceAmount;
        private Object remark;
        private Object stockAddressId;
        private Object picIdStr;
        private Object picUrlList;
        private Object productList;

        public Object getReceiveInId() {
            return receiveInId;
        }

        public void setReceiveInId(Object receiveInId) {
            this.receiveInId = receiveInId;
        }

        public Object getReceiveId() {
            return receiveId;
        }

        public void setReceiveId(Object receiveId) {
            this.receiveId = receiveId;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }

        public Object getReceiveInNo() {
            return receiveInNo;
        }

        public void setReceiveInNo(Object receiveInNo) {
            this.receiveInNo = receiveInNo;
        }

        public Object getReceiveNo() {
            return receiveNo;
        }

        public void setReceiveNo(Object receiveNo) {
            this.receiveNo = receiveNo;
        }

        public Object getDeptId() {
            return deptId;
        }

        public void setDeptId(Object deptId) {
            this.deptId = deptId;
        }

        public Object getDeptIdText() {
            return deptIdText;
        }

        public void setDeptIdText(Object deptIdText) {
            this.deptIdText = deptIdText;
        }

        public Object getDeptName() {
            return deptName;
        }

        public void setDeptName(Object deptName) {
            this.deptName = deptName;
        }

        public Object getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(Object categoryId) {
            this.categoryId = categoryId;
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

        public String getSorter() {
            return sorter;
        }

        public void setSorter(String sorter) {
            this.sorter = sorter;
        }

        public String getSorterText() {
            return sorterText;
        }

        public void setSorterText(String sorterText) {
            this.sorterText = sorterText;
        }

        public String getNetWeight() {
            return netWeight;
        }

        public void setNetWeight(String netWeight) {
            this.netWeight = netWeight;
        }

        public Object getWeight() {
            return weight;
        }

        public void setWeight(Object weight) {
            this.weight = weight;
        }

        public Object getDeductWeight() {
            return deductWeight;
        }

        public void setDeductWeight(Object deductWeight) {
            this.deductWeight = deductWeight;
        }

        public Object getReceiveInStatus() {
            return receiveInStatus;
        }

        public void setReceiveInStatus(Object receiveInStatus) {
            this.receiveInStatus = receiveInStatus;
        }

        public Object getPieceFactor() {
            return pieceFactor;
        }

        public void setPieceFactor(Object pieceFactor) {
            this.pieceFactor = pieceFactor;
        }

        public String getPieceAmount() {
            return pieceAmount;
        }

        public void setPieceAmount(String pieceAmount) {
            this.pieceAmount = pieceAmount;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public Object getStockAddressId() {
            return stockAddressId;
        }

        public void setStockAddressId(Object stockAddressId) {
            this.stockAddressId = stockAddressId;
        }

        public Object getPicIdStr() {
            return picIdStr;
        }

        public void setPicIdStr(Object picIdStr) {
            this.picIdStr = picIdStr;
        }

        public Object getPicUrlList() {
            return picUrlList;
        }

        public void setPicUrlList(Object picUrlList) {
            this.picUrlList = picUrlList;
        }

        public Object getProductList() {
            return productList;
        }

        public void setProductList(Object productList) {
            this.productList = productList;
        }
    }
}
