package com.frontierfield.ganreco;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by kkarimu on 2018/06/25.
 */

public class b3_input extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b3);

        LinearLayout fotterHome = findViewById(R.id.homesetFotterB3);
        LinearLayout fotterInput = findViewById(R.id.inputsetFotterB3);
        LinearLayout fotterYoyaku = findViewById(R.id.yoyakusetFotterB3);
        LinearLayout fotterHospital = findViewById(R.id.hospitalSetFotterB3);
        LinearLayout fotterMypage = findViewById(R.id.mypagesetFotterB3);
        ImageView bell = findViewById(R.id.button_bell_ivB3);

        LinearLayout schedule = findViewById(R.id.schedulesetB3);

        fotterHome.setOnClickListener(this);
        fotterInput.setOnClickListener(this);
        fotterYoyaku.setOnClickListener(this);
        fotterHospital.setOnClickListener(this);
        fotterMypage.setOnClickListener(this);
        bell.setOnClickListener(this);
        schedule.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.homesetFotterB3) {
            startActivity(new Intent(this,b1_2mainmenu.class));
            finish();
        }else if(i == R.id.inputsetFotterB3){
            //startActivity(new Intent(this,b3_input.class));
            //finish();
        }else if (i == R.id.yoyakusetFotterB3){
            startActivity(new Intent(this,e_f_g_h_mainmenu.class));
            finish();
        }else if(i == R.id.hospitalSetFotterB3){

        }else if(i == R.id.mypagesetFotterB3){
            startActivity(new Intent(this,c1_2mypage.class));
            finish();
        }else if(i == R.id.button_bell_ivB3){
            //d3 help
        }else if(i == R.id.schedulesetB3){
            startActivity(new Intent(this,e_f_g_h_mainmenu.class));
            startActivity(new Intent(this,E3_Input.class));
            finish();
        }
    }
}
