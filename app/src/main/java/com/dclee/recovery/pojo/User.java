package com.dclee.recovery.pojo;

public class User {

    /**
     * id : 5
     * username : 回收员一号
     * avatar : ...
     * department : 移动回收一部门
     */

    private int id;
    private String username;
    private String avatar;
    private String department;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
