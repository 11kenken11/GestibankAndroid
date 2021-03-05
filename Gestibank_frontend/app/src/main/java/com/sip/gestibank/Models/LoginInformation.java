package com.sip.gestibank.Models;

public class LoginInformation {
    String email;
    String password;

    public LoginInformation(String email, String password) {
        this.email = email;
        this.password = password;
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

    @Override
    public String toString() {
        return "LoginInformation{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}