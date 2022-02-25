package com.example.cdms;

public class UserTime {
    private String name;
    private String email;
    private String time;
    private String remark;
    private String userid;

    public UserTime(String name, String email, String time, String userid, String remark) {
        this.name = name;
        this.email = email;
        this.time = time;
        this.userid = userid;
        this.remark=remark;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getuserId() {
        return userid;
    }

    public void setId(String id) {
        this.userid = userid;
    }
}
