package com.frontierfield.ganreco;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class A1_A2_A3_WalkThrough extends AppCompatActivity implements ViewPager.OnPageChangeListener,View.OnClickListener {
    public final static int Page_Num=3;
    private ViewPager viewPager;
    private TextView textSkip;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a1_a2_a3_walkthrough);

        viewPager=(ViewPager)findViewById(R.id.walkthroughpager);
        A1_A2_A3_WalkThroughAdapter walkThroughAdapter= new A1_A2_A3_WalkThroughAdapter(getSupportFragmentManager());
        viewPager.setAdapter(walkThroughAdapter);
        viewPager.addOnPageChangeListener(this);

        TabLayout tabLayout=(TabLayout)findViewById(R.id.indicator);
        tabLayout.setupWithViewPager(viewPager,true);

        textSkip=(TextView)findViewById(R.id.skip);
        textSkip.setOnClickListener(this);
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {//スキップボタン表示するかどうか
        if(position==Page_Num-1){
            textSkip.setText("");
        }else{
            textSkip.setText("SKIP");
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.skip && viewPager.getCurrentItem() != Page_Num - 1) {
            viewPager.setCurrentItem(Page_Num, true);
        }
    }
}
