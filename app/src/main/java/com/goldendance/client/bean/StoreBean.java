package com.goldendance.client.bean;

import java.io.Serializable;

/**
 * Created by hemingway on 2017/1/31.
 */

public class StoreBean implements Serializable {
    String value;
    String text;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
