package com.hbag.seoulhang.joinmanage_package;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.hbag.seoulhang.home_package.Home_Main;
import com.hbag.seoulhang.R;

import java.util.Locale;

public class SettingActivity extends AppCompatActivity {

    RadioButton ko;
    RadioButton eng;
    Switch auto_Notice;
    Button commit;
    Button cancel;
    TextView lang_text;
    TextView lang_descrip;
    TextView notice_descrip;
    int lang=0;
    int toggled;
    private long mLastClickTime = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);


        ko = (RadioButton)findViewById(R.id.ko);
        eng = (RadioButton)findViewById(R.id.eng);
        commit = (Button)findViewById(R.id.commit);
        cancel = (Button)findViewById(R.id.cancel);
        lang_text = (TextView)findViewById(R.id.lang_text);
        auto_Notice = (Switch)findViewById(R.id.auto_notice_toggle);
        lang_descrip = (TextView)findViewById(R.id.lang_descrip);
        notice_descrip = (TextView)findViewById(R.id.notice_descrip);


        SharedPreferences preferences = getSharedPreferences("a",MODE_PRIVATE);
        int first_flag = preferences.getInt("First",0);

        final SharedPreferences setting = getSharedPreferences("prefrence_setting",MODE_PRIVATE);
        final SharedPreferences.Editor editor = setting.edit();
        toggled = setting.getInt("auto_notice",0);
        if(toggled==1){
            auto_Notice.setChecked(true);
        }
        else{
            auto_Notice.setChecked(false);
        }



        auto_Notice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(auto_Notice.isChecked()){
                    editor.putInt("auto_notice",1);
                    editor.apply();
                }
                else if(!auto_Notice.isChecked()){
                    editor.putInt("auto_notice",0);
                    editor.apply();

                }
            }
        });


        if(first_flag !=1){
            Toast toast = Toast.makeText(this, getResources().getString(R.string.first_set_msg), Toast.LENGTH_LONG);
            toast.show();
            cancel.setVisibility(View.GONE);
        }
        else
            cancel.setVisibility(View.VISIBLE);

        SharedPreferences pref = getSharedPreferences("lang",MODE_PRIVATE);

        if(pref.getInt("setlang",0)==0){
            ko.setChecked(true);
            lang = 0;
        }
        else {
            eng.setChecked(true);
            lang = 1;
        }



        ko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lang_text.setText("언어 설정");
                auto_Notice.setText("알림 자동 전환");
                notice_descrip.setText("한 줄 공지를 자동으로 전환시킵니다.\n비활성화시 수동으로 페이지를 넘깁니다");
                lang_descrip.setText("언어를 선택합니다.");
                commit.setText("확인");
                cancel.setText("취소");
                lang = 0;
                Log.e("세팅",""+lang);
            }
        });
        eng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lang_text.setText("Language");
                auto_Notice.setText("Notice Auto Switching");
                lang_descrip.setText("Set the language.");
                notice_descrip.setText("Select whether to flip notification\nautomatically or manually.");
                commit.setText("CONFIRM");
                cancel.setText("CANCEL");
                lang = 1;
                Log.e("세팅",""+lang);
            }
        });
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                SharedPreferences a = getSharedPreferences("lang",MODE_PRIVATE);
                SharedPreferences.Editor editor = a.edit();
                editor.putInt("setlang",lang);
                editor.apply();

                if(lang==0){
                    Locale myLocale = new Locale("ko");
                    Resources res = getResources();
                    DisplayMetrics dm = res.getDisplayMetrics();
                    Configuration conf = res.getConfiguration();
                    conf.locale = myLocale;
                    res.updateConfiguration(conf, dm);
                }
                else {
                    Locale myLocale = new Locale("en");
                    Resources res = getResources();
                    DisplayMetrics dm = res.getDisplayMetrics();
                    Configuration conf = res.getConfiguration();
                    conf.locale = myLocale;
                    res.updateConfiguration(conf, dm);
                }

                SharedPreferences first = getSharedPreferences("a",MODE_PRIVATE);
                if(first.getInt("First",0)!=1) {
                    Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Intent intent = new Intent(SettingActivity.this, Home_Main.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }


            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                finish();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                return true;
        }return super.onKeyDown(keyCode, event);}  //BACK버튼 비활성화

}
