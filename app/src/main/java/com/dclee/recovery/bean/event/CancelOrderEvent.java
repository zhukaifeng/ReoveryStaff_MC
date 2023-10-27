package com.dclee.recovery.bean.event;

public class CancelOrderEvent {
    private int id;
    private String memo;

    public CancelOrderEvent(int id,String memo) {
        this.id = id;
        this.memo = memo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
