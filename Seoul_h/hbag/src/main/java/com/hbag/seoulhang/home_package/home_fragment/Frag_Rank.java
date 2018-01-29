package com.hbag.seoulhang.home_package.home_fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hbag.seoulhang.R;
import com.hbag.seoulhang.model.retrofit.RankDTO;
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

public class Frag_Rank extends Fragment
{

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<MyData_R> myDataset;
    UserProfileData_singleton profile;
    NetworkClient networkClient;
    LinearLayout my_rank_lay;
    private List<RankDTO> rankings;
    Ipm ipm;

    TextView rank;
    TextView name;
    TextView mypoint;
    ImageView mygrade;

    public Frag_Rank()
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
        profile = UserProfileData_singleton.getInstance();
        ipm = new Ipm();
        final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.frag_rank,container, false);
        rank = (TextView)layout.findViewById(R.id.my_rank);
        name = (TextView) layout.findViewById(R.id.my_name);
        mypoint = (TextView) layout.findViewById(R.id.my_point);
        mygrade = (ImageView) layout.findViewById(R.id.my_grade);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.rvr);
        my_rank_lay = (LinearLayout) layout.findViewById(R.id.my_rank_lay);
        final String loginid = profile.getId();
        String ip = ipm.getip();
        Log.e("확인",loginid);


        networkClient = NetworkClient.getInstance(ip);
        networkClient.getranking(loginid, new Callback<List<RankDTO>>() {
            @Override
            public void onResponse(Call<List<RankDTO>> call, Response<List<RankDTO>> response) {
                switch (response.code()) {
                    case 200:
                        Log.d("퀴즈", "어댑터 세팅");
                        rankings = response.body();

                        mRecyclerView.setHasFixedSize(true);
                        mLayoutManager = new LinearLayoutManager(getActivity());
                        mRecyclerView.setLayoutManager(mLayoutManager);
                        myDataset=new ArrayList<>();
                        mAdapter = new RecyclerAdapter_R(myDataset);
                        mRecyclerView.setAdapter(mAdapter);

                        RankDTO my_rankDTO = rankings.get(0);
                        String my_rank_num = Integer.toString(my_rankDTO.getRank());
                        String my_playerid = my_rankDTO.getPlayerid();
                        String my_playername = my_rankDTO.getPlayername();
                        String my_point = Integer.toString(my_rankDTO.getPoint());
                        String my_grade = my_rankDTO.getGrade();
                        Log.d("계급",my_grade);
                        myDataset.add(new MyData_R(my_rank_num, my_playerid, my_playername, my_point,my_grade));
                        rank.setText(myDataset.get(0).rank);
                        name.setText(myDataset.get(0).playername);
                        mypoint.setText(myDataset.get(0).point);  //자신의 정보는 따로 세팅한다.
                        switch (myDataset.get(0).grade){
                            case "Unrank":
                                mygrade.setImageResource(R.drawable.tier_unrank);
                                break;
                            case "Bronze":
                                mygrade.setImageResource(R.drawable.tier_bronze);
                                break;
                            case "Silver":
                                mygrade.setImageResource(R.drawable.tier_silver);
                                break;
                            case "Gold":
                                mygrade.setImageResource(R.drawable.tier_gold);
                                break;
                            case "Platinum":
                                mygrade.setImageResource(R.drawable.tier_platinum);
                                break;
                            case "Diamond":
                                mygrade.setImageResource(R.drawable.tier_diamond);
                                break;
                            case "Master":
                                mygrade.setImageResource(R.drawable.tier_master);
                                break;
                            case "Challenger":
                                mygrade.setImageResource(R.drawable.tier_challenger);
                                break;
                        }
                        int rank_int = Integer.parseInt(myDataset.get(0).rank);
                        if(rank_int==1) {
                            rank.setText("");
                            rank.setBackgroundResource(R.drawable.num_1);
                        }
                        else if(rank_int==2) {
                            rank.setText("");
                            rank.setBackgroundResource(R.drawable.num_2);
                        }
                        else if(rank_int==3) {
                            rank.setText("");
                            rank.setBackgroundResource(R.drawable.num_3);
                        }


                        myDataset.remove(0); //본 랭크 데이터를 전달하기 위해 리스트에 존재하는 자신의 정보를 제거한다.


                        for (int i = 1; i < rankings.size(); i++) {
                            RankDTO rankDTO = rankings.get(i);

                            String rank_num = Integer.toString(rankDTO.getRank());
                            String playerid = rankDTO.getPlayerid();
                            String playername = rankDTO.getPlayername();
                            String point = Integer.toString(rankDTO.getPoint());
                            String grade = rankDTO.getGrade();
                            myDataset.add(new MyData_R(rank_num, playerid, playername, point,grade));

                        }



                        break;

                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<RankDTO>> call, Throwable t) {
                Log.e("ACC", "s?? " + t.getMessage());

            }
        });



        return layout;
    }


}