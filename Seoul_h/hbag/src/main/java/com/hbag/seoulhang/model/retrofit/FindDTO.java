package com.hbag.seoulhang.model.retrofit;

/**
 * Created by cwh62 on 2017-09-07.
 */

public class FindDTO {
    private String id;
    private String password;
    private String name;
    private String code;

    public String getId() { return id; }
    public String getPassword() { return password; }
    public String getName() { return name; }
    public String getCode() { return code;}

    public void setId(String id) {
        this.id = id;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setCode(String code) { this.code = code; }
}
