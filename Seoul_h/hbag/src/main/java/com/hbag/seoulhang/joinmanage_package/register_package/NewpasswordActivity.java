package com.hbag.seoulhang.joinmanage_package.register_package;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hbag.seoulhang.appbase_package.BaseActivity;
import com.hbag.seoulhang.R;
import com.hbag.seoulhang.model.retrofit.FindDTO;
import com.hbag.seoulhang.network.Ipm;
import com.hbag.seoulhang.network.NetworkClient;
import com.hbag.seoulhang.joinmanage_package.login_package.UserProfileData_singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewpasswordActivity extends BaseActivity {

    EditText password;
    EditText password_c;
    Button commit;
    Button cancel;
    NetworkClient networkClient;
    Ipm ipm;
    UserProfileData_singleton profile;
    private long mLastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newpassword);
        profile = UserProfileData_singleton.getInstance();
        ipm = new Ipm();
        commit = (Button) findViewById(R.id.commit);
        cancel = (Button) findViewById(R.id.cancel);
        password = (EditText)findViewById(R.id.password_fit);
        password_c = (EditText)findViewById(R.id.confirm_fit);
        String ip = ipm.getip();
        networkClient = NetworkClient.getInstance(ip);

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
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                String pass = password.getText().toString();
                String confirm  = password_c.getText().toString();
                String sid = profile.getId();


                if(!pass.equals(confirm)){
                    Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.비밀번호일치), Toast.LENGTH_LONG);
                    toast.show();
                }

                else if(!passcheck(password.getText().toString())||!special_ch(password.getText().toString())){
                    Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.EPass), Toast.LENGTH_LONG);
                    toast.show();
                }
                else {
                  networkClient.newpassword(sid, pass, new Callback<FindDTO>() {
                      @Override
                      public void onResponse(Call<FindDTO> call, Response<FindDTO> response) {
                          switch (response.code()) {
                              case 200:
                                  Toast toast_s = Toast.makeText(getApplicationContext(), getResources().getText(R.string.비밀번호변경완료),Toast.LENGTH_LONG);
                                  toast_s.show();
                                  finish();
                                  break;
                              default:
                                  Toast toast_f = Toast.makeText(getApplicationContext(), getResources().getString(R.string.can_not_connent_to_server), Toast.LENGTH_LONG);
                                  toast_f.show();
                                  break;
                          }
                      }

                      @Override
                      public void onFailure(Call<FindDTO> call, Throwable t) {
                          Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.can_not_connent_to_server), Toast.LENGTH_LONG);
                          toast.show();
                      }
                  });
                }
            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }  //BACK버튼 비활성화

}
