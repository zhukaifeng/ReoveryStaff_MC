package com.dclee.recovery.pojo;

import java.util.List;

public class OrderSummaryBean {
    private List<SummaryChildItem> items;

    public List<SummaryChildItem> getItems() {
        return items;
    }

    public void setItems(List<SummaryChildItem> items) {
        this.items = items;
    }
}
