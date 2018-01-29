package com.hbag.seoulhang.home_package.home_fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hbag.seoulhang.R;
import com.hbag.seoulhang.model.retrofit.InventoryDTO;
import com.hbag.seoulhang.model.retrofit.RateDTO;
import com.hbag.seoulhang.network.Ipm;
import com.hbag.seoulhang.network.NetworkClient;
import com.hbag.seoulhang.joinmanage_package.login_package.UserProfileData_singleton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by cwh62 on 2017-04-11.
 */

public class Frag_Info extends Fragment
{
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    UserProfileData_singleton profile;
    ArrayList<MyData_I> myDataset;
    private List<InventoryDTO> infos;
    NetworkClient networkClient;
    TextView info_name;
    TextView info_point;
    TextView info_hint;
    TextView info_email;
    TextView info_makequiz;
    TextView view_rate;
    ImageView info_grade;
    TextView no_quiz;
    Ipm ipm;


    public Frag_Info()
    {
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.frag_info,container, false);
        profile = UserProfileData_singleton.getInstance();
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.rvi);
        ipm = new Ipm();
        final String loginid = profile.getId();
        info_name = (TextView)layout.findViewById(R.id.info_name);
        info_point = (TextView)layout.findViewById(R.id.info_point);
        info_hint = (TextView)layout.findViewById(R.id.info_hint);
        info_email = (TextView)layout.findViewById(R.id.info_email);
        info_makequiz = (TextView)layout.findViewById(R.id.info_makequiz);
        info_grade = (ImageView)layout.findViewById(R.id.info_grade);
        view_rate = (TextView) layout.findViewById(R.id.tv_view_rate);
        no_quiz = (TextView)layout.findViewById(R.id.no_quiz);
        String ip = ipm.getip();


        networkClient = NetworkClient.getInstance(ip);
        Log.d("인포",loginid);
        networkClient.getfinishitem(loginid, new Callback<List<InventoryDTO>>() {
            @Override
            public void onResponse(Call<List<InventoryDTO>> call, Response<List<InventoryDTO>> response) {
                switch (response.code()) {
                    case 200:
                        Log.d("인포", "어댑터 세팅");
                        infos = response.body();

                        mRecyclerView.setHasFixedSize(true);
                        mLayoutManager = new LinearLayoutManager(getActivity());
                        mRecyclerView.setLayoutManager(mLayoutManager);
                        myDataset=new ArrayList<>();
                        mAdapter = new RecyclerAdapter_I(myDataset);
                        mRecyclerView.setAdapter(mAdapter);

                        for (int i = 0; i < infos.size(); i++) {
                            InventoryDTO inventoryDTO = infos.get(i);

                            String qcode = Integer.toString(inventoryDTO.getQuestioncode());
                            String rname = inventoryDTO.getRegionname();
                            String q = inventoryDTO.getQuestion();
                            if (!qcode.equals("0"))
                                myDataset.add(new MyData_I(qcode, rname, q));
                        }

                        InventoryDTO check=infos.get(0);
                        if(check.getQuestioncode()==0&&check.getRegionname().equals("0")) {
                            no_quiz.setVisibility(View.VISIBLE);
                            mRecyclerView.setVisibility(View.GONE);
                        }
                        else{
                            mRecyclerView.setVisibility(View.VISIBLE);
                            no_quiz.setVisibility(View.GONE);
                        }

                        InventoryDTO inventoryDTO = infos.get(0);
                        String grade_i = inventoryDTO.getGrade();
                        String nickname = inventoryDTO.getNickname();
                        String point_i = getResources().getString(R.string.score)+Integer.toString(inventoryDTO.getPoint());
                        String hint_i = getResources().getString(R.string.Hint_info)+Integer.toString(inventoryDTO.getHint());
                        String email_i = inventoryDTO.getEmail();
                        String makequiz_i = getResources().getString(R.string.make_quiz_count,inventoryDTO.getMakequiz());


                        info_name.setText(nickname);
                        info_point.setText(point_i);
                        info_hint.setText(hint_i);
                        info_email.setText(email_i);
                        if(inventoryDTO.getMakequiz()>0) {
                            info_makequiz.setText(makequiz_i);
                        }
                        else if(inventoryDTO.getMakequiz()<=0){
                            info_makequiz.setText(getResources().getString(R.string.can_not_make_quiz));
                        }
                        switch (grade_i){
                            case "Unrank":
                                info_grade.setImageResource(R.drawable.tier_unrank);
                                break;
                            case "Bronze":
                                info_grade.setImageResource(R.drawable.tier_bronze);
                                break;
                            case "Silver":
                                info_grade.setImageResource(R.drawable.tier_silver);
                                break;
                            case "Gold":
                                info_grade.setImageResource(R.drawable.tier_gold);
                                break;
                            case "Platinum":
                                info_grade.setImageResource(R.drawable.tier_platinum);
                                break;
                            case "Diamond":
                                info_grade.setImageResource(R.drawable.tier_diamond);
                                break;
                            case "Master":
                                info_grade.setImageResource(R.drawable.tier_master);
                                break;
                            case "Challenger":
                                info_grade.setImageResource(R.drawable.tier_challenger);
                                break;
                        }
                        break;

                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<InventoryDTO>> call, Throwable t) {
                Log.e("ACC", "s?? " + t.getMessage());

            }
        });

        view_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Dragger_Achieve.class);
                startActivity(intent);
            }
        });


        return layout;
    }



}