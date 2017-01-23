package com.goldendance.client.bean;

import java.io.Serializable;

/**
 * 图片上传实体
 * Created by Hemingway1014@gmail.com on 2017/1/23.
 */

public class UploadBean implements Serializable {
    String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
