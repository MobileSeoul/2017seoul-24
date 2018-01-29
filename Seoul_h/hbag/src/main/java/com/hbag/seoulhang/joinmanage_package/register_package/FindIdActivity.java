package com.hbag.seoulhang.joinmanage_package.register_package;

import android.os.SystemClock;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hbag.seoulhang.appbase_package.BaseActivity;
import com.hbag.seoulhang.R;
import com.hbag.seoulhang.model.retrofit.FindDTO;
import com.hbag.seoulhang.network.Ipm;
import com.hbag.seoulhang.network.NetworkClient;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FindIdActivity extends BaseActivity {

    EditText name;
    EditText Email;
    Button commit;
    Button cancel;
    ProgressBar progressBar;
    NetworkClient networkClient;
    Ipm ipm;
    private long mLastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_id);
        ipm = new Ipm();
        commit = (Button)findViewById(R.id.commit);
        cancel = (Button)findViewById(R.id.cancel);
        name = (EditText)findViewById(R.id.name_fit);
        Email = (EditText)findViewById(R.id.email_fit);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

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


                if (name.getText().length() <= 0) {
                    Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.Ename), Toast.LENGTH_LONG);
                    toast.show();
                }
                else if(!checkname(name.getText().toString())){
                    Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.not_available_name), Toast.LENGTH_LONG);
                    toast.show();
                }else if (Email.getText().length() <= 0) {
                Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.EEmail), Toast.LENGTH_LONG);
                toast.show();
                } else if (!checkEmail(Email.getText().toString())) {
                    Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.checkEmail), Toast.LENGTH_LONG);
                    toast.show();
                }

                else {

                    progressON(getResources().getString(R.string.Loading)); //응답대기 프로그레스 애니메이션:

                    String sname = name.getText().toString();
                    String semail = Email.getText().toString();
                    String ip = ipm.getip();
                    networkClient = NetworkClient.getInstance(ip);
                    networkClient.findid(sname, semail, new Callback<FindDTO>() {
                        @Override
                        public void onResponse(Call<FindDTO> call, Response<FindDTO> response) {
                            switch (response.code()) {
                                case 200:
                                    progressOFF();
                                    Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.확인메일보냄), Toast.LENGTH_LONG);
                                    toast.show();
                                    finish();
                                    break;
                                default:
                                    progressOFF();
                                    Toast toast_2 = Toast.makeText(getApplicationContext(), getResources().getString(R.string.아이디찾을수없음), Toast.LENGTH_LONG);
                                    toast_2.show();
                                    break;

                            }

                        }

                        @Override
                        public void onFailure(Call<FindDTO> call, Throwable t) {
                            Log.e("ACC", "s?? " + t.getMessage());
                            Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.can_not_connent_to_server), Toast.LENGTH_LONG);
                            toast.show();
                            progressOFF();

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
