package com.dclee.recovery.bean.event;

import java.util.ArrayList;
import java.util.List;

public class UploadPic {
    private List<String> pics = new ArrayList<>();

    public UploadPic(List<String> pics) {
        this.pics = pics;
    }

    public List<String> getPics() {
        return pics;
    }

    public void setPics(List<String> pics) {
        this.pics = pics;
    }
}
