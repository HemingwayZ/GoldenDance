package com.goldendance.client.bean;

/**
 * Created by hemingway on 2017/1/12.
 */

public class DataResultBean<T> extends MessageBean {
    T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
