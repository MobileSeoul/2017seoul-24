package com.hbag.seoulhang.joinmanage_package.register_package;

import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.hbag.seoulhang.appbase_package.BaseActivity;
import com.hbag.seoulhang.R;
import com.hbag.seoulhang.network.Ipm;
import com.hbag.seoulhang.network.NetworkClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinActivity extends BaseActivity {

    Button commit;
    Button cancel;
    NetworkClient networkClient;
    EditText id;
    EditText password;
    EditText passwordconfirm;
    EditText name;
    EditText email;
    Ipm ipm;
    ActionProcessButton check;
    Boolean check_tf;
    private long mLastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        ipm = new Ipm();
        check_tf=false; // 성별 체크 유무 확인.

        commit = (Button)findViewById(R.id.commit);
        cancel = (Button)findViewById(R.id.cancel);
        id = (EditText)findViewById(R.id.id_t);
        password = (EditText)findViewById(R.id.password_t);
        passwordconfirm = (EditText)findViewById(R.id.password_ct);
        name = (EditText)findViewById(R.id.name_t);
        email = (EditText) findViewById(R.id.email_t);
        check = (ActionProcessButton) findViewById(R.id.check_b);


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

        id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                check.setProgress(0);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        check.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                String sid = id.getText().toString();
                check.setProgress(50);
                id.requestFocus();

                if(id.getText().length()<=0){
                    id.setError((getResources().getString(R.string.EID)));
                    check.setProgress(-1);
                }
                else if(!checkId(id.getText().toString())){
                    id.setError(getString(R.string.not_good_id));
                    check.setProgress(-1);
                }

                else{

                    networkClient.checkid(sid, new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            switch (response.code()) {

                                case 200:
                                    check.setProgress(100);
                                    check_tf = true;
                                    break;

                                default:
                                    check.setProgress(-1);
                                    id.setError(getString(R.string.please_check_already_id));
                                    break;
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            check.setProgress(-1);
                            Log.e("ACC", "s?? " + t.getMessage());
                            Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.can_not_connent_to_server), Toast.LENGTH_LONG);
                            toast.show();

                        }
                    });
                }
            }
        });




        commit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                boolean isFailed = false;


                if(id.getText().length()<=0){ id.setError((getResources().getString(R.string.EID))); isFailed = true;}
                if(!passcheck(password.getText().toString())||!special_ch(password.getText().toString())){password.setError(getResources().getString(R.string.EPass));isFailed = true;}
                if(!password.getText().toString().equals(passwordconfirm.getText().toString())){passwordconfirm.setError(getResources().getString(R.string.비밀번호일치)); isFailed = true;}
                if(name.getText().length()<=0){ name.setError(getResources().getString(R.string.Ename)); isFailed = true;}
                else if(!checkname(name.getText().toString())){name.setError(getResources().getString(R.string.not_available_name)); isFailed=true;}
                if(email.getText().length()<=0){email.setError(getResources().getString(R.string.EEmail)); isFailed = true;}
                if(!checkEmail(email.getText().toString())){email.setError(getResources().getString(R.string.checkEmail)); isFailed = true;}

                if(!isFailed) {

                    String sid = id.getText().toString();
                    final String spass = password.getText().toString();
                    final String scpass = passwordconfirm.getText().toString();
                    String sname = name.getText().toString();
                    String semail = email.getText().toString();
                    String logintype = "normal";

                    networkClient.getjoin(sid, spass, sname, semail, logintype, new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if (spass.equals(scpass)) {
                                switch (response.code()) {
                                    case 200:
                                        finish();
                                        Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.회원가입성공), Toast.LENGTH_LONG);
                                        toast.show();
                                        break;

                                    case 205:
                                        Log.e("TAG", "입력 제한");
                                        Toast toast_f = Toast.makeText(getApplicationContext(), getResources().getString(R.string.please_check_already_id), Toast.LENGTH_LONG);
                                        toast_f.show();
                                        break;

                                }
                            }

                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Log.e("ACC", "s?? " + t.getMessage());
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
        }return super.onKeyDown(keyCode, event);}  //BACK버튼 비활성화

}
