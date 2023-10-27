package com.dclee.recovery.bean.event;

import com.calypso.bluelib.bean.SearchResult;

import java.util.List;

public class BlueTeenEvent {
    private int code;
    private String msg;
    private List<SearchResult> results;

    public BlueTeenEvent(int code, String msg, List<SearchResult> results) {
        this.code = code;
        this.msg = msg;
        this.results = results;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<SearchResult> getResults() {
        return results;
    }

    public void setResults(List<SearchResult> results) {
        this.results = results;
    }
}
