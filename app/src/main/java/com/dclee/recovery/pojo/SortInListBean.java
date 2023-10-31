package com.dclee.recovery.pojo;

import java.util.List;

public class SortInListBean {

    private String createBy;
    private String createTime;
    private String updateBy;
    private String updateTime;
    private ParamsDTO params;
    private Integer pageNum;
    private Integer pageSize;
    private Integer receiveId;
    private Integer orderId;
    private Integer categoryId;
    private Integer categoryTypeId;
    private Integer productTypeId;
    private Integer netWeight;
    private Integer productId;
    private Integer receiveWeight;
    private String receiveStatus;
    private String remark;
    private List<String> dispatchTimeArr;
    private String dispatchTimeBeg;
    private String dispatchTimeEnd;
    private String orderNo;
    private String realTimeOutBound;
    private List<AddDataListDTO> addDataList;
    private Integer deptId;
    private Integer stockAddressId;
    private List<OrderReceiveInVoListDTO> orderReceiveInVoList;
    private String receiveType;

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public ParamsDTO getParams() {
        return params;
    }

    public void setParams(ParamsDTO params) {
        this.params = params;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(Integer receiveId) {
        this.receiveId = receiveId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getCategoryTypeId() {
        return categoryTypeId;
    }

    public void setCategoryTypeId(Integer categoryTypeId) {
        this.categoryTypeId = categoryTypeId;
    }

    public Integer getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(Integer productTypeId) {
        this.productTypeId = productTypeId;
    }

    public Integer getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(Integer netWeight) {
        this.netWeight = netWeight;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getReceiveWeight() {
        return receiveWeight;
    }

    public void setReceiveWeight(Integer receiveWeight) {
        this.receiveWeight = receiveWeight;
    }

    public String getReceiveStatus() {
        return receiveStatus;
    }

    public void setReceiveStatus(String receiveStatus) {
        this.receiveStatus = receiveStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<String> getDispatchTimeArr() {
        return dispatchTimeArr;
    }

    public void setDispatchTimeArr(List<String> dispatchTimeArr) {
        this.dispatchTimeArr = dispatchTimeArr;
    }

    public String getDispatchTimeBeg() {
        return dispatchTimeBeg;
    }

    public void setDispatchTimeBeg(String dispatchTimeBeg) {
        this.dispatchTimeBeg = dispatchTimeBeg;
    }

    public String getDispatchTimeEnd() {
        return dispatchTimeEnd;
    }

    public void setDispatchTimeEnd(String dispatchTimeEnd) {
        this.dispatchTimeEnd = dispatchTimeEnd;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getRealTimeOutBound() {
        return realTimeOutBound;
    }

    public void setRealTimeOutBound(String realTimeOutBound) {
        this.realTimeOutBound = realTimeOutBound;
    }

    public List<AddDataListDTO> getAddDataList() {
        return addDataList;
    }

    public void setAddDataList(List<AddDataListDTO> addDataList) {
        this.addDataList = addDataList;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Integer getStockAddressId() {
        return stockAddressId;
    }

    public void setStockAddressId(Integer stockAddressId) {
        this.stockAddressId = stockAddressId;
    }

    public List<OrderReceiveInVoListDTO> getOrderReceiveInVoList() {
        return orderReceiveInVoList;
    }

    public void setOrderReceiveInVoList(List<OrderReceiveInVoListDTO> orderReceiveInVoList) {
        this.orderReceiveInVoList = orderReceiveInVoList;
    }

    public String getReceiveType() {
        return receiveType;
    }

    public void setReceiveType(String receiveType) {
        this.receiveType = receiveType;
    }

    public static class ParamsDTO {
        private Property1DTO property1;
        private Property2DTO property2;

        public Property1DTO getProperty1() {
            return property1;
        }

        public void setProperty1(Property1DTO property1) {
            this.property1 = property1;
        }

        public Property2DTO getProperty2() {
            return property2;
        }

        public void setProperty2(Property2DTO property2) {
            this.property2 = property2;
        }

        public static class Property1DTO {
        }

        public static class Property2DTO {
        }
    }

    public static class AddDataListDTO {
    }

    public static class OrderReceiveInVoListDTO {
        private Integer receiveInId;
        private Integer receiveId;
        private String createTime;
        private String receiveInNo;
        private String receiveNo;
        private Integer deptId;
        private String deptName;
        private Integer categoryId;
        private Integer productId;
        private Integer sorter;
        private Integer netWeight;
        private Integer weight;
        private Integer deductWeight;
        private String receiveInStatus;
        private Integer pieceFactor;
        private Integer pieceAmount;
        private String remark;
        private Integer stockAddressId;
        private String picIdStr;
        private List<String> picUrlList;
        private List<String> productList;

        public Integer getReceiveInId() {
            return receiveInId;
        }

        public void setReceiveInId(Integer receiveInId) {
            this.receiveInId = receiveInId;
        }

        public Integer getReceiveId() {
            return receiveId;
        }

        public void setReceiveId(Integer receiveId) {
            this.receiveId = receiveId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getReceiveInNo() {
            return receiveInNo;
        }

        public void setReceiveInNo(String receiveInNo) {
            this.receiveInNo = receiveInNo;
        }

        public String getReceiveNo() {
            return receiveNo;
        }

        public void setReceiveNo(String receiveNo) {
            this.receiveNo = receiveNo;
        }

        public Integer getDeptId() {
            return deptId;
        }

        public void setDeptId(Integer deptId) {
            this.deptId = deptId;
        }

        public String getDeptName() {
            return deptName;
        }

        public void setDeptName(String deptName) {
            this.deptName = deptName;
        }

        public Integer getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(Integer categoryId) {
            this.categoryId = categoryId;
        }

        public Integer getProductId() {
            return productId;
        }

        public void setProductId(Integer productId) {
            this.productId = productId;
        }

        public Integer getSorter() {
            return sorter;
        }

        public void setSorter(Integer sorter) {
            this.sorter = sorter;
        }

        public Integer getNetWeight() {
            return netWeight;
        }

        public void setNetWeight(Integer netWeight) {
            this.netWeight = netWeight;
        }

        public Integer getWeight() {
            return weight;
        }

        public void setWeight(Integer weight) {
            this.weight = weight;
        }

        public Integer getDeductWeight() {
            return deductWeight;
        }

        public void setDeductWeight(Integer deductWeight) {
            this.deductWeight = deductWeight;
        }

        public String getReceiveInStatus() {
            return receiveInStatus;
        }

        public void setReceiveInStatus(String receiveInStatus) {
            this.receiveInStatus = receiveInStatus;
        }

        public Integer getPieceFactor() {
            return pieceFactor;
        }

        public void setPieceFactor(Integer pieceFactor) {
            this.pieceFactor = pieceFactor;
        }

        public Integer getPieceAmount() {
            return pieceAmount;
        }

        public void setPieceAmount(Integer pieceAmount) {
            this.pieceAmount = pieceAmount;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public Integer getStockAddressId() {
            return stockAddressId;
        }

        public void setStockAddressId(Integer stockAddressId) {
            this.stockAddressId = stockAddressId;
        }

        public String getPicIdStr() {
            return picIdStr;
        }

        public void setPicIdStr(String picIdStr) {
            this.picIdStr = picIdStr;
        }

        public List<String> getPicUrlList() {
            return picUrlList;
        }

        public void setPicUrlList(List<String> picUrlList) {
            this.picUrlList = picUrlList;
        }

        public List<String> getProductList() {
            return productList;
        }

        public void setProductList(List<String> productList) {
            this.productList = productList;
        }
    }
}
