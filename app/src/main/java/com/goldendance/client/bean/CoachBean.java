package com.goldendance.client.bean;

import java.io.Serializable;

/**
 * Created by hemingway on 2017/2/3.
 */

public class CoachBean implements Serializable {
//         "coachid": "COA20170113165208488050421",
//                 "name": "教练1",
//                 "signature": "该用户很懒，没有留下签名",
//                 "gender": "1",
//                 "icon": "http://120.77.206.145:8080/JinWuTuanhttp://120.77.206.145:8080/JinWuTuanhttp://120.77.206.145:8080/JinWuTuan/COACH/COA20170113165208488050421_1.jpg",
//                 "state": "1"

    private String coachid;
    private String name;
    private String signature;
    private String gender;
    private String icon;
    private String state;

    public String getCoachid() {
        return coachid;
    }

    public void setCoachid(String coachid) {
        this.coachid = coachid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
