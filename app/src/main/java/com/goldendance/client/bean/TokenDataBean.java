package com.goldendance.client.bean;

import java.io.Serializable;

/**
 * Created by hemingway on 2017/1/14.
 */

public class TokenDataBean implements Serializable {
    TokenBean tokeninfo;

    public TokenBean getTokeninfo() {
        return tokeninfo;
    }

    public void setTokeninfo(TokenBean tokeninfo) {
        this.tokeninfo = tokeninfo;
    }
}
