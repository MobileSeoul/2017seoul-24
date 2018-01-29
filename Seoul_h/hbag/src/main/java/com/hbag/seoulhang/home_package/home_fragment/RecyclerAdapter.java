package com.hbag.seoulhang.home_package.home_fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hbag.seoulhang.R;
import com.hbag.seoulhang.home_package.Home_Main;

import java.io.Serializable;
import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * Created by cwh62 on 2017-05-03.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private ArrayList<MyData_Q> mDataset;
    public Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView quiz_num;
        private TextView quiz_region;
        private TextView tap;



        public ViewHolder(View view) {
            super(view);
            quiz_num = (TextView) view.findViewById(R.id.quiz_num);
            quiz_region = (TextView) view.findViewById(R.id.quiz_region);
            tap = (TextView) view.findViewById(R.id.tap);
        }


    }

    public RecyclerAdapter(ArrayList<MyData_Q> myDataset,Context context) {
        mDataset = myDataset;
        mContext = context;

    }


    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quiz, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.quiz_num.setText(mDataset.get(position).text_num);
        holder.quiz_region.setText(mDataset.get(position).text_region);

        holder.tap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Dragger_Quiz.class);
                intent.putExtra("position",position);
                intent.putExtra("dataset",mDataset);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}

class MyData_Q implements Serializable{
    public String text_num;
    public String text_region;
    public int region_code;
    public String text_quiz;
    public String Answer_q;
    public String playerid;
    public String hint;
    public String q_type;
    public MyData_Q(String q_type,
                  String text_num,
                  String text_region,
                  int region_code,
                  String text_quiz,
                  String answer_q,
                  String playerid,
                  String hint){
        this.q_type = q_type;
        this.text_num = text_num;
        this.text_region = text_region;
        this.region_code = region_code;
        this.text_quiz = text_quiz;
        this.Answer_q = answer_q;
        this.playerid = playerid;
        this.hint = hint;
    }


}


