package com.vangasuthalam.model;

public class Customer {

    private int customerId;
    private String name;
    private String mobile;
    private String email;
    private String password;
    private String address;
    private String status;

    public Customer() {
    }

    public Customer(String name, String mobile, String email,
                    String password, String address) {

        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.password = password;
        this.address = address;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}