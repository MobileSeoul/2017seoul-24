package com.hbag.seoulhang.home_package.home_fragment;


import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;

import android.text.Layout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hbag.seoulhang.R;
import com.hbag.seoulhang.appbase_package.RealmInit;

import java.util.ArrayList;


/**
 * Created by cwh62 on 2017-10-09.
 */

public class RecyclerAdapter_T extends RecyclerView.Adapter<RecyclerAdapter_T.ViewHoler>{
    private ArrayList<MyData_T> mDataset;


    public static class ViewHoler extends RecyclerView.ViewHolder{

        TextView question_name;
        ImageView background;
        TextView question_code;
        TextView visit_count;
        TextView region_name;


        public ViewHoler(View view){
            super(view);
            question_name = (TextView)view.findViewById(R.id.tv_top);
            question_code = (TextView)view.findViewById(R.id.tv_num);
            visit_count = (TextView)view.findViewById(R.id.tv_visit);
            region_name = (TextView)view.findViewById(R.id.tv_region);
            background = (ImageView)view.findViewById(R.id.iv_topback);


            Context sContext = view.getContext();

            DisplayMetrics metrics = new DisplayMetrics();
            WindowManager mgr = (WindowManager)sContext.getSystemService(Context.WINDOW_SERVICE);
            mgr.getDefaultDisplay().getMetrics(metrics);

            int dpi = metrics.densityDpi;

            Log.d("DPI",""+dpi);

            switch (dpi){ //DPI별 뷰 크기 설정
                case 240:
                    question_name.setTextSize(TypedValue.COMPLEX_UNIT_DIP,10);
                    question_code.setTextSize(TypedValue.COMPLEX_UNIT_DIP,10);
                    region_name.setTextSize(TypedValue.COMPLEX_UNIT_DIP,10);
                    visit_count.setTextSize(TypedValue.COMPLEX_UNIT_DIP,8);
                    break;
                case 320:
                    question_name.setTextSize(TypedValue.COMPLEX_UNIT_DIP,12);
                    question_code.setTextSize(TypedValue.COMPLEX_UNIT_DIP,12);
                    region_name.setTextSize(TypedValue.COMPLEX_UNIT_DIP,12);
                    visit_count.setTextSize(TypedValue.COMPLEX_UNIT_DIP,10);
                    break;
                case 480:
                    question_name.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14);
                    question_code.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14);
                    region_name.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14);
                    visit_count.setTextSize(TypedValue.COMPLEX_UNIT_DIP,12);
                    break;
                case 560:
                    question_name.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14);
                    question_code.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14);
                    region_name.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14);
                    visit_count.setTextSize(TypedValue.COMPLEX_UNIT_DIP,12);
                    break;
                case 640:
                    question_name.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16);
                    question_code.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16);
                    region_name.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16);
                    visit_count.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14);
                    break;
            }






        }


    }
    public RecyclerAdapter_T(ArrayList<MyData_T> myDataset){
        mDataset = myDataset;
    }
    @Override
    public RecyclerAdapter_T.ViewHoler onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate((R.layout.item_topten),parent,false);
        ViewHoler vh = new ViewHoler(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHoler holder,int position){

        Methods methods = new Methods();

        holder.question_name.setText(mDataset.get(position).question_name);
        holder.question_code.setText(mDataset.get(position).question_code);
        holder.region_name.setText(" "+mDataset.get(position).region_name+" ");
        holder.visit_count.setText(mDataset.get(position).visit_count);
        int rev_size = mDataset.get(position).home_rev.getHeight();

        ViewGroup.LayoutParams params = holder.background.getLayoutParams();
        params.width = rev_size*7/10-5;
        params.height = rev_size*7/10-5;
        holder.background.setLayoutParams(params);        //DPI별로 이미지뷰 크기를 지정해준다.
        holder.background.setImageResource(methods.imageSelector(mDataset.get(position).region_name)); // 해당지역의 사진으로 변경
        holder.question_name.setMaxWidth(params.width);



    }
    @Override
    public int getItemCount(){
        return mDataset.size();
    }

}

class MyData_T{
    public String question_code;
    public String question_name;
    public String region_name;
    public String visit_count;
    public RecyclerView home_rev;

    public MyData_T(String question_code, String question_name, String region_name, String visit_count, RecyclerView home_rev){
        this.question_code = question_code;
        this.question_name = question_name;
        this.region_name = region_name;
        this.visit_count = visit_count;
        this.home_rev = home_rev;
    }
}
