package com.example.cdms;

public class User {
    private String name;
    private String email;
    private String password;
    private String key;
    private boolean admin;

    public User(){

    }
    public User(String name, String email, String password,String key ,boolean admin) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.key=key;
        this.admin=admin;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
