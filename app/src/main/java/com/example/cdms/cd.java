package com.example.cdms;

public class cd {
    private String name;
    private String summary;
    private String id;
    private String user;

    public cd(){

    }
    public cd(String name, String summary, String id , String user) {
        this.name = name;
        this.summary = summary;
        this.id=id;
        this.user=user;
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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getUser() { return user; }

    public void setUser(String user) {
        this.user = user;
    }
}
