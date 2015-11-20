package com.cs4310.epsilon.nutsinterface;

/**
 * Created by dave on 11/20/15.
 */
public class UserFront {

    Long id;
    String userName;
    String password;
    String email;
    String telephone;
    String name;

    /**
     * Constructor for use by the frontend for convenience; should not be
     * used by the backend because ids are assigned null!
     * @param userName  Username
     * @param password  Password
     * @param email     Email contact info
     * @param telephone Telephone contact info
     * @param name      Name contact info
     */
    public UserFront(String userName, String password, String email, String telephone, String name) {
        this.id = null;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.telephone = telephone;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return  "UserFront with id=" + id + ":\n" +
                "Username=" + userName + ":\n" +
                "Password=" + password + ":\n" +
                "Name=" + name + ":\n" +
                "Email=" + email + ":\n" +
                "Telephone=" + telephone;
    }

}
