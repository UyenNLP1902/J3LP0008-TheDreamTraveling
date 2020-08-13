/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uyennlp.account;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 *
 * @author HP
 */
public class AccountDTO implements Serializable {

    private String userId;
    @SerializedName(value = "name")
    private String name;
    private String password;
    private int role;
    @SerializedName(value = "id")
    private String facebookId;
    private String facebookLink;

    public AccountDTO() {
    }

    public AccountDTO(String userId, String name, int role) {
        this.userId = userId;
        this.name = name;
        this.role = role;
    }

    public AccountDTO(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public AccountDTO(String userId, String name, String password, int role, String facebookId, String facebookLink) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.role = role;
        this.facebookId = facebookId;
        this.facebookLink = facebookLink;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getFacebookLink() {
        return facebookLink;
    }

    public void setFacebookLink(String facebookLink) {
        this.facebookLink = facebookLink;
    }

}
