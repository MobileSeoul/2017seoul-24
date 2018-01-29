package com.hbag.seoulhang.home_package.home_fragment;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.hbag.seoulhang.R;
import com.hbag.seoulhang.appbase_package.BaseActivity;
import com.hbag.seoulhang.joinmanage_package.login_package.UserProfileData_singleton;
import com.hbag.seoulhang.model.retrofit.RateDTO;
import com.hbag.seoulhang.network.Ipm;
import com.hbag.seoulhang.network.NetworkClient;
import com.liuguangqiang.swipeback.SwipeBackActivity;
import com.liuguangqiang.swipeback.SwipeBackLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dragger_Achieve extends BaseActivity {

    public ExpandableListAdapter listAdapter;
    public ExpandableListView expListView;
    public ArrayList<MyData_G> groupData;
    private ArrayList<ArrayList<MyData_H>> childData;
    private List<RateDTO> rates;
    NetworkClient networkClient;
    UserProfileData_singleton profile;
    Ipm ipm;
    Methods methods;
    Button close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achieve_dragger);

        profile = UserProfileData_singleton.getInstance();
        String sid = profile.getId();

        networkClient = NetworkClient.getInstance();
        expListView = (ExpandableListView)findViewById(R.id.exl); // 확장 리스트뷰를 가져온다.
        ipm = new Ipm();
        close = (Button)findViewById(R.id.bt_close);



        networkClient.getrate(sid, new Callback<List<RateDTO>>() {
            @Override
            public void onResponse(Call<List<RateDTO>> call, Response<List<RateDTO>> response) {
                switch (response.code()) {
                    case 200:
                        Log.d("인포", "어댑터 세팅");
                        rates = response.body();
                        groupData = new ArrayList<>();
                        childData = new ArrayList<>();
                        methods = new Methods();

                        for (int i = 0; i < rates.size(); i++) {

                            RateDTO rateDTO = rates.get(i);

                            int rates_q = rateDTO.getRate();
                            String regionname = rateDTO.getRegionname();
                            String exp = rateDTO.getExplain();
                            int regioncode = rateDTO.getRegioncode();
                            Log.d("리즌",""+regioncode);
                            // 그룹 생성
                            groupData.add(new MyData_G(regionname));
                            childData.add(new ArrayList<MyData_H>());
                            // 차일드 생성
                            childData.get(i).add(new MyData_H(getResources().getString(R.string.달성률) + " " + rates_q + "%", rates_q, exp, expListView));
                            Log.e("확인",""+rates_q);

                        }
                        listAdapter = new ExpandableListViewAdapter(Dragger_Achieve.this, groupData, childData);
                        expListView.setAdapter(listAdapter);// 리스트어댑터 세팅

                        break;

                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<RateDTO>> call, Throwable t) {
                Log.e("ACC", "s?? " + t.getMessage());

            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
