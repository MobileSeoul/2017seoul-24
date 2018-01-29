package com.hbag.seoulhang.home_package;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.hbag.seoulhang.R;
import com.hbag.seoulhang.joinmanage_package.login_package.UserProfileData_singleton;
import com.hbag.seoulhang.network.Ipm;
import com.hbag.seoulhang.network.NetworkClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by cwh62 on 2017-05-31.
 */

public class Dialog_Grade extends Dialog {


    private TextView tv_1;
    private TextView tv_2;
    private TextView tv_3;
    private TextView tv_4;
    private TextView tv_5;
    private TextView tv_6;
    private TextView tv_7;
    private TextView tv_8;
    private Button commit;
    private View.OnClickListener click;
    NetworkClient networkClient;
    Ipm ipm;
    UserProfileData_singleton profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);
        setContentView(R.layout.activity_grade_dialog);

        tv_1 = (TextView)findViewById(R.id.tv_unrank);
        tv_2 = (TextView)findViewById(R.id.tv_bronze);
        tv_3 = (TextView)findViewById(R.id.tv_silver);
        tv_4 = (TextView)findViewById(R.id.tv_gold);
        tv_5 = (TextView)findViewById(R.id.tv_platinum);
        tv_6 = (TextView)findViewById(R.id.tv_diamond);
        tv_7 = (TextView)findViewById(R.id.tv_master);
        tv_8 = (TextView)findViewById(R.id.tv_challenger);

        profile = UserProfileData_singleton.getInstance();

        ipm= new Ipm();
        String ip = ipm.getip();
        String loginid = profile.getId();

        networkClient = NetworkClient.getInstance(ip);

        commit = (Button)findViewById(R.id.commit);

        if(click!=null)
            commit.setOnClickListener(click);
        commit.setTag(0);

        networkClient.grade(loginid, new Callback<List<Integer>>() {
            @Override
            public void onResponse(Call<List<Integer>> call, Response<List<Integer>> response) {
                switch (response.code()){
                    case 200:
                        List<Integer> grade_rate;
                        grade_rate = response.body();
                        for(int i = 0 ;i<grade_rate.size();i++){
                            grade_rate.get(i);
                        }

                        tv_1.setText(getContext().getResources().getString(R.string.점수_grade,grade_rate.get(0)));
                        tv_2.setText(getContext().getResources().getString(R.string.점수_grade,grade_rate.get(1)));
                        tv_3.setText(getContext().getResources().getString(R.string.점수_grade,grade_rate.get(2)));
                        tv_4.setText(getContext().getResources().getString(R.string.점수_grade,grade_rate.get(3)));
                        tv_5.setText(getContext().getResources().getString(R.string.점수_grade,grade_rate.get(4)));
                        tv_6.setText(getContext().getResources().getString(R.string.점수_grade,grade_rate.get(5)));
                        tv_7.setText(getContext().getResources().getString(R.string.점수_grade,grade_rate.get(6)));
                        tv_8.setText(getContext().getResources().getString(R.string.점수_grade,grade_rate.get(7)));   //등급표에 승급 점수 표기
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<Integer>> call, Throwable t) {

            }
        });


    }
    public Dialog_Grade(Context context, View.OnClickListener singleListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.click = singleListener;
    }

}
