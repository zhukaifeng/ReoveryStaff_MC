package com.dclee.recovery.pojo;

import java.util.List;

public class SortDefaultBean {

    private Integer code;
    private String msg;
    private DataDTO data;

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

    public DataDTO getData() {
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public static class DataDTO {
        private String receiveId;
        private String deptId;
        private String deptIdText;
        private Object deptName;
        private String createTime;
        private String receiveNo;
        private String realTimeOutBound;
        private String realTimeOutBoundText;
        private Object orderNo;
        private String customerName;
        private Object categoryName;
        private Object productName;
        private String categoryId;
        private String categoryIdParentText;
        private Integer categoryIdParentId;
        private String categoryIdText;
        private String productId;
        private String productIdParentText;
        private String productIdParentId;
        private String productIdText;
        private String stockAddressId;
        private String stockAddressIdText;
        private Object stockAddressName;
        private String receiveWeight;
        private Object intoStorehouseWeight;
        private Object differenceWeight;
        private String orderId;
        private Object productList;
        private String receiveStatus;
        private String receiveStatusText;
        private String createBy;
        private String createByText;
        private List<RemoveSkinListDTO> removeSkinList;
        private Object orderReceiveInVoList;
        private List<SysUserListDTO> sysUserList;
        private Object sysProductTypeChooseList;
        private List<SysProductTypeParentChooseListDTO> sysProductTypeParentChooseList;
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

        public Object getDeptName() {
            return deptName;
        }

        public void setDeptName(Object deptName) {
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

        public Object getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(Object orderNo) {
            this.orderNo = orderNo;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public Object getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(Object categoryName) {
            this.categoryName = categoryName;
        }

        public Object getProductName() {
            return productName;
        }

        public void setProductName(Object productName) {
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

        public Integer getCategoryIdParentId() {
            return categoryIdParentId;
        }

        public void setCategoryIdParentId(Integer categoryIdParentId) {
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

        public Object getStockAddressName() {
            return stockAddressName;
        }

        public void setStockAddressName(Object stockAddressName) {
            this.stockAddressName = stockAddressName;
        }

        public String getReceiveWeight() {
            return receiveWeight;
        }

        public void setReceiveWeight(String receiveWeight) {
            this.receiveWeight = receiveWeight;
        }

        public Object getIntoStorehouseWeight() {
            return intoStorehouseWeight;
        }

        public void setIntoStorehouseWeight(Object intoStorehouseWeight) {
            this.intoStorehouseWeight = intoStorehouseWeight;
        }

        public Object getDifferenceWeight() {
            return differenceWeight;
        }

        public void setDifferenceWeight(Object differenceWeight) {
            this.differenceWeight = differenceWeight;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public Object getProductList() {
            return productList;
        }

        public void setProductList(Object productList) {
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

        public List<RemoveSkinListDTO> getRemoveSkinList() {
            return removeSkinList;
        }

        public void setRemoveSkinList(List<RemoveSkinListDTO> removeSkinList) {
            this.removeSkinList = removeSkinList;
        }

        public Object getOrderReceiveInVoList() {
            return orderReceiveInVoList;
        }

        public void setOrderReceiveInVoList(Object orderReceiveInVoList) {
            this.orderReceiveInVoList = orderReceiveInVoList;
        }

        public List<SysUserListDTO> getSysUserList() {
            return sysUserList;
        }

        public void setSysUserList(List<SysUserListDTO> sysUserList) {
            this.sysUserList = sysUserList;
        }

        public Object getSysProductTypeChooseList() {
            return sysProductTypeChooseList;
        }

        public void setSysProductTypeChooseList(Object sysProductTypeChooseList) {
            this.sysProductTypeChooseList = sysProductTypeChooseList;
        }

        public List<SysProductTypeParentChooseListDTO> getSysProductTypeParentChooseList() {
            return sysProductTypeParentChooseList;
        }

        public void setSysProductTypeParentChooseList(List<SysProductTypeParentChooseListDTO> sysProductTypeParentChooseList) {
            this.sysProductTypeParentChooseList = sysProductTypeParentChooseList;
        }

        public String getReceiveType() {
            return receiveType;
        }

        public void setReceiveType(String receiveType) {
            this.receiveType = receiveType;
        }

        public static class RemoveSkinListDTO {
            private String id;
            private String status;
            private String weight;
            private String type;
            private Object remark;
            private String updateBy;
            private Object updateByText;
            private String updateTime;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getWeight() {
                return weight;
            }

            public void setWeight(String weight) {
                this.weight = weight;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public Object getRemark() {
                return remark;
            }

            public void setRemark(Object remark) {
                this.remark = remark;
            }

            public String getUpdateBy() {
                return updateBy;
            }

            public void setUpdateBy(String updateBy) {
                this.updateBy = updateBy;
            }

            public Object getUpdateByText() {
                return updateByText;
            }

            public void setUpdateByText(Object updateByText) {
                this.updateByText = updateByText;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }
        }

        public static class SysUserListDTO {
            private String userId;
            private String userIdText;
            private String openid;
            private String deptId;
            private String deptIdText;
            private String userBusinessType;
            private Object userBusinessTypeList;

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getUserIdText() {
                return userIdText;
            }

            public void setUserIdText(String userIdText) {
                this.userIdText = userIdText;
            }

            public String getOpenid() {
                return openid;
            }

            public void setOpenid(String openid) {
                this.openid = openid;
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

            public String getUserBusinessType() {
                return userBusinessType;
            }

            public void setUserBusinessType(String userBusinessType) {
                this.userBusinessType = userBusinessType;
            }

            public Object getUserBusinessTypeList() {
                return userBusinessTypeList;
            }

            public void setUserBusinessTypeList(Object userBusinessTypeList) {
                this.userBusinessTypeList = userBusinessTypeList;
            }
        }

        public static class SysProductTypeParentChooseListDTO {
            private String productId;
            private String productCode;
            private String productTypeName;
            private Integer parentId;
            private Integer level;
            private String status;
            private Object remark;
            private Object picIdStrList;
            private String productOrder;
            private Object commission;
            private Object parentName;
            private Object stockAddressId;
            private Object stockAddressIdText;
            private List<ChildrenDTO> children;
            private Object productPicUrlList;
            private Object picList;
            private boolean isSelected;

            public boolean isSelected() {
                return isSelected;
            }

            public void setSelected(boolean selected) {
                isSelected = selected;
            }

            public String getProductId() {
                return productId;
            }

            public void setProductId(String productId) {
                this.productId = productId;
            }

            public String getProductCode() {
                return productCode;
            }

            public void setProductCode(String productCode) {
                this.productCode = productCode;
            }

            public String getProductTypeName() {
                return productTypeName;
            }

            public void setProductTypeName(String productTypeName) {
                this.productTypeName = productTypeName;
            }

            public Integer getParentId() {
                return parentId;
            }

            public void setParentId(Integer parentId) {
                this.parentId = parentId;
            }

            public Integer getLevel() {
                return level;
            }

            public void setLevel(Integer level) {
                this.level = level;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public Object getRemark() {
                return remark;
            }

            public void setRemark(Object remark) {
                this.remark = remark;
            }

            public Object getPicIdStrList() {
                return picIdStrList;
            }

            public void setPicIdStrList(Object picIdStrList) {
                this.picIdStrList = picIdStrList;
            }

            public String getProductOrder() {
                return productOrder;
            }

            public void setProductOrder(String productOrder) {
                this.productOrder = productOrder;
            }

            public Object getCommission() {
                return commission;
            }

            public void setCommission(Object commission) {
                this.commission = commission;
            }

            public Object getParentName() {
                return parentName;
            }

            public void setParentName(Object parentName) {
                this.parentName = parentName;
            }

            public Object getStockAddressId() {
                return stockAddressId;
            }

            public void setStockAddressId(Object stockAddressId) {
                this.stockAddressId = stockAddressId;
            }

            public Object getStockAddressIdText() {
                return stockAddressIdText;
            }

            public void setStockAddressIdText(Object stockAddressIdText) {
                this.stockAddressIdText = stockAddressIdText;
            }

            public List<ChildrenDTO> getChildren() {
                return children;
            }

            public void setChildren(List<ChildrenDTO> children) {
                this.children = children;
            }

            public Object getProductPicUrlList() {
                return productPicUrlList;
            }

            public void setProductPicUrlList(Object productPicUrlList) {
                this.productPicUrlList = productPicUrlList;
            }

            public Object getPicList() {
                return picList;
            }

            public void setPicList(Object picList) {
                this.picList = picList;
            }

            public static class ChildrenDTO {
                private String productId;
                private String productCode;
                private String productTypeName;
                private String parentId;
                private Integer level;
                private String status;
                private Object remark;
                private Object picIdStrList;
                private String productOrder;
                private String commission;
                private Object parentName;
                private String stockAddressId;
                private String stockAddressIdText;
                private Object children;
                private Object productPicUrlList;
                private Object picList;
                private boolean isSelected;

                public boolean isSelected() {
                    return isSelected;
                }

                public void setSelected(boolean selected) {
                    isSelected = selected;
                }
                public String getProductId() {
                    return productId;
                }

                public void setProductId(String productId) {
                    this.productId = productId;
                }

                public String getProductCode() {
                    return productCode;
                }

                public void setProductCode(String productCode) {
                    this.productCode = productCode;
                }

                public String getProductTypeName() {
                    return productTypeName;
                }

                public void setProductTypeName(String productTypeName) {
                    this.productTypeName = productTypeName;
                }

                public String getParentId() {
                    return parentId;
                }

                public void setParentId(String parentId) {
                    this.parentId = parentId;
                }

                public Integer getLevel() {
                    return level;
                }

                public void setLevel(Integer level) {
                    this.level = level;
                }

                public String getStatus() {
                    return status;
                }

                public void setStatus(String status) {
                    this.status = status;
                }

                public Object getRemark() {
                    return remark;
                }

                public void setRemark(Object remark) {
                    this.remark = remark;
                }

                public Object getPicIdStrList() {
                    return picIdStrList;
                }

                public void setPicIdStrList(Object picIdStrList) {
                    this.picIdStrList = picIdStrList;
                }

                public String getProductOrder() {
                    return productOrder;
                }

                public void setProductOrder(String productOrder) {
                    this.productOrder = productOrder;
                }

                public String getCommission() {
                    return commission;
                }

                public void setCommission(String commission) {
                    this.commission = commission;
                }

                public Object getParentName() {
                    return parentName;
                }

                public void setParentName(Object parentName) {
                    this.parentName = parentName;
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

                public Object getChildren() {
                    return children;
                }

                public void setChildren(Object children) {
                    this.children = children;
                }

                public Object getProductPicUrlList() {
                    return productPicUrlList;
                }

                public void setProductPicUrlList(Object productPicUrlList) {
                    this.productPicUrlList = productPicUrlList;
                }

                public Object getPicList() {
                    return picList;
                }

                public void setPicList(Object picList) {
                    this.picList = picList;
                }
            }
        }
    }
}
