package com.frontierfield.ganreco;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by kkarimu on 2018/06/23.
 */

public class d5taikai extends AppCompatActivity implements View.OnClickListener {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.d5);

        LinearLayout fotterHome = findViewById(R.id.homesetFotterD5);
        LinearLayout fotterInput = findViewById(R.id.inputsetFotterD5);
        LinearLayout fotterYoyaku = findViewById(R.id.yoyakusetFotterD5);
        LinearLayout fotterHospital = findViewById(R.id.hospitalSetFotterD5);
        LinearLayout fotterMypage = findViewById(R.id.mypagesetFotterD5);
        ImageView bell = findViewById(R.id.button_bell_ivD5);
        ImageView backBtnheader = findViewById(R.id.shape_ivD5);

        TextView taikaiBtn = findViewById(R.id.taikaiD5);

        fotterHome.setOnClickListener(this);
        fotterInput.setOnClickListener(this);
        fotterYoyaku.setOnClickListener(this);
        fotterHospital.setOnClickListener(this);
        fotterMypage.setOnClickListener(this);
        bell.setOnClickListener(this);
        backBtnheader.setOnClickListener(this);
        taikaiBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.homesetFotterD5) {
            startActivity(new Intent(this,B1_2_GanrecoMain.class));
            finish();
        }else if(i == R.id.inputsetFotterD5){

        }else if (i == R.id.yoyakusetFotterD5){

        }else if(i == R.id.hospitalSetFotterD5){

        }else if(i == R.id.mypagesetFotterD5){
            startActivity(new Intent(this,c1_2mypage.class));
            finish();
        }else if(i == R.id.shape_ivD5) {
            startActivity(new Intent(this, c1_2mypage.class));
            finish();
        }else if(i == R.id.button_bell_ivD5){
            //d3 help
        }else if(i == R.id.taikaiD5){
            callMailer();
        }
    }
    private void callMailer() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SENDTO);

        intent.setType("text/plain");
        intent.setData(Uri.parse("mailto:xxx@yyy.zzz"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "がんレコ　退会手続き");
        intent.putExtra(Intent.EXTRA_TEXT, "[退会手続き]\n " +
                "以下のフォーマットに従ってアンケートにご協力ください。\n " +
                "■がんレコの改善点\n\n" +
                "がんレコをご利用していただきありがとうございました。\n");
        startActivity(Intent.createChooser(intent, null));
    }
}
