package com.BTA.SmartPrep.domain.entity;
import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name="Users")
public class User {
    //Default
    public User() {
    }
    //N-Arg
    public User(UUID id, String userName, String email, String pass_hash) {
        this.userId = id;
        this.userName = userName;
        this.email = email;
        this.pass_hash = pass_hash;
    }

    @Id //This Annotation Says this is an ID
    @GeneratedValue(strategy = GenerationType.UUID) //Creates ID for us, using UUID
    @Column(name = "userID",updatable = false,nullable = false) //col name from DB, not Updatable, cannot be null
    private UUID userId;

    @Column(name = "username",nullable = false)
    private String userName;

    @Column(name = "email",nullable = false)
    private String email;

    @Column(name = "pass_Hash",nullable = false)
    private String pass_hash;
    public UUID getId() {
        return userId;
    }

    public void setId(UUID id) {
        this.userId = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass_hash() {
        return pass_hash;
    }

    public void setPass_hash(String pass_hash) {
        this.pass_hash = pass_hash;
    }

    @Override // Checks ID To make sure Equal
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User users = (User) o;
        return Objects.equals(userId, users.userId);
    }

    @Override //
    public int hashCode() {
        return Objects.hashCode(userId);
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + userId +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", pass_hash='" + pass_hash + '\'' +
                '}';
    }
}
