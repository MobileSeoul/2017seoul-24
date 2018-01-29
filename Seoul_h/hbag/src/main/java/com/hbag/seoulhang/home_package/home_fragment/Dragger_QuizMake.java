package com.hbag.seoulhang.home_package.home_fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.hbag.seoulhang.R;
import com.hbag.seoulhang.home_package.Home_Main;
import com.hbag.seoulhang.joinmanage_package.login_package.UserProfileData_singleton;
import com.hbag.seoulhang.model.retrofit.InventoryDTO;
import com.hbag.seoulhang.network.Ipm;
import com.hbag.seoulhang.network.NetworkClient;
import com.liuguangqiang.swipeback.SwipeBackActivity;
import com.liuguangqiang.swipeback.SwipeBackLayout;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dragger_QuizMake extends SwipeBackActivity {


    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 2002;

    double latitude;
    double longitude;

    private LocationManager locationManager;
    private LocationListener locationListener;

    NetworkClient networkClient;
    Ipm ipm;
    UserProfileData_singleton profile;

    EditText quiz;
    EditText hint;

    RadioGroup Answer_ox;
    RadioButton bt_o;
    RadioButton bt_x;

    Button commit;
    Button cancel;

    String Answer_check;
    String sLocale;

    String squiz;
    String shint;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_make);
        setDragEdge(SwipeBackLayout.DragEdge.LEFT);

        //GPS 여부 체크
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            createGpsDisabledAlert();
        }

        settingGPS();

        Location userLocation = getMyLocation();

        if( userLocation != null ) {
            latitude = (float)userLocation.getLatitude();
            longitude = (float)userLocation.getLongitude();
        }



        profile = UserProfileData_singleton.getInstance();

        quiz = (EditText)findViewById(R.id.quiz_fpt);
        hint = (EditText)findViewById(R.id.hint_fpt);
        Answer_ox = (RadioGroup)findViewById(R.id.Answer_ox);
        bt_o = (RadioButton)findViewById(R.id.rd_o);
        bt_x = (RadioButton)findViewById(R.id.rd_x);
        commit = (Button)findViewById(R.id.commit);
        cancel = (Button)findViewById(R.id.cancel);

        SharedPreferences pref = getSharedPreferences("lang",MODE_PRIVATE);
        if(pref.getInt("setlang",0)==0){
            sLocale = "ko";
        }
        else {
            sLocale = "en";
        }

        ipm = new Ipm();
        final String ip = ipm.getip();
        final String loginid = profile.getId();
        final String username = profile.getNickname();

        Answer_ox.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if(checkedId==R.id.rd_o || checkedId == R.id.rd_x){
                    commit.setEnabled(true);
                }
                else commit.setEnabled(false);
            }
        });  //체크값이 없으면 제출버튼 비활성화

        networkClient = NetworkClient.getInstance(ip);

        //퀴즈 제출 버튼
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                squiz =  quiz.getText().toString();
                shint = hint.getText().toString();

                if(squiz.length()<=0||shint.length()<=0){
                    Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.enter_quizmake), Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }

                if(!checkquiz(squiz)){
                    Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.not_available_special_ch), Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }
                else if(!checkquiz(shint)){
                    Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.not_available_special_ch), Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }



                settingGPS();

                Location userLocation = getMyLocation();

                if( userLocation != null ) {
                    latitude = (float)userLocation.getLatitude();
                    longitude = (float)userLocation.getLongitude();
                }
                final AlertDialog.Builder alert = new AlertDialog.Builder(Dragger_QuizMake.this);
                alert.setCancelable(false);
                alert.setMessage(R.string.make_quiz_check);

                alert.setNegativeButton(R.string.취소, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                });
                alert.setPositiveButton(R.string.확인, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });
                final AlertDialog dialog = alert.create();
                dialog.show();
                dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(latitude==0||longitude==0){  //위치정보가 0이 들어가는 것 방지
                            Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.GPS활성여부), Toast.LENGTH_LONG);
                            toast.show();
                            finish();
                            return;
                        }
                        networkClient.getfinishitem(loginid, new Callback<List<InventoryDTO>>() {
                            @Override
                            public void onResponse(Call<List<InventoryDTO>> call, Response<List<InventoryDTO>> response) {
                                switch (response.code()){
                                    case 200:
                                        List<InventoryDTO> get_point = response.body();
                                        int check_point = get_point.get(0).getMakequiz();
                                        if(check_point>0){
                                            if(bt_o.isChecked()){
                                                Answer_check = "o";
                                            }
                                            if(bt_x.isChecked()){
                                                Answer_check = "x";
                                            }



                                            Log.d("GPS",loginid+username+squiz+shint+Answer_check+sLocale+latitude+longitude);

                                            networkClient.make_quiz(loginid, username, latitude, longitude, squiz, Answer_check, shint, sLocale, new Callback<Integer>() {
                                                @Override
                                                public void onResponse(Call<Integer> call, Response<Integer> response) {
                                                    switch (response.code()){
                                                        case 200:

                                                            Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.succes_make_quiz), Toast.LENGTH_LONG);
                                                            toast.show();
                                                            Intent intent = new Intent(Dragger_QuizMake.this, Home_Main.class);
                                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                                            startActivity(intent);
                                                            finish();
                                                            break;
                                                        default:
                                                            Log.d("제출", "실패");
                                                            break;
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<Integer> call, Throwable t) {
                                                    Log.e("ACC", "GPS에러 " + t.getMessage());
                                                    Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.can_not_connent_to_server), Toast.LENGTH_LONG);
                                                    toast.show();
                                                }
                                            });
                                        }
                                        else{
                                            Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.can_not_make_quiz), Toast.LENGTH_LONG);
                                            toast.show();
                                            dialog.dismiss();
                                        }
                                        break;
                                    default:
                                        break;
                                }
                            }

                            @Override
                            public void onFailure(Call<List<InventoryDTO>> call, Throwable t) {

                            }
                        });

                    }
                });

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private Location getMyLocation() {
        Location currentLocation = null;
        // Register the listener with the Location Manager to receive location updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // 사용자 권한 요청
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 2002);
        }
        else {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

            // 수동으로 위치 구하기
            String locationProvider = LocationManager.GPS_PROVIDER;
            currentLocation = locationManager.getLastKnownLocation(locationProvider);
            if (currentLocation != null) {
                longitude = currentLocation.getLongitude();
                latitude = currentLocation.getLatitude();
                Log.d("Main", "longtitude=" + longitude + ", latitude=" + latitude);
            }
        }
        return currentLocation;
    }

    private void settingGPS() {
        // Acquire a reference to the system Location Manager
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                // TODO 위도, 경도로 하고 싶은 것
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };
    }


    boolean canReadLocation = false;
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Location userLocation = getMyLocation();
                if( userLocation != null ) {
                    latitude = userLocation.getLatitude();
                    longitude = userLocation.getLongitude();
                }
                canReadLocation = true;
            } else {
                canReadLocation = false;
            }
        }
    }

    private void createGpsDisabledAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.GPSMessage)
                .setCancelable(false).setPositiveButton(R.string.GPSY,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        showGpsOptions();
                    }
                })
                .setNegativeButton(R.string.GPSN,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                                dialog.cancel();;
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private  void showGpsOptions(){
        Intent gpsOptionsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(gpsOptionsIntent);
    }

    public boolean checkquiz(String id){ //인젝션 방지
        String regex = "^[가-힣a-zA-Z0-9\\p{Punct}\\p{Space}][^\\/#&<>]{4,40}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(id);
        return m.matches();
    }
}
