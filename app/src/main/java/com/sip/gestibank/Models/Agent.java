package com.sip.gestibank.Models;

import com.google.gson.annotations.SerializedName;

public class Agent {
    @SerializedName("lastName")
    private String lastName;
    @SerializedName("firstName")
    private String firstName;
    @SerializedName("email")
    private String email;
    @SerializedName("tel")
    private String tel;
    @SerializedName("password")
    private String password;
    @SerializedName("role")
    private String role;
    @SerializedName("status")
    private String status;
    @SerializedName("matricule")
    private String matricule;

    public Agent() {
    }

    public Agent(String lastName, String firstName, String email, String tel, String password, String matricule) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.tel = tel;
        this.password = password;
        this.role = "AGENT";
        this.status = "VALIDE";
        this.matricule = matricule;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    @Override
    public String toString() {
        return "Agent{" +
                "lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", email='" + email + '\'' +
                ", tel='" + tel + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", status='" + status + '\'' +
                ", matricule='" + matricule + '\'' +
                '}';
    }
}
