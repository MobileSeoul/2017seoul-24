package com.hbag.seoulhang.joinmanage_package;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

import com.hbag.seoulhang.R;

import java.util.Locale;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.splash);
        super.onCreate(savedInstanceState);
        Handler s = new Handler();
        s.postDelayed(new splashhanddler(),2000);

        SharedPreferences pref = getSharedPreferences("lang",MODE_PRIVATE);


        if(pref.getInt("setlang",0)==0){
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
    }
    private class splashhanddler implements Runnable{
        @Override
        public void run() {
            SharedPreferences first = getSharedPreferences("a",MODE_PRIVATE);
            if(first.getInt("First",0)!=1){
                startActivity(new Intent(getApplication(),SettingActivity.class));
                SplashActivity.this.finish();
            }
            else
                startActivity(new Intent(getApplication(),LoginActivity.class));
                SplashActivity.this.finish();

        }
    }
}




