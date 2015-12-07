package com.vlasovartem.tvspace.entity;

import com.vlasovartem.tvspace.entity.enums.UserRole;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by artemvlasov on 07/12/15.
 */
@Document(collection = "users")
public class User {
    private String id;
    private String email;
    private String password;
    private String login;
    private String name;
    private boolean deleted;
    private List<UserSeries> userSeries;
    private UserRole role;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public List<UserSeries> getUserSeries() {
        return userSeries;
    }

    public void setUserSeries(List<UserSeries> userSeries) {
        this.userSeries = userSeries;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
