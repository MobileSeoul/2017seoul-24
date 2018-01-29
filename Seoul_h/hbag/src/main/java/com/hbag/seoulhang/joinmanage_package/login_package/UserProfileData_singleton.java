package com.hbag.seoulhang.joinmanage_package.login_package;

import android.app.Application;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cwh62 on 2017-09-30.
 */

public class UserProfileData_singleton extends Application{
    private static final UserProfileData_singleton ourInstance = new UserProfileData_singleton();
    String id;
    String name;
    String email;
    String nickname;
    String loginType;

    public static UserProfileData_singleton getInstance() {
        return ourInstance;
    }

    private UserProfileData_singleton() {

    }


    public String getId(){return this.id;}
    public void setId(String id){this.id=id;}

    public String getEmail(){return this.email;}
    public void setEmail(String email){this.email=email;}

    public String getName(){return this.name;}
    public void setName(String name){this.name=name;}

    public String getNickname() {return  this.nickname;}
    public void setNickname(String nickname) {this.nickname=nickname;}

    public String getLoginType() {return  this.loginType;}
    public void setLoginType(String loginType) {this.loginType=loginType;}
}
