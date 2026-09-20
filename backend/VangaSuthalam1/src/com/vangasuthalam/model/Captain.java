package com.vangasuthalam.model;

public class Captain {

    private int captainId;
    private String name;
    private String mobile;
    private String email;
    private String password;
    private int experienceYears;
    private String qualification;
    private String verificationStatus;
    private String status;

    public Captain() {
    }

    public Captain(String name, String mobile, String email,
                   String password, int experienceYears,
                   String qualification) {

        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.password = password;
        this.experienceYears = experienceYears;
        this.qualification = qualification;
    }

    public int getCaptainId() {
        return captainId;
    }

    public void setCaptainId(int captainId) {
        this.captainId = captainId;
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

    public int getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(int experienceYears) {
        this.experienceYears = experienceYears;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(String verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}