package com.hbag.seoulhang.home_package.home_fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.hbag.seoulhang.R;
import com.hbag.seoulhang.home_package.Home_Main;
import com.hbag.seoulhang.model.retrofit.HintDTO;
import com.hbag.seoulhang.network.Ipm;
import com.hbag.seoulhang.network.NetworkClient;
import com.liuguangqiang.swipeback.SwipeBackActivity;
import com.liuguangqiang.swipeback.SwipeBackLayout;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Dragger_Quiz extends SwipeBackActivity {

    TextView quiz_region;
    TextView quiz;
    TextView hint;
    EditText Answer;
    LinearLayout ox_lay;
    RadioGroup Answer_ox;
    RadioButton bt_o;
    RadioButton bt_x;
    Button Submit;
    Button Cancel;
    Button get_hint;
    String ip;
    Ipm ipm;

    NetworkClient networkClient;

    private ArrayList<MyData_Q> mDataset;
    private HintDTO hints;
    private int grade_check;
    private int dataPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_dragger);
        setDragEdge(SwipeBackLayout.DragEdge.LEFT);

        ipm = new Ipm();
        ip=ipm.getip();

        quiz_region = (TextView)findViewById(R.id.quiz_region);
        quiz = (TextView)findViewById(R.id.quiz);
        hint = (TextView)findViewById(R.id.hint_text);
        Answer = (EditText)findViewById(R.id.Answer);
        Answer_ox = (RadioGroup) findViewById(R.id.Answer_ox);
        ox_lay = (LinearLayout)findViewById(R.id.ox_lay);
        bt_o = (RadioButton)findViewById(R.id.rd_o);
        bt_x = (RadioButton)findViewById(R.id.rd_x);
        Submit = (Button)findViewById(R.id.commit);
        Cancel = (Button)findViewById(R.id.cancel);
        get_hint = (Button)findViewById(R.id.button_hint);
        networkClient = NetworkClient.getInstance(ip);

        Intent intent = getIntent();
        dataPosition = intent.getExtras().getInt("position");

        mDataset = (ArrayList<MyData_Q>) intent.getSerializableExtra("dataset");

        if(mDataset.get(dataPosition).q_type.equals("ox")){                    //문제형식에 따라 UI변경
            ox_lay.setVisibility(View.VISIBLE);
            Answer.setVisibility(View.GONE);
        }
        else if(mDataset.get(dataPosition).q_type.equals("default")){
            Answer.setVisibility(View.VISIBLE);
            ox_lay.setVisibility(View.GONE);
        }

        quiz_region.setText(mDataset.get(dataPosition).text_region);
        quiz.setText(mDataset.get(dataPosition).text_quiz);
        Submit.setEnabled(false);

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Methods methods;
                methods = new Methods();
                methods.progressON(Dragger_Quiz.this, getResources().getString(R.string.Loading)); //응답대기 애니메이션
                String answer="";
                ipm = new Ipm();
                String ip = ipm.getip();
                NetworkClient.getInstance(ip);
                int qun = Integer.parseInt(mDataset.get(dataPosition).text_num);
                Log.e("확인",""+qun);
                Log.e("확인",mDataset.get(dataPosition).playerid);
                if(mDataset.get(dataPosition).q_type.equals("ox")){  //퀴즈타입에 따라 반환값 타입 설정
                    if(bt_o.isChecked()){
                        answer = "o";
                    }
                    else if(bt_x.isChecked()){
                        answer = "x";
                    }
                }
                else if(mDataset.get(dataPosition).q_type.equals("default")){
                    String sAnswer = Answer.getText().toString();
                    answer = sAnswer.replaceAll("\\p{Digit}|\\p{Space}|\\p{Punct}", ""); //인젝션 에러방지와 공백으로 인한 오답 체크 처리
                }

                if (answer.equalsIgnoreCase(mDataset.get(dataPosition).Answer_q)) {  //해당 position에 있는 답과 입력값 비교
                    Log.e("확인",mDataset.get(dataPosition).playerid+ mDataset.get(dataPosition).text_num);
                    networkClient.checkanswer(mDataset.get(dataPosition).playerid, qun, new Callback<Integer>() {
                        @Override
                        public void onResponse(Call<Integer> call, Response<Integer> response) {
                            switch (response.code()) {
                                case 200:
                                    methods.progressOFF();
                                    grade_check = response.body();
                                    Log.e("확인","aaa"+grade_check);
                                    AlertDialog.Builder alert = new AlertDialog.Builder(Dragger_Quiz.this);
                                    alert.setPositiveButton(getResources().getString(R.string.확인), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();     //닫기
                                            Intent intent = new Intent(Dragger_Quiz.this,Home_Main.class);
                                            intent.putExtra("playerid",mDataset.get(dataPosition).playerid);
                                            startActivity(intent);
                                            finish();

                                        }
                                    });
                                    if(grade_check == 2) {
                                        methods.progressOFF();
                                        alert.setMessage(R.string.승급);
                                        alert.show();
                                    }
                                    else if(grade_check == 1) {
                                        methods.progressOFF();
                                        alert.setMessage(R.string.정답이다);
                                        alert.show();
                                    }
                                    break;
                                default:
                                    methods.progressOFF();
                                    break;
                            }
                        }

                        @Override
                        public void onFailure(Call<Integer> call, Throwable t) {
                            methods.progressOFF();
                        }
                    });


                }

                else if(mDataset.get(dataPosition).q_type.equals("ox")){
                    networkClient.ox_flag(mDataset.get(dataPosition).playerid, qun, new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            switch (response.code()){
                                case 200:
                                    String check = response.body();
                                    AlertDialog.Builder alert = new AlertDialog.Builder(Dragger_Quiz.this);
                                    alert.setPositiveButton(getResources().getString(R.string.확인), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(Dragger_Quiz.this,Home_Main.class);
                                            intent.putExtra("playerid",mDataset.get(dataPosition).playerid);
                                            startActivity(intent);
                                            finish();
                                            dialog.dismiss();     //닫기
                                        }
                                    });
                                    if(check.equals("rankup")) {
                                        methods.progressOFF();
                                        alert.setMessage(R.string.승급);
                                        alert.show();
                                    }
                                    else if(check.equals("success")) {
                                        methods.progressOFF();
                                        alert.setMessage(R.string.오답);
                                        alert.show();
                                    }
                                    break;
                                default:
                                    Log.d("oxFlag","default");
                                    methods.progressOFF();
                                    break;
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Log.d("oxFlag","failed");
                            methods.progressOFF();
                        }
                    });
                }

                else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(Dragger_Quiz.this);
                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();     //닫기
                        }
                    });
                    methods.progressOFF();
                    alert.setMessage(R.string.오답);
                    alert.show();
                }

            }
        });
        final String loginid = mDataset.get(dataPosition).playerid;
        final int qcode = Integer.parseInt(mDataset.get(dataPosition).text_num);


        networkClient.gethint(loginid, qcode, "check", new Callback<HintDTO>() {
            @Override
            public void onResponse(Call<HintDTO> call, Response<HintDTO> response) {
                switch (response.code()){
                    case 200:
                        HintDTO hintDTO = response.body();
                        int hint_flag = hintDTO.getHintflag();
                        if(hint_flag==1) {
                            get_hint.setVisibility(View.GONE);
                            hint.setText(mDataset.get(dataPosition).hint);
                            hint.setVisibility(View.VISIBLE);
                        }
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<HintDTO> call, Throwable t) {

            }
        });


        get_hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(Dragger_Quiz.this);
                alert.setMessage(getResources().getString(R.string.hint_msg));
                alert.setNegativeButton(getResources().getString(R.string.GPSN), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.setPositiveButton(getResources().getString(R.string.GPSY), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        networkClient.gethint(loginid,qcode,"get", new Callback<HintDTO>() {
                            @Override
                            public void onResponse(Call<HintDTO> call, Response<HintDTO> response) {
                                Log.d("퀴즈", "123");
                                switch (response.code()) {
                                    case 200:
                                        hints = response.body();
                                        if (hints.getHintcount()==1) {
                                            get_hint.setVisibility(View.GONE);
                                            hint.setText(mDataset.get(dataPosition).hint);
                                            hint.setVisibility(View.VISIBLE);
                                        } else {
                                            AlertDialog.Builder alert = new AlertDialog.Builder(Dragger_Quiz.this);
                                            alert.setPositiveButton(getResources().getString(R.string.GPSY), new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();     //닫기
                                                }
                                            });
                                            alert.setMessage(R.string.힌트없음);
                                            alert.show();
                                        }

                                        break;
                                    default:
                                        break;

                                }
                            }

                            @Override
                            public void onFailure(Call<HintDTO> call, Throwable t) {

                            }
                        });
                        dialog.dismiss();     //닫기
                    }
                });
                alert.show();

            }
        });
        Answer_ox.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if(checkedId==R.id.rd_o || checkedId == R.id.rd_x){
                    Submit.setEnabled(true);
                }
                else Submit.setEnabled(false);
            }
        });  //체크값이 없으면 제출버튼 비활성화
        Answer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int textsize = Answer.getText().length();
                if(textsize > 0 || mDataset.get(dataPosition).q_type.equals("ox")){
                    Submit.setEnabled(true);
                }
                else Submit.setEnabled(false);

            }  //텍스트 입력값이 없으면 제출버튼 비활성화

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

