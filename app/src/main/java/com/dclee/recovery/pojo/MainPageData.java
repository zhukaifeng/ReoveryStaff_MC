package com.dclee.recovery.pojo;

import java.util.List;

public class MainPageData {

    /**
     * user : {"id":5,"username":"回收员一号","avatar":"...","department":"移动回收一部门"}
     * notify : [{"id":1,"content":"测试公告"}]
     */

    private User user;
    private List<News> notify;
    private String adminType;
    private String assignEnable;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<News> getNotify() {
        return notify;
    }

    public void setNotify(List<News> notify) {
        this.notify = notify;
    }

    public String getAdminType() {
        return adminType;
    }

    public void setAdminType(String adminType) {
        this.adminType = adminType;
    }

    public String getAssignEnable() {
        return assignEnable;
    }

    public void setAssignEnable(String assignEnable) {
        this.assignEnable = assignEnable;
    }
}
