package com.goldendance.client.bean;

import java.io.Serializable;

/**
 * 消息实体
 * Created by hemingway on 2017/1/11.
 */

public class MessageBean implements Serializable {
    public static final int CODE_TOKEN_OVERTIME = 999;
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
