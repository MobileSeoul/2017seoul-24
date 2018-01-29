package com.hbag.seoulhang.home_package.home_fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.getkeepsafe.relinker.elf.Elf;
import com.hbag.seoulhang.R;

import java.util.ArrayList;

/**
 * Created by cwh62 on 2017-05-10.
 */

public class RecyclerAdapter_I extends RecyclerView.Adapter<RecyclerAdapter_I.ViewHoler> {
    private ArrayList<MyData_I> mDataset;
    View view;

    public static class ViewHoler extends RecyclerView.ViewHolder{
        public TextView quiz_num;
        public TextView quiz_region;
        public TextView quiz;
        public LinearLayout item_lay;


        public ViewHoler(View view){
            super(view);
            quiz_num = (TextView)view.findViewById(R.id.quiz_num);
            quiz_region = (TextView)view.findViewById(R.id.quiz_region);
            quiz = (TextView)view.findViewById(R.id.quiz);
            item_lay = (LinearLayout)view.findViewById(R.id.item_lay);
        }


    }
    public RecyclerAdapter_I(ArrayList<MyData_I> myDataset){
        mDataset = myDataset;
    }
    @Override
    public RecyclerAdapter_I.ViewHoler onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate((R.layout.item_info),parent,false);
        view= v;
        RecyclerAdapter_I.ViewHoler vh = new RecyclerAdapter_I.ViewHoler(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final RecyclerAdapter_I.ViewHoler holder, final int position){
        holder.quiz_num.setText(mDataset.get(position).quiz_num);
        holder.quiz_region.setText(mDataset.get(position).quiz_region);
        holder.quiz.setText(mDataset.get(position).quiz);
        holder.quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(Frag_Quiz.activity);
                alert.setPositiveButton(view.getResources().getString(R.string.확인), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();     //닫기
                    }
                });
                alert.setMessage(mDataset.get(position).quiz);
                alert.show();
            }
        });



    }
    @Override
    public int getItemCount(){
        return mDataset.size();
    }

}

class MyData_I{
    public String quiz_num;
    public String quiz_region;
    public String quiz;
    public MyData_I(String quiz_num, String quiz_region,String quiz){
        this.quiz_num = quiz_num;
        this.quiz_region = quiz_region;
        this.quiz = quiz;
    }

}
