package com.hbag.seoulhang.joinmanage_package;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hbag.seoulhang.home_package.Home_Main;
import com.hbag.seoulhang.R;
import com.hbag.seoulhang.joinmanage_package.login_package.UserProfileData_singleton;
import com.hbag.seoulhang.joinmanage_package.tutorial_package.tuto_a;
import com.hbag.seoulhang.joinmanage_package.tutorial_package.tuto_b;
import com.hbag.seoulhang.joinmanage_package.tutorial_package.tuto_c;
import com.hbag.seoulhang.joinmanage_package.tutorial_package.tuto_d;

import me.relex.circleindicator.CircleIndicator;

public class TutorialActivity extends AppCompatActivity {
    ViewPager tpager;
    CircleIndicator indicator;
    Button skip;
    pagerAdapter mAdapter = new pagerAdapter((getSupportFragmentManager()));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        tpager = (ViewPager)findViewById(R.id.tutovp);
        indicator = (CircleIndicator)findViewById(R.id.indicator);
        skip = (Button)findViewById(R.id.skip_b);

        int infoFirst = 1;
        final SharedPreferences a = getSharedPreferences("a",MODE_PRIVATE);
        SharedPreferences.Editor editor = a.edit();
        editor.putInt("First",infoFirst);
        editor.apply();



        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TutorialActivity.this, Home_Main.class);
                startActivity(intent);
                finish();
            }
        });

        tpager.setAdapter(mAdapter);
        indicator.setViewPager(tpager);
        tpager.setCurrentItem(0);
        mAdapter.registerDataSetObserver(indicator.getDataSetObserver());

        tpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (tpager.getCurrentItem()){
                    case 0:
                        skip.setBackgroundResource(R.drawable.bt_skip);
                        break;
                    case 1:
                        skip.setBackgroundResource(R.drawable.bt_skip);
                        break;
                    case 2:
                        skip.setBackgroundResource(R.drawable.bt_skip);
                        break;
                    case 3:
                        skip.setBackgroundResource(R.drawable.bt_skip_start);
                        break;
                    default:
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }




    private class pagerAdapter extends FragmentStatePagerAdapter
    {
        public pagerAdapter(android.support.v4.app.FragmentManager fm)
        {
            super(fm);
        }
        @Override
        public android.support.v4.app.Fragment getItem(int position)
        {
            switch(position)
            {
                case 0:
                    return new tuto_a();
                case 1:
                    return new tuto_b();
                case 2:
                    return new tuto_c();
                case 3:
                    return new tuto_d();
                default:
                    return null;
            }
        }
        @Override
        public int getCount()
        {
            return 4;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                return true;
        }return super.onKeyDown(keyCode, event);}  //BACK버튼 비활성화

}


