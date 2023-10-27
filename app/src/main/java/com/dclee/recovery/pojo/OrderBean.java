package com.dclee.recovery.pojo;

import com.dclee.recovery.bean.net.StaffEntity;

import java.util.List;

public class OrderBean {
    private int id;
    private String order_sn;
    private int user_id;
    private int address_id;
    private int weight;
    private String order_time;
    private String arrive_time;
    private String reject_check_status;
    private String created_at;
    private String receive_time;
    private String images;
    private RecvAddress address;
    private List<OrderProd> details;
    private int order_state;
    private StaffEntity staff;
    private StaffEntity users;

    public StaffEntity getStaff() {
        return staff;
    }

    public void setStaff(StaffEntity staff) {
        this.staff = staff;
    }

    public StaffEntity getUsers() {
        return users;
    }

    public void setUsers(StaffEntity users) {
        this.users = users;
    }

    public String getReject_check_status() {
        return reject_check_status;
    }

    public void setReject_check_status(String reject_check_status) {
        this.reject_check_status = reject_check_status;
    }

    public int getOrder_state() {
        return order_state;
    }

    public void setOrder_state(int order_state) {
        this.order_state = order_state;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public List<OrderProd> getDetails() {
        return details;
    }

    public void setDetails(List<OrderProd> details) {
        this.details = details;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public String getArrive_time() {
        return arrive_time;
    }

    public void setArrive_time(String arrive_time) {
        this.arrive_time = arrive_time;
    }

    public RecvAddress getAddress() {
        return address;
    }

    public void setAddress(RecvAddress address) {
        this.address = address;
    }

    public String getReceive_time() {
        return receive_time;
    }

    public void setReceive_time(String receive_time) {
        this.receive_time = receive_time;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

}
