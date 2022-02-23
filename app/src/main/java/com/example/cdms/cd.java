package com.example.cdms;

public class cd {
    private String name;
    private String id;
    private String user;
    private String date;
    private String fund;
    private String BranchCode;
    public cd(){

    }

    public cd(String name, String id, String user, String date, String fund, String branchCode) {
        this.name = name;
        this.id = id;
        this.user = user;
        this.date = date;
        this.fund = fund;
        BranchCode = branchCode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFund() {
        return fund;
    }

    public void setFund(String fund) {
        this.fund = fund;
    }

    public String getBranchCode() {
        return BranchCode;
    }

    public void setBranchCode(String branchCode) {
        BranchCode = branchCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser() { return user; }

    public void setUser(String user) {
        this.user = user;
    }
}
