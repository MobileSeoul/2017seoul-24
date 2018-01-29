package com.hbag.seoulhang.model.retrofit;

/**
 * Created by cwh62 on 2017-03-14.
 */

public class PlayerDTO {
    private String id;
    private int language;
    private String password;





    public String getId() { return id; }
    public int getLanguage() {return language;}
    public String getPassword(){return password;}

    public void setId(String id) {
        this.id = id;
    }
    public void setLanguage(int language) {this.language = language;}
    public void setPassword(String password) {this.password = password;}

    @Override
    public String toString(){
        return "GameDTO - id:" + this.id + "/pass:";
    }
}
