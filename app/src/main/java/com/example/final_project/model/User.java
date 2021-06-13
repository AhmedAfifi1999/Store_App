package com.example.final_project.model;

public class User {
    private int id;
    private String fullName;
    private String email;
    private String username;
    private String password;
    private String country;
    private String birthDate;
    private String phone;
    private boolean isAdministrator;
    private boolean Male;
    private String URLImage;
    private boolean verified;

    public User(int id, String fullName, String email, String username, String password, String country, String birthDate, String phone, boolean isAdministrator, boolean male, String URLImage, boolean verified) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.country = country;
        this.birthDate = birthDate;
        this.phone = phone;
        this.isAdministrator = isAdministrator;
        Male = male;
        this.URLImage = URLImage;
        this.verified = verified;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isAdministrator() {
        return isAdministrator;
    }

    public void setAdministrator(boolean administrator) {
        isAdministrator = administrator;
    }

    public boolean isMale() {
        return Male;
    }

    public void setMale(boolean male) {
        Male = male;
    }

    public String getURLImage() {
        return URLImage;
    }

    public void setURLImage(String URLImage) {
        this.URLImage = URLImage;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }
}
