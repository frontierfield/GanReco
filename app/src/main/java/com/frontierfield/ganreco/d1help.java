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

public class d1help extends AppCompatActivity implements View.OnClickListener {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.d1);

        LinearLayout fotterHome = findViewById(R.id.homesetFotterD1);
        LinearLayout fotterInput = findViewById(R.id.inputsetFotterD1);
        LinearLayout fotterYoyaku = findViewById(R.id.yoyakusetFotterD1);
        LinearLayout fotterHospital = findViewById(R.id.hospitalSetFotterD1);
        LinearLayout fotterMypage = findViewById(R.id.mypagesetFotterD1);
        ImageView bell = findViewById(R.id.button_bell_ivD1);
        ImageView backBtnheader = findViewById(R.id.shape_ivD1);

        TextView toiawaseBtn = findViewById(R.id.toiawaseD1);

        fotterHome.setOnClickListener(this);
        fotterInput.setOnClickListener(this);
        fotterYoyaku.setOnClickListener(this);
        fotterHospital.setOnClickListener(this);
        fotterMypage.setOnClickListener(this);
        bell.setOnClickListener(this);
        backBtnheader.setOnClickListener(this);
        toiawaseBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.homesetFotterD1) {
            startActivity(new Intent(this,B1_2_GanrecoMain.class));
            finish();
        }else if(i == R.id.inputsetFotterD1){

        }else if (i == R.id.yoyakusetFotterD1){

        }else if(i == R.id.hospitalSetFotterD1){

        }else if(i == R.id.mypagesetFotterD1){
            startActivity(new Intent(this,c1_2mypage.class));
            finish();
        }else if(i == R.id.shape_ivD1) {
            startActivity(new Intent(this, c1_2mypage.class));
            finish();
        }else if(i == R.id.button_bell_ivD1){
            //d3 help
        }else if(i == R.id.toiawaseD1){
            callMailer();
        }
    }
    private void callMailer() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SENDTO);

        intent.setType("text/plain");
        intent.setData(Uri.parse("mailto:xxx@yyy.zzz"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "がんレコ　お問い合わせ");
        intent.putExtra(Intent.EXTRA_TEXT, "[お問い合わせ]\n " +
                "以下のフォーマットに従ってお問い合わせ内容を記入しメールを送信してください。\n " +
                "■お問い合わせ内容\n\n" +
                "■お名前\n\n" +
                "■その他ご要望など\n\n" +
                "今回の情報を元に、通常３～５営業日以内にご連絡させていただきますのでお待ちくださいませ。\n" +
                "ご不便をお掛けして申し訳ありませんが、引き続きこのアプリをお楽しみください！");
        startActivity(Intent.createChooser(intent, null));
    }
}
