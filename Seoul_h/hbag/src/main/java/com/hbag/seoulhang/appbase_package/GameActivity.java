package com.hbag.seoulhang.appbase_package;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.hbag.seoulhang.R;
import com.hbag.seoulhang.home_package.Home_Main;
import com.hbag.seoulhang.model.retrofit.InventoryDTO;
import com.hbag.seoulhang.model.retrofit.ItemDTO;
import com.hbag.seoulhang.network.Ipm;
import com.hbag.seoulhang.network.NetworkClient;
import com.unity3d.player.UnityPlayer;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
//유니티 액티비티를 불러오기위해 UnityPlayerActivity를 상속받아서 관련 메소드를 오버라이드 한다.
public class GameActivity extends UnityPlayerActivity {
    Ipm ipm;
    NetworkClient networkClient;
    List<ItemDTO> item = new ArrayList<>();
    List<InventoryDTO> inventory = new ArrayList<>();
    List<String> quizlist = new ArrayList<>();
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 2002;
    int count_msg = 0;



    @Override
    protected void onCreate(final Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        Intent id = getIntent();
        final String playerid = id.getExtras().getString("playerid");
        ipm = new Ipm();
        String ip = ipm.getip();
        networkClient = NetworkClient.getInstance(ip);

        networkClient.getstartitem(playerid, new Callback<List<ItemDTO>>() {
            @Override
            public void onResponse(Call<List<ItemDTO>> call, Response<List<ItemDTO>> response) {
                switch (response.code()){
                    case 200:
                        item=response.body();
                        for(int i = 0; i<item.size();i++){
                            quizlist.add(Integer.toString(item.get(i).getQuestioncode()));
                        }
                        networkClient.getfinishitem(playerid, new Callback<List<InventoryDTO>>() {
                            @Override
                            public void onResponse(Call<List<InventoryDTO>> call, Response<List<InventoryDTO>> response) {
                                switch (response.code()){
                                    case 200:
                                        inventory=response.body();
                                        for(int i=0; i<inventory.size();i++){
                                            quizlist.add(Integer.toString(inventory.get(i).getQuestioncode()));
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
                        break;
                    default:
                        break;
                }
                Log.d("퀴즈리스트",""+quizlist.get(0));
            }

            @Override
            public void onFailure(Call<List<ItemDTO>> call, Throwable t) {

            }
        });





        //GPS 여부 체크
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            createGpsDisabledAlert();
        }

        boolean CameraLocationRationale = ActivityCompat
                .shouldShowRequestPermissionRationale(this,
                        Manifest.permission.CAMERA);
        boolean GPSLocationRationale = ActivityCompat
                .shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION);
        boolean PhoneLocationRationale = ActivityCompat
                .shouldShowRequestPermissionRationale(this,
                        Manifest.permission.READ_PHONE_STATE);
        boolean StorageLocationRationale = ActivityCompat
                .shouldShowRequestPermissionRationale(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE);

        int permissionCamera = ContextCompat.checkSelfPermission(GameActivity.this,
                Manifest.permission.CAMERA);
        int permissionGPS = ContextCompat.checkSelfPermission(GameActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int permissionPhone = ContextCompat.checkSelfPermission(GameActivity.this,
                Manifest.permission.READ_PHONE_STATE);
        int permissionStorage = ContextCompat.checkSelfPermission(GameActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE);


        if (permissionCamera == PackageManager.PERMISSION_DENIED && CameraLocationRationale) {
            showDialogForPermission(getResources().getString(R.string.unity_permission_camera));
            count_msg++;
        }
        if (permissionGPS == PackageManager.PERMISSION_DENIED && GPSLocationRationale) {
            showDialogForPermission(getResources().getString(R.string.unity_permission_gps));
            count_msg++;
        }
        if (permissionPhone == PackageManager.PERMISSION_DENIED && PhoneLocationRationale) {
            showDialogForPermission(getResources().getString(R.string.unity_permission_phone));
            count_msg++;
        }
        if (permissionStorage == PackageManager.PERMISSION_DENIED && StorageLocationRationale) {
            showDialogForPermission(getResources().getString(R.string.unity_permission_storage));
            count_msg++;
        }
        if(count_msg==0) {
            ActivityCompat.requestPermissions(GameActivity.this, new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        }


    }
    private void showDialogForPermission(String msg) {

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(GameActivity.this);
        builder.setTitle(getResources().getString(R.string.알림));
        builder.setMessage(msg);
        builder.setCancelable(false);
        builder.setPositiveButton(getResources().getString(R.string.GPSY), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                count_msg--;
                if(count_msg==0) {
                    ActivityCompat.requestPermissions(GameActivity.this, new String[]{
                            Manifest.permission.CAMERA,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                }
            }
        });

        builder.create().show();
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

    @Override
    public void onTouch(String questioncode) {
        Log.d("실행됨","실행안됨");
        Intent id = getIntent();
        final String playerid = id.getExtras().getString("playerid");
        final int q_code = Integer.parseInt(questioncode);
        Log.d("게임액티비티확인",playerid);
        Log.e ("확인",playerid+q_code);
        networkClient.getquestioncode(playerid, q_code, new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                switch (response.code()) {
                    case 200:
                        Log.d("유니티디버그",playerid+" "+q_code);
                        Log.d("QUSETIONNUM: ",""+q_code);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.e("ACC", "유니티에러 " + t.getMessage());

            }

        });
        Intent intent = new Intent(GameActivity.this,Home_Main.class);
        intent.putExtra("got",q_code);
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && (grantResults[0] == PackageManager.PERMISSION_DENIED
                        || grantResults[1] == PackageManager.PERMISSION_DENIED
                        || grantResults[2] == PackageManager.PERMISSION_DENIED
                        || grantResults[3] == PackageManager.PERMISSION_DENIED)) {
                    Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.unity_denied), Toast.LENGTH_LONG);
                    toast.show();
                    finish();
                }

            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    @Override
    public void checkLocale(){
        SharedPreferences pref = getSharedPreferences("lang",MODE_PRIVATE);
        int lang = pref.getInt("setlang",0);
        String sLang="";

        if (lang == 0) {
            sLang = "ko";
        }
        else if (lang ==1) {
            sLang = "en";
        }


        UnityPlayer.UnitySendMessage("Main Camera","checkLocale",sLang);

        for(int i = 0; i<quizlist.size();i++){
            String quiz = quizlist.get(i);
            UnityPlayer.UnitySendMessage("Main Camera","getQuizlist",quiz);
        }
    }
}
