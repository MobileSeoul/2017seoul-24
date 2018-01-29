package com.hbag.seoulhang.home_package.home_fragment;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hbag.seoulhang.R;

import java.util.ArrayList;

/**
 * Created by cwh62 on 2017-05-08.
 */

public class RecyclerAdapter_R extends RecyclerView.Adapter<RecyclerAdapter_R.ViewHoler>{
    private ArrayList<MyData_R> mDataset;

    public static class ViewHoler extends RecyclerView.ViewHolder{
        TextView playercode;
        TextView playername;
        TextView rank;
        TextView point;
        ImageView grade;
        LinearLayout rank_lay;

        public ViewHoler(View view){
            super(view);
            playername = (TextView)view.findViewById(R.id.playernick);
            rank = (TextView)view.findViewById(R.id.rank);
            point = (TextView)view.findViewById(R.id.point);
            grade = (ImageView) view.findViewById(R.id.grade);
            rank_lay = (LinearLayout)view.findViewById(R.id.rank_lay);
        }


    }
    public RecyclerAdapter_R(ArrayList<MyData_R> myDataset){
        mDataset = myDataset;
    }
    @Override
    public RecyclerAdapter_R.ViewHoler onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate((R.layout.item_rank),parent,false);
        ViewHoler vh = new ViewHoler(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHoler holder,int position){
        holder.rank.setText(mDataset.get(position).rank);
        holder.playername.setText(mDataset.get(position).playername);
        holder.point.setText(mDataset.get(position).point);
        switch (mDataset.get(position).grade){
            case "Unrank":
                holder.grade.setImageResource(R.drawable.tier_unrank);
                break;
            case "Bronze":
                holder.grade.setImageResource(R.drawable.tier_bronze);
                break;
            case "Silver":
                holder.grade.setImageResource(R.drawable.tier_silver);
                break;
            case "Gold":
                holder.grade.setImageResource(R.drawable.tier_gold);
                break;
            case "Platinum":
                holder.grade.setImageResource(R.drawable.tier_platinum);
                break;
            case "Diamond":
                holder.grade.setImageResource(R.drawable.tier_diamond);
                break;
            case "Master":
                holder.grade.setImageResource(R.drawable.tier_master);
                break;
            case "Challenger":
                holder.grade.setImageResource(R.drawable.tier_challenger);
                break;
        }
        int rank_int = Integer.parseInt(mDataset.get(position).rank);
        if(rank_int==1) {
            holder.rank.setText("");
            holder.rank.setBackgroundResource(R.drawable.num_1);
        }
        else if(rank_int==2) {
            holder.rank.setText("");
            holder.rank.setBackgroundResource(R.drawable.num_2);
        }
        else if(rank_int==3) {
            holder.rank.setText("");
            holder.rank.setBackgroundResource(R.drawable.num_3);
        }

    }
    @Override
    public int getItemCount(){
        return mDataset.size();
    }

}

class MyData_R{
    public String rank;
    public String playercode;
    public String playername;
    public String point;
    public String grade;
    public MyData_R(String rank, String playercode,String playername,String point,String grade){
        this.rank = rank;
        this.playercode = playercode;
        this.playername = playername;
        this.point = point;
        this.grade = grade;
    }
}

