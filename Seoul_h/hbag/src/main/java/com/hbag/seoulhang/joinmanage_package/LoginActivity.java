package com.hbag.seoulhang.joinmanage_package;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.dd.processbutton.iml.ActionProcessButton;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.hbag.seoulhang.appbase_package.BaseActivity;
import com.hbag.seoulhang.home_package.BackPressCloseHandler;
import com.hbag.seoulhang.home_package.Home_Main;
import com.hbag.seoulhang.R;
import com.hbag.seoulhang.home_package.LogoutDialog;
import com.hbag.seoulhang.model.retrofit.PlayerDTO;
import com.hbag.seoulhang.network.Ipm;
import com.hbag.seoulhang.network.NetworkClient;
import com.hbag.seoulhang.joinmanage_package.login_package.UserProfileData_singleton;
import com.hbag.seoulhang.joinmanage_package.register_package.FindIdActivity;
import com.hbag.seoulhang.joinmanage_package.register_package.FindPassActivity;
import com.hbag.seoulhang.joinmanage_package.register_package.JoinActivity;

import org.json.JSONObject;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends BaseActivity implements
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "LoginActivity";
    private static final int RC_SIGN_IN = 9001;

    private GoogleApiClient mGoogleApiClient;

    LinearLayout login_lay;
    LinearLayout regist_lay;
    LinearLayout syncbt_lay;
    LinearLayout loginbt_lay;
    TextView title_login;
    ActionProcessButton login;
    Button set;
    TextView logoutsync;
    Button bt_facebookLogin;
    Button bt_googleSignbt;
    EditText loginId_tv;
    TextView join;
    TextView findid;
    TextView findPass;
    EditText password;
    String loginid;
    String snickname;
    LogoutDialog logoutDialog;
    NetworkClient networkClient;
    Ipm ipm;
    private BackPressCloseHandler backPressCloseHandler;
    CallbackManager callbackManager;
    InputMethodManager keyboard;
    private long mLastClickTime = 0;
    UserProfileData_singleton profile;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        callbackManager = CallbackManager.Factory.create();
        login_lay = (LinearLayout) findViewById(R.id.text_log_lay);
        regist_lay = (LinearLayout) findViewById(R.id.register_lay);
        syncbt_lay = (LinearLayout) findViewById(R.id.syncbtset_lay);
        loginbt_lay = (LinearLayout) findViewById(R.id.loginbutton_lay);
        title_login = (TextView) findViewById(R.id.iv_title);
        password = (EditText) findViewById(R.id.loginPass);
        loginId_tv = (EditText) findViewById(R.id.loginId);
        join = (TextView) findViewById(R.id.join_tm);
        findid = (TextView) findViewById(R.id.find_id);
        findPass = (TextView) findViewById(R.id.find_pass_t);
        login = (ActionProcessButton) findViewById(R.id.Login);
        set = (Button) findViewById(R.id.bt_set);
        logoutsync = (TextView) findViewById(R.id.bt_logout_sync);
        bt_facebookLogin = (Button) findViewById(R.id.bt_facebookLogin);
        bt_googleSignbt = (Button) findViewById(R.id.bt_googleLogin);
        ipm = new Ipm();
        backPressCloseHandler = new BackPressCloseHandler(this);
        final String ip = ipm.getip();
        networkClient = NetworkClient.getInstance(ip);
        keyboard = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        profile = UserProfileData_singleton.getInstance();

        FacebookSdk.sdkInitialize(getApplicationContext());

        sharedPreferences = getSharedPreferences("log", MODE_PRIVATE);
        final String loginPref = sharedPreferences.getString("logintype", "");     //이전 접속 상태를 불러온다.
        editor = sharedPreferences.edit();
        Log.d("Login", loginPref);
        if (loginPref.equals("facebook") || loginPref.equals("google") || loginPref.equals("normal")) {
            UI_State1(1);
            login.setEnabled(false);
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder
                (GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        bt_googleSignbt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 600) {
                    return;
                }
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(intent, RC_SIGN_IN);
            }
        });


        bt_facebookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 600) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                //LoginManager - 요청된 읽기 또는 게시 권한으로 페이스북 로그인 절차를 시작합니다.
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this,
                        Arrays.asList("public_profile", "email"));
                LoginManager.getInstance().registerCallback(callbackManager,
                        new FacebookCallback<LoginResult>() {
                            @Override
                            public void onSuccess(LoginResult loginResult) {
                                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                                        new GraphRequest.GraphJSONObjectCallback() {
                                            @Override
                                            public void onCompleted(JSONObject object, GraphResponse response) {
                                                try {
                                                    profile.setId(object.getString("id"));
                                                    Log.d("페이스북 계정", profile.getId());
                                                    profile.setEmail(object.getString("email"));
                                                    profile.setName(object.getString("name"));
                                                    Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.loginsuccess, profile.getName()), Toast.LENGTH_LONG);
                                                    toast.show();
                                                    login.setEnabled(true);
                                                    UI_State1(1);
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                    UI_State1(0);
                                                    Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.계정연동실패), Toast.LENGTH_LONG);
                                                    toast.show();
                                                    login.setEnabled(true);

                                                }
                                            }
                                        });
                                Bundle parameters = new Bundle();
                                parameters.putString("fields", "id,name,email");
                                request.setParameters(parameters);
                                request.executeAsync();
                                Log.d("페이스북 계정 연동 : ", "성공");

                            }

                            @Override
                            public void onCancel() {
                                Log.e("페이스북 계정 연동 : ", "취소됨");
                                UI_State1(0);
                                login.setEnabled(true);
                            }

                            @Override
                            public void onError(FacebookException exception) {
                                Log.e("페이스북 계정 연동 : ", "에러 " + exception.getLocalizedMessage());
                                Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.계정연동실패), Toast.LENGTH_LONG);
                                UI_State1(0);
                                login.setEnabled(true);
                                toast.show();
                            }
                        });
                profile.setLoginType("facebook");
                editor.putString("logintype", profile.getLoginType());   //로그인 상태 저장
                editor.apply();
            }
        });

        logoutsync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 600) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                logoutDialog = new LogoutDialog(LoginActivity.this, clicklistener);
                logoutDialog.show();
            }

        });


        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//게임자체 계정으로 로그인합니다.
                if (SystemClock.elapsedRealtime() - mLastClickTime < 600) {
                    return;
                }
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(password.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(loginId_tv.getWindowToken(), 0);

                mLastClickTime = SystemClock.elapsedRealtime();
                final String sLoginid = loginId_tv.getText().toString();
                final String sPassword = password.getText().toString();
                loginid = sLoginid;
                profile.setId(sLoginid);

                Log.e("아이디", sLoginid);
                Log.e("비밀번호", sPassword);

                Log.e("ACC", "TEAM id IS !!! " + loginid);
                // progressON(getResources().getString(R.string.Loading)); //응답대기 애니메이션

                networkClient.login(sLoginid, sPassword, new Callback<PlayerDTO>() {
                    @Override
                    public void onResponse(Call<PlayerDTO> call, Response<PlayerDTO> response) {
                        Log.e("아이디", sLoginid);
                        Log.e("비밀번호", sPassword);
                        switch (response.code()) {
                            case 200:
                                profile.setLoginType("normal");
                                editor.putString("logintype", "normal");
                                editor.apply();
                                UI_State1(1);
                                Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.loginsuccess, loginid), Toast.LENGTH_LONG);
                                toast.show();
                                login.setEnabled(true);
                                editor.putString("id", sLoginid);
                                editor.apply();             //디바이스 내부에 아이디를 저장시킵니다.
                                //  progressOFF();
                                break;

                            default:
                                Log.e("TAG", "다른 아이디");
                                // progressOFF();
                                Toast toast_2 = Toast.makeText(getApplicationContext(), getResources().getString(R.string.please_check_id), Toast.LENGTH_LONG);
                                toast_2.show();
                                login.setEnabled(true);
                                break;
                        }
                    }

                    @Override
                    public void onFailure(Call<PlayerDTO> call, Throwable t) {
                        Log.e("ACC", "s?? " + t.getMessage());
                        //  progressOFF();
                        Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.can_not_connent_to_server), Toast.LENGTH_LONG);
                        toast.show();
                        login.setEnabled(true);

                    }
                });
            }
        });


        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 600) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                clean_tv();
                Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
                startActivity(intent);
            }
        });

        findid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 600) {
                    return;
                }
                clean_tv();
                mLastClickTime = SystemClock.elapsedRealtime();
                Intent intent = new Intent(LoginActivity.this, FindIdActivity.class);
                startActivity(intent);
            }
        });
        findPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 600) {
                    return;
                }
                clean_tv();
                mLastClickTime = SystemClock.elapsedRealtime();
                Intent intent = new Intent(LoginActivity.this, FindPassActivity.class);
                startActivity(intent);
            }
        });


        login.setOnClickListener(new View.OnClickListener() { //페이스북 또는 네이버로 시작버튼 누를시 회원가입처리

            @Override
            public void onClick(View view) {
                login.setProgress(1);
                login.setEnabled(false);
                if (SystemClock.elapsedRealtime() - mLastClickTime < 300) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();


                final String email = profile.getEmail();
                final String name = profile.getName();
                final String logintype = profile.getLoginType();

                switch (logintype) {
                    case "normal":
                        loginid = sharedPreferences.getString("id", "");
                        profile.setId(loginid);
                        startLogin();
                        break;
                    case "facebook":
                        loginid = profile.getId();
                        networkClient.getjoin(loginid, " ", name, email, logintype, new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                switch (response.code()) {
                                    case 200:
                                        Log.d("페이스북으로 회원가입", loginid);
                                        startLogin();
                                        break;
                                    default:
                                        startLogin();
                                        break;
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                login.setProgress(0);
                                login.setEnabled(true);
                                Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.can_not_connent_to_server), Toast.LENGTH_LONG);
                                toast.show();
                            }
                        });
                        break;

                    case "google":
                        Log.d("구글", name);
                        loginid = profile.getId();
                        profile.setId(loginid);
                        networkClient.getjoin(loginid, " ", name, email, logintype, new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                switch (response.code()) {
                                    case 200:
                                        Log.d("구글회원가입", loginid);
                                        startLogin();
                                        break;
                                    default:
                                        startLogin();
                                        break;
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                login.setProgress(0);
                                login.setEnabled(true);
                                Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.can_not_connent_to_server), Toast.LENGTH_LONG);
                                toast.show();
                            }
                        });
                        break;
                    default:
                        break;
                }
            }

        });


        if (!loginPref.equals("logout")) {   //이전 접속데이터로 즉시 로그인한다.
            profile.setLoginType(loginPref);
            String LoginTypeChecker = profile.getLoginType();
            switch (LoginTypeChecker) {
                case "facebook":
                    bt_facebookLogin.performClick();
                    break;
                case "google":
                    Intent intent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                    startActivityForResult(intent, RC_SIGN_IN);
                    UI_State1(1);
                    break;
                case "normal":
                    String sLoginid = sharedPreferences.getString("id", "");
                    Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.loginsuccess, sLoginid), Toast.LENGTH_LONG);
                    toast.show();
                    UI_State1(1);
                    login.setEnabled(true);
                    login.setProgress(0);
                    break;
                default:
                    break;
            }
        } else {
            profile.setLoginType(loginPref);
        }

        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(v.getId()==R.id.loginPass && actionId== EditorInfo.IME_ACTION_DONE) {
                    set.performClick();
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {                               //구글에서 유저정보를 받아온다.
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount acct = result.getSignInAccount();
                profile.setName(acct.getDisplayName());
                profile.setEmail(acct.getEmail());
                profile.setId(acct.getId());

                Log.d("구글로그인", profile.getId()
                        + " " + profile.getName()
                        + " " + profile.getEmail());
                profile.setLoginType("google");
                SharedPreferences sharedPreferences = getSharedPreferences("log", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("logintype", profile.getLoginType());   //로그인 상태 저장
                editor.apply();
                Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.loginsuccess, profile.getName()), Toast.LENGTH_LONG);
                toast.show();
                login.setEnabled(true);
                login.setProgress(0);
                UI_State1(1);
            } else {
                UI_State1(0);
                Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.google_failed_to_login), Toast.LENGTH_LONG);
                toast.show();
                login.setEnabled(true);
                login.setProgress(0);
                Log.d("구글로그인", "실패");
            }
        }
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {   //구글 연동 실패 시 호출
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }


    public void UI_State1(int state) {
        switch (state) {
            case 0:                                           //초기 로그인선택화면 표시
                syncbt_lay.setVisibility(View.GONE);
                regist_lay.setVisibility(View.VISIBLE);
                login_lay.setVisibility(View.VISIBLE);
                loginbt_lay.setVisibility(View.VISIBLE);
                title_login.setVisibility(View.VISIBLE);
                login.setProgress(0);
                login.setEnabled(true);
                break;
            case 1:                                           //최종시작화면 표시
                syncbt_lay.setVisibility(View.VISIBLE);
                regist_lay.setVisibility(View.GONE);
                login_lay.setVisibility(View.GONE);
                loginbt_lay.setVisibility(View.GONE);
                title_login.setVisibility(View.GONE);
                login.setProgress(0);
                break;
            default:
                break;
        }

    }

    public void clean_tv() { //로그인창의 텍스트뷰를 비운다.
        loginId_tv.setText("");
        password.setText("");
    }

    private View.OnClickListener clicklistener = new View.OnClickListener() { //로그아웃 다이알로그의 리스너
        @Override
        public void onClick(View v) {

            int tag = (int) v.getTag();     //다이알로그 버튼의 태그값을 이용하여 뷰 판단

            switch (tag) {
                case 1:
                    logoutDialog.dismiss();
                    break;
                case 2:
                    Log.d("페이스북 로그아웃", profile.getLoginType());
                    if (profile.getLoginType().equals("facebook")) {
                        LoginManager.getInstance().logOut();

                    } else if (profile.getLoginType().equals("google")) {
                        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                                new ResultCallback<Status>() {
                                    @Override
                                    public void onResult(@NonNull Status status) {
                                        Log.d("구글 로그아웃", profile.getLoginType());
                                    }
                                }
                        );
                    }
                    UI_State1(0);
                    editor.putString("logintype", "logout");   //로그인 상태 로그아웃으로 저장
                    editor.apply();
                    profile.setLoginType("logout");
                    logoutDialog.dismiss();
                    break;
                default:
                    break;
            }

        }
    };

    public void startLogin() {
        networkClient.startgame(loginid, new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                switch (response.code()){
                    case 200:
                        login.setProgress(100);
                        Log.d("닉네임 입력 필요: ",loginid);
                        int hasNickname = response.body();
                        String nick = Integer.toString(hasNickname);
                        if(hasNickname == 1) {
                            Log.d("닉네임 확인",nick);
                            final AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);

                            alert.setCancelable(false);
                            alert.setMessage(R.string.닉네임입력);

                            final EditText nickname = new EditText(LoginActivity.this);

                            nickname.setInputType(InputType.TYPE_CLASS_TEXT);
                            nickname.setMaxLines(1);
                            alert.setView(nickname);

                            alert.setPositiveButton(R.string.확인, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                            final AlertDialog dialog = alert.create();
                            dialog.show();
                            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    snickname = nickname.getText().toString();
                                    Log.d("닉네임",snickname);
                                    Log.d("닉네임",loginid);
                                    keyboard.hideSoftInputFromWindow(nickname.getWindowToken(), 0);
                                    if(!checknickname(snickname)){
                                        Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.not_available_nickname), Toast.LENGTH_LONG);
                                        toast.show();
                                    }
                                    else {
                                        networkClient.newnickname(loginid, snickname, new Callback<String>() {
                                            @Override
                                            public void onResponse(Call<String> call, Response<String> response) {
                                                switch (response.code()) {
                                                    case 200:
                                                        Intent intent = new Intent(LoginActivity.this, Home_Main.class);
                                                        startActivity(intent);
                                                        finish();
                                                        dialog.dismiss();
                                                        break;
                                                    default:
                                                        Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.같은닉네임존재), Toast.LENGTH_LONG);
                                                        toast.show();
                                                        break;
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<String> call, Throwable t) {
                                                login.setProgress(0);
                                                login.setEnabled(true);
                                                Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.can_not_connent_to_server), Toast.LENGTH_LONG);
                                                toast.show();
                                            }
                                        });
                                    }

                                }
                            });

                        }
                        else {
                            Intent intent = new Intent(LoginActivity.this, Home_Main.class);
                            startActivity(intent);
                            finish();
                            break;
                        }
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                login.setProgress(0);
                login.setEnabled(true);
                UI_State1(0);
                Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.can_not_connent_to_server), Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }
}

