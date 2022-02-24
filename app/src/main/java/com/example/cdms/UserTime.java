package com.example.cdms;

public class UserTime {
    private String name;
    private String email;
    private String time;
    private String id;
    private String remark;

    public UserTime(String name, String email, String time, String id, String remark) {
        this.name = name;
        this.email = email;
        this.time = time;
        this.id = id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
