package com.hbag.seoulhang.home_package.home_fragment;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hbag.seoulhang.R;

import java.util.ArrayList;

/**
 * Created by cwh62 on 2017-05-10.
 */

public class ExpandableListViewAdapter extends BaseExpandableListAdapter{
    private Context _context;
    private ArrayList<MyData_G> _listDataHeader; // header titles
    // child data in format of header title, child title
    private ArrayList<ArrayList<MyData_H>> _listDataChild;
    private ViewHolder viewHolder = null;

    public ExpandableListViewAdapter(Context context, ArrayList<MyData_G> listDataHeader, ArrayList<ArrayList<MyData_H>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    //차일드 뷰 반환
    @Override
    public MyData_H getChild(int groupPosition, int childPosititon) {
        return _listDataChild.get(groupPosition).get(childPosititon);
    }


    //차일드 뷰 ID 반환
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    //차일드 뷰 생성(각 차일드 뷰의 (ROW)
    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {


        if (convertView == null) {

            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_info_c, null);
        }

        Methods methods = new Methods();

        TextView progress = (TextView)convertView.findViewById(R.id.progress);
        ProgressBar progressBar = (ProgressBar)convertView.findViewById(R.id.progressBar);
        final ExpandableListView exl = _listDataChild.get(groupPosition).get(childPosition).exp;
        final TextView content = (TextView)convertView.findViewById(R.id.content);
        final LinearLayout background = (LinearLayout)convertView.findViewById(R.id.back_rate);
        content.setMovementMethod(ScrollingMovementMethod.getInstance());
        content.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (content.canScrollVertically(1)||content.canScrollVertically(-1)) {
                    if (event.getAction() == MotionEvent.ACTION_UP)
                        exl.requestDisallowInterceptTouchEvent(false);
                    else
                        exl.requestDisallowInterceptTouchEvent(true);
                }
                return false;
            }
        });


        background.setBackgroundResource(methods.imageSelector2(_listDataHeader.get(groupPosition).getName()));

        progress.setText(_listDataChild.get(groupPosition).get(childPosition).getProgress());
        progressBar.setProgress(_listDataChild.get(groupPosition).get(childPosition).getProgressbar());
        content.setText(_listDataChild.get(groupPosition).get(childPosition).getContent());

        progressBar.getIndeterminateDrawable().setColorFilter(
                Color.parseColor("#FFFFD700"),
                PorterDuff.Mode.SCREEN);


        return convertView;
    }

    //차일드 뷰의 사이즈 반환
    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    //그룹 포지션 반환
    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    //그룹 사이즈를 반환
    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    //그룹 ID를 반환
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    //그룹 뷰 생성(그룹 각 뷰의 ROW)
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_info_p, null);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        TextView text_p = (TextView) convertView.findViewById(R.id.parent);
        text_p.setTypeface(null, Typeface.BOLD);
        text_p.setText(_listDataHeader.get(groupPosition).getName());

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    //뷰홀더 클래스 생성
    class ViewHolder {
        public TextView content;
    }

}


class MyData_H{
    public String progress;
    public int progressbar;
    public String content;
    public ExpandableListView exp;
    public MyData_H(String progress,int progressbar,String content,ExpandableListView exp){
        this.progress = progress;
        this.progressbar = progressbar;
        this.content = content;
        this.exp = exp;
    }
    public String getProgress(){return progress;}
    public int getProgressbar(){return progressbar;}
    public String getContent(){return content;}
}

class MyData_G{
    public String name;
    public MyData_G(String name){
        this.name = name;
    }
    public String getName(){return name;}
}


