package com.goldendance.client.bean;

import java.io.Serializable;

/**
 * Created by hemingway on 2017/1/12.
 */

public class UserBean implements Serializable {
    String tel;
    String icon;//头像
    String defoulttime;
    int points;
    String cardname;
    String token;
    String tokenovertime;
    String signature;

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDefoulttime() {
        return defoulttime;
    }

    public void setDefoulttime(String defoulttime) {
        this.defoulttime = defoulttime;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getCardname() {
        return cardname;
    }

    public void setCardname(String cardname) {
        this.cardname = cardname;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenovertime() {
        return tokenovertime;
    }

    public void setTokenovertime(String tokenovertime) {
        this.tokenovertime = tokenovertime;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
