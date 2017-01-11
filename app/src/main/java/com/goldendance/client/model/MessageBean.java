package com.goldendance.client.model;

import java.io.Serializable;

/**
 * 消息实体
 * Created by hemingway on 2017/1/11.
 */

public class MessageBean implements Serializable {
    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
