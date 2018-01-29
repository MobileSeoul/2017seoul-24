package com.hbag.seoulhang.home_package.home_fragment;

import android.support.v7.app.AppCompatDialog;

import com.hbag.seoulhang.appbase_package.RealmInit;

/**
 * Created by cwh62 on 2017-09-10.
 */

public class BaseFragment extends android.support.v4.app.Fragment{
    private static final BaseFragment ourInstance = new BaseFragment();

    static BaseFragment getInstance() {
        return ourInstance;
    }

    private AppCompatDialog progressDialog;

    public BaseFragment() {
    }

    public void progressON() { RealmInit.getInstance().progressON(getActivity(), null); }
    public void progressON(String message) { RealmInit.getInstance().progressON(getActivity(), message); }
    public void progressOFF() { RealmInit.getInstance().progressOFF(); }
}
