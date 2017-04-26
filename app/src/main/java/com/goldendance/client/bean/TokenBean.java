package com.goldendance.client.bean;

import java.io.Serializable;

/**
 * Created by hemingway on 2017/1/13.
 */

public class TokenBean implements Serializable {
    String tokenid;
    String life;//life为TOKEN有效期

    public String getTokenid() {
        return tokenid;
    }

    public void setTokenid(String tokenid) {
        this.tokenid = tokenid;
    }

    public String getLife() {
        return life;
    }

    public void setLife(String life) {
        this.life = life;
    }
}
