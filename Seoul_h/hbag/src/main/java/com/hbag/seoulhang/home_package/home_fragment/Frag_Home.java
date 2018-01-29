package com.hbag.seoulhang.home_package.home_fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.hbag.seoulhang.appbase_package.GameActivity;
import com.hbag.seoulhang.home_package.Home_Main;
import com.hbag.seoulhang.R;
import com.hbag.seoulhang.map_package.GooglemapsActivity;
import com.hbag.seoulhang.model.retrofit.NoticeDTO;
import com.hbag.seoulhang.model.retrofit.TopDTO;
import com.hbag.seoulhang.network.Ipm;
import com.hbag.seoulhang.network.NetworkClient;
import com.hbag.seoulhang.joinmanage_package.login_package.UserProfileData_singleton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;


/**
 * Created by cwh62 on 2017-04-11.
 */

public class Frag_Home extends BaseFragment {

    Home_Main home_main;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<MyData_T> myDataset;
    private List<NoticeDTO> notice;
    private List<TopDTO> inventories;
    private List<View> filp_list;
    NetworkClient networkClient;
    Methods methods;
    ImageView map_b;
    ImageView tuto;
    ImageView camera_b;
    ImageView achv_b;
    ViewFlipper viewFlipper;
    Ipm ipm;
    Handler handler;
    private long mLastClickTime = 0;
    UserProfileData_singleton profile;
    int autoflip;
    int sLocale;

    public Frag_Home() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.frag_home, container, false);

        filp_list = new ArrayList<>();
        profile = UserProfileData_singleton.getInstance();
        ipm = new Ipm();
        String ip = ipm.getip();
        final String loginid = profile.getId();
        map_b = (ImageView)layout.findViewById(R.id.mapview);
        camera_b = (ImageView) layout.findViewById(R.id.cameraon_b);
        tuto = (ImageView)layout.findViewById(R.id.tutorial_b);
        achv_b = (ImageView)layout.findViewById(R.id.achv_b);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.home_rev);
        home_main = (Home_Main)getActivity();
        viewFlipper =  (ViewFlipper)layout.findViewById(R.id.vf_notice);
        handler = new Handler(Looper.getMainLooper());
        networkClient = NetworkClient.getInstance(ip);

        final SharedPreferences setting = getActivity().getSharedPreferences("prefrence_setting",MODE_PRIVATE);
        final SharedPreferences lang = getActivity().getSharedPreferences("lang",MODE_PRIVATE);
        autoflip = setting.getInt("auto_notice",0);
        sLocale = lang.getInt("setlang",0);




        //튜토리얼 spotlight 타겟에 추가한다.
      map_b.getViewTreeObserver().addOnGlobalFocusChangeListener(new ViewTreeObserver.OnGlobalFocusChangeListener() {
          @Override
          public void onGlobalFocusChanged(View oldFocus, View newFocus) {
              ((Home_Main)getActivity()).targetMaker(achv_b,achv_b.getWidth()*40/100,getResources().getString(R.string.submitquiz_title),getResources().getString(R.string.makequiz_descrip));
              ((Home_Main)getActivity()).targetMaker(map_b,map_b.getWidth(),getResources().getString(R.string.map_view),getResources().getString(R.string.map_view_descrip));
              ((Home_Main)getActivity()).targetMaker(tuto,tuto.getHeight(),getResources().getString(R.string.replay),getResources().getString(R.string.replay_descrip));

              map_b.getViewTreeObserver().removeOnGlobalFocusChangeListener(this);

              Log.d("ITPANGPANG","onWindowFocusChanged");


              int width = map_b.getWidth();
              int height = map_b.getHeight();
              Log.d("size_map", "x=>" + width + ",y=>" + height);
              width = camera_b.getWidth();
              height = camera_b.getHeight();
              Log.d("size_camera", "x=>" + width + ",y=>" + height);
              width = tuto.getWidth();
              height = tuto.getHeight();
              Log.d("size_tuto", "x=>" + width + ",y=>" + height);
              width = achv_b.getWidth();
              height = achv_b.getHeight();
              Log.d("size_achv", "x=>" + width + ",y=>" + height);

          }
      });


        networkClient.toptenregion(loginid, new Callback<List<TopDTO>>() {  //인기 순위 top10 지역을 서버로 부터 불러온다.
            @Override
            public void onResponse(Call<List<TopDTO>> call, Response<List<TopDTO>> response) {
                switch (response.code()) {
                    case 200:
                        Log.d("퀴즈", "어댑터 세팅");
                        inventories = response.body();
                        mRecyclerView.setHasFixedSize(false);
                        mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false);
                        mLayoutManager.canScrollHorizontally();
                        mRecyclerView.setLayoutManager(mLayoutManager);  //레이아웃 매니저를 세팅한다.
                        myDataset = new ArrayList<>();
                        mAdapter = new RecyclerAdapter_T(myDataset);
                        mRecyclerView.setAdapter(mAdapter);       //어탭더 정의

                        methods = new Methods();



                        for(int i = 0 ; i<inventories.size();i++) {
                            TopDTO topDTO = inventories.get(i);
                            String sQuestion_num = Integer.toString(topDTO.getQuestioncode());
                            String sQuestion_name = topDTO.getQuestionname();
                            String sVisitCount = getResources().getString(R.string.visitors, topDTO.getCount());
                            String sRegion_name = topDTO.getRegion_name();

                            myDataset.add(new MyData_T(sQuestion_num, sQuestion_name, sRegion_name, sVisitCount, mRecyclerView));//각 인자들을 어댑터클래스의 데이터베이스에 전달.
                            Log.d("퀴즈번호 홈 :", "" + sQuestion_name);
                            Log.d("퀴즈지역 홈 :", "" +sQuestion_num);
                            Log.d("퀴즈방문자 홈 : ", " "+sVisitCount);
                        }


                        onResume();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<TopDTO>> call, Throwable t) {
                Log.e("ACC", "s?? " + t.getMessage());

            }
        });

        networkClient.notice(loginid, new Callback<List<NoticeDTO>>() {
            @Override
            public void onResponse(Call<List<NoticeDTO>> call, Response<List<NoticeDTO>> response) {
                notice = response.body();
                Animation showIn = AnimationUtils.loadAnimation(getContext(), R.anim.center_in_left);
                Animation showOut = AnimationUtils.loadAnimation(getContext(), R.anim.center_out_left);

                for (int j = 0; j < notice.size(); j++) {

                    NoticeDTO noticeDTO = notice.get(j);
                    String title_ko = noticeDTO.getTitle_ko();
                    String title_en = noticeDTO.getTitle_en();
                    String sDate = noticeDTO.getDate1();
                    String sDate2 = noticeDTO.getDate2();
                    if(sLocale==0) {  //언어에 따른 내용 삽입
                        ViewCreater(title_ko, sDate, sDate2);   //플리퍼에 추가할 뷰를 동적 생성
                    }
                    else if(sLocale==1){
                        ViewCreater(title_en, sDate, sDate2);
                    }
                    viewFlipper.addView(filp_list.get(j));  //생성한 뷰를 플리퍼에 추가
                    Log.d("데이트",sDate);

                }
                viewFlipper.setInAnimation(showIn);
                viewFlipper.setOutAnimation(showOut);
                if (autoflip == 1) {                   //자동플립 설정시
                    if(viewFlipper.getChildCount()>1) {
                        viewFlipper.setFlipInterval(5000);
                        viewFlipper.startFlipping();
                    }
                }
                else {                                  //수동플립 설정시
                    if(viewFlipper.getChildCount()>1) {
                        viewFlipper.setOnTouchListener(new View.OnTouchListener() {
                            float xAtDown;
                            float xAtUp;

                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                viewFlipper.requestDisallowInterceptTouchEvent(true);
                                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                                    xAtDown = event.getX(); // 터치 시작지점 x좌표 저장
                                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                                    xAtUp = event.getX(); // 터치 끝난지점 x좌표 저장

                                    if (xAtUp < xAtDown) {
                                        // 왼쪽 방향 에니메이션 지정
                                        viewFlipper.setInAnimation(AnimationUtils.loadAnimation(getContext(),
                                                R.anim.center_in_left));
                                        viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(getContext(),
                                                R.anim.center_out_left));

                                        // 다음 view 보여줌
                                        viewFlipper.showNext();
                                    } else if (xAtUp > xAtDown) {
                                        // 오른쪽 방향 에니메이션 지정
                                        viewFlipper.setInAnimation(AnimationUtils.loadAnimation(getContext(),
                                                R.anim.center_in_right));
                                        viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(getContext(),
                                                R.anim.center_out_right));
                                        // 전 view 보여줌
                                        viewFlipper.showPrevious();
                                    }
                                }
                                return true;
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<List<NoticeDTO>> call, Throwable t) {

            }
        });

        achv_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Dragger_QuizMake.class);
                startActivity(intent);
            }
        });

        camera_b.setOnClickListener(new View.OnClickListener() {
            String playerid = profile.getId();


            @Override
            public void onClick(View v) {
                Log.d("게임아이디frag",playerid);
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                Intent intent = new Intent(getActivity(),GameActivity.class);
                intent.putExtra("playerid",playerid);
                getActivity().startActivityForResult(intent,0);
            }
        });
        map_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                final Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() { // Thread 로 작업할 내용을 구현
                        Looper.prepare();
                        progressON();
                        Log.d("쓰레드 시작","시작");
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(getActivity(),GooglemapsActivity.class);
                                startActivity(intent);
                            }
                        });
                        Looper.loop();
                        handler.getLooper().quit();
                    }
                });
                t.start(); // 쓰레드 시작i
            }
        });
        tuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                    ((Home_Main) getActivity()).spotLightExcute(0);
            }
        });


        return layout;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        final String playerid=profile.getId();
        String ip =ipm.getip();
        Log.d("유니티 통신완료 ID : ",playerid);

        if(requestCode == 0){
            Log.d("유니티 리퀘스트코드 : ",""+requestCode);
            if(resultCode == RESULT_OK) {
                int question_code = data.getIntExtra("got",0);
                Log.d("유니티","Q_code"+question_code);
                networkClient = NetworkClient.getInstance(ip);
                networkClient.checkplayer(playerid, question_code,new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        Log.d("유니티 통신완료 재확인 ID : ",playerid);
                        switch (response.code()){
                            case 200:
                                int check = response.body();
                                Log.d("확인","check"+check);
                                if (check == 1) {  //Responce의값이 1일 때 새 문제 획득
                                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                                    alert.setOnKeyListener(new DialogInterface.OnKeyListener() {
                                        @Override
                                        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                                            Intent intent = new Intent(getContext(),Home_Main.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                            getActivity().overridePendingTransition(0, 0);
                                            startActivity(intent);
                                            getActivity().finish();
                                            dialog.dismiss();     //닫기
                                            return false;
                                        }
                                    });
                                    alert.setPositiveButton(getResources().getString(R.string.dialog_ok), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(getActivity(),Home_Main.class);
                                            startActivity(intent);
                                            getActivity().finish();
                                            dialog.dismiss();     //닫기
                                        }
                                    });
                                    alert.setCancelable(false);
                                    alert.setMessage(R.string.새문제);
                                    alert.show();
                                } else if (check == 0) { //Responce값이 0일 때 이미 가지고 있는 문제
                                    Log.d("확인"," "+check);
                                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                                    alert.setPositiveButton(getResources().getString(R.string.dialog_ok), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            dialog.dismiss();     //닫기
                                        }
                                    });
                                    alert.setMessage(R.string.이미가지고있는문제);
                                    alert.show();
                                }
                                else if (check == 2){ //본인이 낸 퀴즈일 경우
                                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                                    alert.setPositiveButton(getResources().getString(R.string.dialog_ok), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(getContext(),Home_Main.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                            getActivity().overridePendingTransition(0, 0);
                                            startActivity(intent);
                                            getActivity().finish();

                                            dialog.dismiss();     //닫기
                                        }
                                    });
                                    alert.setMessage(R.string.you_can_not_get);
                                    alert.show();
                                }


                                break;
                            default:
                                Log.e("TAG", "다른 아이디");
                                Toast toast = Toast.makeText(getActivity(), getResources().getString(R.string.please_check_id), Toast.LENGTH_LONG);
                                toast.show();
                                break;
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        Log.e("ACC","s?? " + t.getMessage());

                    }
                });


            }


        }


    }

    public void ViewCreater(String contents, String sDate, String sDate2) { //viewfliper에 추가할 뷰를 생성해주는 함수

        LinearLayout view = new LinearLayout(getContext());
        view.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.MATCH_PARENT,0.3f);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.MATCH_PARENT,0.7f);

        TextView tv_sDate = new TextView(getContext());
        tv_sDate.setLayoutParams(layoutParams);
        tv_sDate.setText(sDate+ '\n' + sDate2);
        tv_sDate.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        tv_sDate.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
        tv_sDate.setTextColor(Color.parseColor("#000000"));
        view.addView(tv_sDate);

        TextView tv_content = new TextView(getContext());
        tv_content.setLayoutParams(layoutParams2);
        tv_content.setText(contents);
        tv_content.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        tv_content.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
        tv_content.setTextColor(Color.parseColor("#000000"));
        view.addView(tv_content);
        filp_list.add(view);
    }


}