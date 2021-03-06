package com.nutsinterface.Mike.myapplication.backend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * Created by Mike on 11/20/2015.
 */
@Entity
public class NutsUser {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    Long id;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Index
    String userName;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    String email;

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    String telephone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name;
    /*
     @Named("userName") String userName,
            @Named("password") String password,
            @Named("name") String name,
            @Named("email") String email,
            @Named("telephone") String telephone) {
     */

    /*public NutsUser(String userName_t, String password_t, String name_t, String email_t, String telephone_t) {
        userName = userName_t;
        password = password_t;
        name = name_t;
        email = email_t;
        telephone = telephone_t;
    }*/
}
