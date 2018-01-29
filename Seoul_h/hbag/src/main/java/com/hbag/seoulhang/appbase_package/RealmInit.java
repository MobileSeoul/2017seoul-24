package com.hbag.seoulhang.appbase_package;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatDialog;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.hbag.seoulhang.R;

import io.fabric.sdk.android.Fabric;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by cwh62 on 2017-03-14.
 */
//통신에 필요한 Realm 메소드와 프로그레스 로딩이미지 소스를 App수준에 배치
public class RealmInit extends Application {
    private Realm realm;
    private static RealmInit ourInstance = new RealmInit();

    public static RealmInit getInstance() {
        return ourInstance;
    }
    private AppCompatDialog progressDialog;

    public void progressON(Activity activity, String message) {

        if (activity == null || activity.isFinishing()) {
            return;
        }


        if (progressDialog != null && progressDialog.isShowing()) {
            progressSET(message);
        } else {

            progressDialog = new AppCompatDialog(activity);
            progressDialog.setCancelable(false);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            progressDialog.setContentView(R.layout.progress_loading);
            progressDialog.show();

        }


        final ImageView img_loading_frame = (ImageView) progressDialog.findViewById(R.id.runningman_iv);
        final AnimationDrawable frameAnimation = (AnimationDrawable) img_loading_frame.getBackground();
        img_loading_frame.post(new Runnable() {
            @Override
            public void run() {
                frameAnimation.start();
            }
        });

        TextView tv_progress_message = (TextView) progressDialog.findViewById(R.id.loading_message);
        if (!TextUtils.isEmpty(message)) {
            tv_progress_message.setText(message);
        }


    }

    public void progressSET(String message) {

        if (progressDialog == null || !progressDialog.isShowing()) {
            return;
        }


        TextView tv_progress_message = (TextView) progressDialog.findViewById(R.id.loading_message);
        if (!TextUtils.isEmpty(message)) {
            tv_progress_message.setText(message);
        }

    }

    public void progressOFF() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        RealmInit.ourInstance = this;
        Log.e("debug","Application created!");
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(realmConfiguration);

//        configureRealmDatabase();
    }
    public Realm getRealm() {
        if(realm == null){
            realm = Realm.getDefaultInstance();
        }
        return realm;
    }

    public int setPrimaryKeyId(Class c){
        try{
            AtomicLong primaryKeyValue = new AtomicLong(realm.where(c).max("id").longValue());
            return (int)primaryKeyValue.incrementAndGet();
        }catch (NullPointerException e){
            return 1;
        }

    }

    private void configureRealmDatabase(){
        RealmConfiguration config = new RealmConfiguration.Builder().build();
//        Realm.deleteRealm(config);
        Realm.setDefaultConfiguration(config);
        realm = Realm.getDefaultInstance();
    }

}


