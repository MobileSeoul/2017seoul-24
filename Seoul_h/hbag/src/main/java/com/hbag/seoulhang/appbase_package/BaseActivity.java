package com.hbag.seoulhang.appbase_package;

import android.support.v7.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by cwh62 on 2017-09-10.
 */

public class BaseActivity extends AppCompatActivity {


    public void progressON() {
        RealmInit.getInstance().progressON(this, null);
    }

    public void progressON(String message) {
        RealmInit.getInstance().progressON(this, message);
    }

    public void progressOFF() {
        RealmInit.getInstance().progressOFF();
    }

    public boolean checkEmail(String email){ //이메일 유효성 체크

        String regex = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        return m.matches();

    }
    public boolean passcheck(String password) {  //영어+숫자+특수문자 혼합해서 6~18자 사이 비밀번호를 체크 인젝션 가능성 특수문자 배제
        String regex = "^(?=.*[a-zA-Z])(?=.*[!@[$]%\\^[*][+]-=[?]])(?=.*[0-9]).{6,18}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(password);
        return m.matches();
    }

    public boolean special_ch(String password) {  // / # & < > 배제
        String regex = "^[^\\/#&<>]+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(password);
        return m.matches();
    }

    public boolean checkId(String id){ //첫글자는 영어, 나머지 아이디는 영어와 숫자로 구성된 5~12자 아이디
        String regex = "^[a-zA-Z]{1}[a-zA-Z0-9_]{4,11}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(id);
        return m.matches();

    }

    public boolean checkname(String id){ //영문과 한글만 가능 1~12자
        String regex = "^[가-힣a-zA-Z]{1,12}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(id);
        return m.matches();
    }

    public boolean checknickname(String id){ //영문과 한글, 숫자만 가능 1~9자
        String regex = "^[가-힣a-zA-Z0-9]{1,9}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(id);
        return m.matches();
    }

    public boolean checkcode(String id){ //영문과 숫자만 가능
        String regex = "^[a-zA-Z0-9]{2,10}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(id);
        return m.matches();
    }



}
