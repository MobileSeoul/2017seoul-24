package com.hbag.seoulhang.home_package.home_fragment;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatDialog;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hbag.seoulhang.R;

/**
 * Created by cwh62 on 2017-05-15.
 */

public class Methods {

    private AppCompatDialog progressDialog;

    public int imageSelector(String region){
        switch (region){
            case "종로구":
                return R.drawable.region_jongrogu;
            case "중구":
                return R.drawable.region_jungu;
            case "용산구":
                return R.drawable.region_youngsan;
            case "성동구":
                return R.drawable.region_sungdongu;
            case "광진구":
                return R.drawable.region_gwangjingu;
            case "동대문구":
                return R.drawable.region_dondaemoongu;
            case "중랑구":
                return R.drawable.region_junglangu;
            case "성북구":
                return R.drawable.region_sungbukgu;
            case "강북구":
                return R.drawable.region_gangbukgu;
            case "도봉구":
                return R.drawable.region_dobongu;
            case "노원구":
                return R.drawable.region_nowongu;
            case "은평구":
                return R.drawable.region_enpyeong;
            case "서대문구":
                return R.drawable.region_seodaemoongu;
            case "마포구":
                return R.drawable.region_mapogu;
            case "양천구":
                return R.drawable.region_yangcheongu;
            case "강서구":
                return R.drawable.region_gangseogu;
            case "구로구":
                return R.drawable.region_gurogu;
            case "금천구":
                return R.drawable.region_geomcheongu;
            case "영등포구":
                return R.drawable.region_yungdeungpogu;
            case "동작구":
                return R.drawable.region_dongjakgu;
            case "관악구":
                return R.drawable.region_gwanakgu;
            case "서초구":
                return R.drawable.region_seochogu;
            case "강남구":
                return R.drawable.region_gannamgu;
            case "송파구":
                return R.drawable.region_songpagu;
            case "강동구":
                return R.drawable.region_gangdonggu;
            case "사용자퀴즈":
                return R.drawable.region_userquiz;
            case "Jongno-gu":
                return R.drawable.region_jongrogu;
            case "Jung-gu":
                return R.drawable.region_jungu;
            case "Youngsan-gu":
                return R.drawable.region_youngsan;
            case "Seongdong-gu":
                return R.drawable.region_sungdongu;
            case "Gwangjin-gu":
                return R.drawable.region_gwangjingu;
            case "Dongdaemun-gu":
                return R.drawable.region_dondaemoongu;
            case "Jungnang-gu":
                return R.drawable.region_junglangu;
            case "Seongbuk-gu":
                return R.drawable.region_sungbukgu;
            case "Gangbuk-gu":
                return R.drawable.region_gangbukgu;
            case "Dobong-gu":
                return R.drawable.region_dobongu;
            case "Nowon-gu":
                return R.drawable.region_nowongu;
            case "Eunpyeong-gu":
                return R.drawable.region_enpyeong;
            case "Seodaemun-gu":
                return R.drawable.region_seodaemoongu;
            case "Mapo-gu":
                return R.drawable.region_mapogu;
            case "Yangcheon-gu":
                return R.drawable.region_yangcheongu;
            case "Gangseo-gu":
                return R.drawable.region_gangseogu;
            case "Guro-gu":
                return R.drawable.region_gurogu;
            case "Geumcheon-gu":
                return R.drawable.region_geomcheongu;
            case "Yeongdeungpo-gu":
                return R.drawable.region_yungdeungpogu;
            case "Dongjak-gu":
                return R.drawable.region_dongjakgu;
            case "Gwanak-gu":
                return R.drawable.region_gwanakgu;
            case "Seocho-gu":
                return R.drawable.region_seochogu;
            case "Gangnam-gu":
                return R.drawable.region_gannamgu;
            case "Songpa-gu":
                return R.drawable.region_songpagu;
            case "Gangdong-gu":
                return R.drawable.region_gangdonggu;
            case "UserQuiz":
                return R.drawable.region_userquiz;
            default:
                return R.drawable.region_userquiz;
        }

    }
    public int imageSelector2(String region){
        switch (region){
            case "종로구":
                return R.drawable.rate_1;
            case "중구":
                return R.drawable.rate_2;
            case "용산구":
                return R.drawable.rate_3;
            case "성동구":
                return R.drawable.rate_4;
            case "광진구":
                return R.drawable.rate_5;
            case "동대문구":
                return R.drawable.rate_6;
            case "중랑구":
                return R.drawable.rate_7;
            case "성북구":
                return R.drawable.rate_8;
            case "강북구":
                return R.drawable.rate_9;
            case "도봉구":
                return R.drawable.rate_10;
            case "노원구":
                return R.drawable.rate_11;
            case "은평구":
                return R.drawable.rate_12;
            case "서대문구":
                return R.drawable.rate_13;
            case "마포구":
                return R.drawable.rate_14;
            case "양천구":
                return R.drawable.rate_15;
            case "강서구":
                return R.drawable.rate_16;
            case "구로구":
                return R.drawable.rate_17;
            case "금천구":
                return R.drawable.rate_18;
            case "영등포구":
                return R.drawable.rate_19;
            case "동작구":
                return R.drawable.rate_20;
            case "관악구":
                return R.drawable.rate_21;
            case "서초구":
                return R.drawable.rate_22;
            case "강남구":
                return R.drawable.rate_23;
            case "송파구":
                return R.drawable.rate_24;
            case "강동구":
                return R.drawable.rate_25;
            case "사용자퀴즈":
                return R.drawable.rate_26;
            case "Jongno-gu":
                return R.drawable.rate_1;
            case "Jung-gu":
                return R.drawable.rate_2;
            case "Youngsan-gu":
                return R.drawable.rate_3;
            case "Seongdong-gu":
                return R.drawable.rate_4;
            case "Gwangjin-gu":
                return R.drawable.rate_5;
            case "Dongdaemun-gu":
                return R.drawable.rate_6;
            case "Jungnang-gu":
                return R.drawable.rate_7;
            case "Seongbuk-gu":
                return R.drawable.rate_8;
            case "Gangbuk-gu":
                return R.drawable.rate_9;
            case "Dobong-gu":
                return R.drawable.rate_10;
            case "Nowon-gu":
                return R.drawable.rate_11;
            case "Eunpyeong-gu":
                return R.drawable.rate_12;
            case "Seodaemun-gu":
                return R.drawable.rate_13;
            case "Mapo-gu":
                return R.drawable.rate_14;
            case "Yangcheon-gu":
                return R.drawable.rate_15;
            case "Gangseo-gu":
                return R.drawable.rate_16;
            case "Guro-gu":
                return R.drawable.rate_17;
            case "Geumcheon-gu":
                return R.drawable.rate_18;
            case "Yeongdeungpo-gu":
                return R.drawable.rate_19;
            case "Dongjak-gu":
                return R.drawable.rate_20;
            case "Gwanak-gu":
                return R.drawable.rate_21;
            case "Seocho-gu":
                return R.drawable.rate_22;
            case "Gangnam-gu":
                return R.drawable.rate_23;
            case "Songpa-gu":
                return R.drawable.rate_24;
            case "Gangdong-gu":
                return R.drawable.rate_25;
            case "UserQuiz":
                return R.drawable.rate_26;
            default:
                return R.drawable.rate_26;
        }

    }
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
}
