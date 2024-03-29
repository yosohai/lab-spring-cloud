package com.lab.ws.server;
import java.io.Serializable;
/**
 * 实体类
 *
 * @author Administrator
 */
public class User implements Serializable {
    private static final long serialVersionUID = -3718315085738793442L;
    private Integer id;
    private String name;
    private String email;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}

