package com.goldendance.client.bean;

import java.io.Serializable;

/**
 * Created by hemingway on 2017/1/26.
 */

public class CardRecordBean implements Serializable {

    private String cardrecordid;
    private String userid;
    private String cardname;
    private String paytime;
    private String version;

    public String getCardrecordid() {
        return cardrecordid;
    }

    public void setCardrecordid(String cardrecordid) {
        this.cardrecordid = cardrecordid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getCardname() {
        return cardname;
    }

    public void setCardname(String cardname) {
        this.cardname = cardname;
    }

    public String getPaytime() {
        return paytime;
    }

    public void setPaytime(String paytime) {
        this.paytime = paytime;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
