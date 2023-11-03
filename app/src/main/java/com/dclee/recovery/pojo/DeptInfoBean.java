package com.dclee.recovery.pojo;

import java.util.List;

public class DeptInfoBean {

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
        private String categoryTypeId;
        private String categoryName;
        private Object productTypeId;
        private Object productName;
        private Object stockWeight;
        private Object remark;
        private List<ChildrenDTO> children;
        private String deptId;
        private String deptIdText;
        private Object stockAddressId;
        private Object stockName;
        private Object picList;

        public String getCategoryTypeId() {
            return categoryTypeId;
        }

        public void setCategoryTypeId(String categoryTypeId) {
            this.categoryTypeId = categoryTypeId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public Object getProductTypeId() {
            return productTypeId;
        }

        public void setProductTypeId(Object productTypeId) {
            this.productTypeId = productTypeId;
        }

        public Object getProductName() {
            return productName;
        }

        public void setProductName(Object productName) {
            this.productName = productName;
        }

        public Object getStockWeight() {
            return stockWeight;
        }

        public void setStockWeight(Object stockWeight) {
            this.stockWeight = stockWeight;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public List<ChildrenDTO> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenDTO> children) {
            this.children = children;
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

        public Object getStockAddressId() {
            return stockAddressId;
        }

        public void setStockAddressId(Object stockAddressId) {
            this.stockAddressId = stockAddressId;
        }

        public Object getStockName() {
            return stockName;
        }

        public void setStockName(Object stockName) {
            this.stockName = stockName;
        }

        public Object getPicList() {
            return picList;
        }

        public void setPicList(Object picList) {
            this.picList = picList;
        }

        public static class ChildrenDTO {
            private Object categoryTypeId;
            private Object categoryName;
            private String productTypeId;
            private String productName;
            private String stockWeight;
            private Object remark;
            private Object children;
            private Object deptId;
            private Object deptIdText;
            private String stockAddressId;
            private String stockName;
            private List<?> picList;

            public Object getCategoryTypeId() {
                return categoryTypeId;
            }

            public void setCategoryTypeId(Object categoryTypeId) {
                this.categoryTypeId = categoryTypeId;
            }

            public Object getCategoryName() {
                return categoryName;
            }

            public void setCategoryName(Object categoryName) {
                this.categoryName = categoryName;
            }

            public String getProductTypeId() {
                return productTypeId;
            }

            public void setProductTypeId(String productTypeId) {
                this.productTypeId = productTypeId;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public String getStockWeight() {
                return stockWeight;
            }

            public void setStockWeight(String stockWeight) {
                this.stockWeight = stockWeight;
            }

            public Object getRemark() {
                return remark;
            }

            public void setRemark(Object remark) {
                this.remark = remark;
            }

            public Object getChildren() {
                return children;
            }

            public void setChildren(Object children) {
                this.children = children;
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

            public String getStockAddressId() {
                return stockAddressId;
            }

            public void setStockAddressId(String stockAddressId) {
                this.stockAddressId = stockAddressId;
            }

            public String getStockName() {
                return stockName;
            }

            public void setStockName(String stockName) {
                this.stockName = stockName;
            }

            public List<?> getPicList() {
                return picList;
            }

            public void setPicList(List<?> picList) {
                this.picList = picList;
            }
        }
    }
}
