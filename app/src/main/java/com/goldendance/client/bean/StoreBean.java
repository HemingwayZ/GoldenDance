package com.goldendance.client.bean;

import java.io.Serializable;

/**
 * Created by hemingway on 2017/1/31.
 */

public class StoreBean implements Serializable {
    String storeid;
    String nickname;
    boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getStoreid() {
        return storeid;
    }

    public void setStoreid(String storeid) {
        this.storeid = storeid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
