package com.dclee.recovery.pojo;

import java.util.List;

public class SorterBean {


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
        private String createBy;
        private String createTime;
        private Object updateBy;
        private Object updateTime;
        private Integer pageNum;
        private Integer pageSize;
        private String userId;
        private String deptId;
        private String deptIdText;
        private String userName;
        private String nickName;
        private Object userType;
        private String email;
        private String phonenumber;
        private String sex;
        private String avatar;
        private String status;
        private String delFlag;
        private String loginIp;
        private Object loginDate;
        private Object remark;
        private String icCardNo;
        private Object userCode;
        private Object userBusinessType;
        private DeptDTO dept;
        private List<?> roles;
        private Object roleIds;
        private Object postIds;
        private Object permissions;
        private Object roleId;
        private Boolean deptAdmin;
        private String post;
        private Object hasFollow;
        private Object menuList;
        private Object openid;
        private String userOrder;
        private Object wxConfig;
        private String clockCount;
        private Object workNo;
        private Object joinDate;
        private Boolean admin;

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

        public Object getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(Object updateBy) {
            this.updateBy = updateBy;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
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

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
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

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public Object getUserType() {
            return userType;
        }

        public void setUserType(Object userType) {
            this.userType = userType;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhonenumber() {
            return phonenumber;
        }

        public void setPhonenumber(String phonenumber) {
            this.phonenumber = phonenumber;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(String delFlag) {
            this.delFlag = delFlag;
        }

        public String getLoginIp() {
            return loginIp;
        }

        public void setLoginIp(String loginIp) {
            this.loginIp = loginIp;
        }

        public Object getLoginDate() {
            return loginDate;
        }

        public void setLoginDate(Object loginDate) {
            this.loginDate = loginDate;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public String getIcCardNo() {
            return icCardNo;
        }

        public void setIcCardNo(String icCardNo) {
            this.icCardNo = icCardNo;
        }

        public Object getUserCode() {
            return userCode;
        }

        public void setUserCode(Object userCode) {
            this.userCode = userCode;
        }

        public Object getUserBusinessType() {
            return userBusinessType;
        }

        public void setUserBusinessType(Object userBusinessType) {
            this.userBusinessType = userBusinessType;
        }

        public DeptDTO getDept() {
            return dept;
        }

        public void setDept(DeptDTO dept) {
            this.dept = dept;
        }

        public List<?> getRoles() {
            return roles;
        }

        public void setRoles(List<?> roles) {
            this.roles = roles;
        }

        public Object getRoleIds() {
            return roleIds;
        }

        public void setRoleIds(Object roleIds) {
            this.roleIds = roleIds;
        }

        public Object getPostIds() {
            return postIds;
        }

        public void setPostIds(Object postIds) {
            this.postIds = postIds;
        }

        public Object getPermissions() {
            return permissions;
        }

        public void setPermissions(Object permissions) {
            this.permissions = permissions;
        }

        public Object getRoleId() {
            return roleId;
        }

        public void setRoleId(Object roleId) {
            this.roleId = roleId;
        }

        public Boolean getDeptAdmin() {
            return deptAdmin;
        }

        public void setDeptAdmin(Boolean deptAdmin) {
            this.deptAdmin = deptAdmin;
        }

        public String getPost() {
            return post;
        }

        public void setPost(String post) {
            this.post = post;
        }

        public Object getHasFollow() {
            return hasFollow;
        }

        public void setHasFollow(Object hasFollow) {
            this.hasFollow = hasFollow;
        }

        public Object getMenuList() {
            return menuList;
        }

        public void setMenuList(Object menuList) {
            this.menuList = menuList;
        }

        public Object getOpenid() {
            return openid;
        }

        public void setOpenid(Object openid) {
            this.openid = openid;
        }

        public String getUserOrder() {
            return userOrder;
        }

        public void setUserOrder(String userOrder) {
            this.userOrder = userOrder;
        }

        public Object getWxConfig() {
            return wxConfig;
        }

        public void setWxConfig(Object wxConfig) {
            this.wxConfig = wxConfig;
        }

        public String getClockCount() {
            return clockCount;
        }

        public void setClockCount(String clockCount) {
            this.clockCount = clockCount;
        }

        public Object getWorkNo() {
            return workNo;
        }

        public void setWorkNo(Object workNo) {
            this.workNo = workNo;
        }

        public Object getJoinDate() {
            return joinDate;
        }

        public void setJoinDate(Object joinDate) {
            this.joinDate = joinDate;
        }

        public Boolean getAdmin() {
            return admin;
        }

        public void setAdmin(Boolean admin) {
            this.admin = admin;
        }

        public static class DeptDTO {
            private Object createBy;
            private Object createTime;
            private Object updateBy;
            private Object updateTime;
            private Integer pageNum;
            private Integer pageSize;
            private Object parentName;
            private Object parentId;
            private List<?> children;
            private String deptId;
            private String deptName;
            private Object deptCode;
            private Object orderNum;
            private Object leader;
            private Object phone;
            private Object email;
            private Object status;
            private Object delFlag;
            private Object ancestors;
            private Object deptType;
            private Object remark;

            public Object getCreateBy() {
                return createBy;
            }

            public void setCreateBy(Object createBy) {
                this.createBy = createBy;
            }

            public Object getCreateTime() {
                return createTime;
            }

            public void setCreateTime(Object createTime) {
                this.createTime = createTime;
            }

            public Object getUpdateBy() {
                return updateBy;
            }

            public void setUpdateBy(Object updateBy) {
                this.updateBy = updateBy;
            }

            public Object getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(Object updateTime) {
                this.updateTime = updateTime;
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

            public Object getParentName() {
                return parentName;
            }

            public void setParentName(Object parentName) {
                this.parentName = parentName;
            }

            public Object getParentId() {
                return parentId;
            }

            public void setParentId(Object parentId) {
                this.parentId = parentId;
            }

            public List<?> getChildren() {
                return children;
            }

            public void setChildren(List<?> children) {
                this.children = children;
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

            public Object getDeptCode() {
                return deptCode;
            }

            public void setDeptCode(Object deptCode) {
                this.deptCode = deptCode;
            }

            public Object getOrderNum() {
                return orderNum;
            }

            public void setOrderNum(Object orderNum) {
                this.orderNum = orderNum;
            }

            public Object getLeader() {
                return leader;
            }

            public void setLeader(Object leader) {
                this.leader = leader;
            }

            public Object getPhone() {
                return phone;
            }

            public void setPhone(Object phone) {
                this.phone = phone;
            }

            public Object getEmail() {
                return email;
            }

            public void setEmail(Object email) {
                this.email = email;
            }

            public Object getStatus() {
                return status;
            }

            public void setStatus(Object status) {
                this.status = status;
            }

            public Object getDelFlag() {
                return delFlag;
            }

            public void setDelFlag(Object delFlag) {
                this.delFlag = delFlag;
            }

            public Object getAncestors() {
                return ancestors;
            }

            public void setAncestors(Object ancestors) {
                this.ancestors = ancestors;
            }

            public Object getDeptType() {
                return deptType;
            }

            public void setDeptType(Object deptType) {
                this.deptType = deptType;
            }

            public Object getRemark() {
                return remark;
            }

            public void setRemark(Object remark) {
                this.remark = remark;
            }
        }
    }
}
