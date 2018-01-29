package com.hbag.seoulhang.home_package;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.hbag.seoulhang.R;

public class LogoutDialog extends Dialog{
    private Button bt_yes;
    private Button bt_no;
    private View.OnClickListener click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);
        setContentView(R.layout.activity_logout_dialog);


        bt_yes = (Button)findViewById(R.id.bt_yes);
        bt_no = (Button)findViewById(R.id.bt_no);
        bt_no.setTag(1);
        bt_yes.setTag(2);

        if(click!=null) {
            bt_yes.setOnClickListener(click);
            bt_no.setOnClickListener(click);
        }


    }
    public LogoutDialog(Context context, View.OnClickListener confirmlistener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.click = confirmlistener;
    }
}
