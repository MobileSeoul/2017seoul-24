package com.hbag.seoulhang.joinmanage_package.tutorial_package;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hbag.seoulhang.R;
import com.hbag.seoulhang.joinmanage_package.login_package.UserProfileData_singleton;

import java.util.Locale;


public class tuto_a extends Fragment
{
    UserProfileData_singleton profile;
    public tuto_a()
    {

    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.fragment_tuto_a, container, false);
        profile = UserProfileData_singleton.getInstance();
        TextView tv = (TextView)layout.findViewById(R.id.tv_1);
        tv.setText(getString(R.string.welcome,profile.getNickname()));
        profile.getName();
        return layout;
    }
}



