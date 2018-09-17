package com.frontierfield.ganreco;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by kkarimu on 2018/06/14.
 */

public class c1_2mypage extends AppCompatActivity implements View.OnClickListener {
    TextView username;
    TextView address;
    TextView birth;
    ImageView userAvater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.c1_2);

        LinearLayout fotterHome = findViewById(R.id.homesetFotterE1_2_3_4);
        LinearLayout fotterInput = findViewById(R.id.inputsetFotterE1_2_3_4);
        LinearLayout fotterYoyaku = findViewById(R.id.yoyakusetFotterE1_2_3_4);
        LinearLayout fotterHospital = findViewById(R.id.hospitalSetFotterE1_2_3_4);
        LinearLayout fotterMypage = findViewById(R.id.mypagesetFotterE1_2_3_4);
        ImageView bell = findViewById(R.id.button_bell_ivE1_2_3_4);
        ImageView editUser = findViewById(R.id.button_edit_userE1_2_3_4);
        ImageView backBtnheader = findViewById(R.id.shape_ivE1_2_3_4);

        TextView helpBtn = findViewById(R.id.helpC1_2);
        TextView settingBtn = findViewById(R.id.settingC1_2);
        TextView otoiawaseBtn = findViewById(R.id.toiawaseC1_2);
        TextView logoutBtn = findViewById(R.id.logoutC1_2);
        TextView taikaiBtn = findViewById(R.id.taikaiC1_2);

        username = findViewById(R.id.username);
        address = findViewById(R.id.address);
        birth = findViewById(R.id.birth);
        userAvater = findViewById(R.id.userAvater);

        fotterHome.setOnClickListener(this);
        fotterInput.setOnClickListener(this);
        fotterYoyaku.setOnClickListener(this);
        fotterHospital.setOnClickListener(this);
        fotterMypage.setOnClickListener(this);
        bell.setOnClickListener(this);
        editUser.setOnClickListener(this);
        backBtnheader.setOnClickListener(this);

        helpBtn.setOnClickListener(this);
        settingBtn.setOnClickListener(this);
        otoiawaseBtn.setOnClickListener(this);
        logoutBtn.setOnClickListener(this);
        taikaiBtn.setOnClickListener(this);


    }
    @Override
    protected void onResume() {
        super.onResume();
        //User profile Update
        user_profile up = new user_profile();
        if(up.lastName != null && up.firstName != null && up.year_Index != -1 && up.month_Index != -1 && up.day_Index != -1) {
            Global_Util gu = new Global_Util();
            username.setText(up.lastName + " " + up.firstName);
            address.setText(up.address);
            birth.setText(
                    gu.aYear.get(up.year_Index).toString() + "/" + gu.aMonth[up.month_Index] + "/" + gu.aDay[up.day_Index]
            );
            if(up.sex_Index != -1){
                //avater 変更
                if(up.sex_Index == 0){
                    userAvater.setImageResource(R.drawable.icon_father);
                }else{
                    userAvater.setImageResource(R.drawable.icon_mother);
                }
            }
        }

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.homesetFotterE1_2_3_4) {
            startActivity(new Intent(this,b1_2mainmenu.class));
            finish();
        }else if(i == R.id.inputsetFotterE1_2_3_4){

        }else if (i == R.id.yoyakusetFotterE1_2_3_4){

        }else if(i == R.id.hospitalSetFotterE1_2_3_4){

        }else if(i == R.id.mypagesetFotterE1_2_3_4){
            //startActivity(new Intent(this,c1_2mypage.class));
            //finish();
        }else if(i == R.id.button_edit_userE1_2_3_4){
            startActivity(new Intent(this,c3_edituser.class));
        }else if(i == R.id.shape_ivE1_2_3_4) {
            startActivity(new Intent(this, b1_2mainmenu.class));
            finish();
        }else if(i == R.id.button_bell_ivD1){
                //d3 help
        }else if(i == R.id.helpC1_2){
            startActivity(new Intent(this,d1help.class));
            finish();
        }else if(i == R.id.settingC1_2){

        }else if(i == R.id.logoutC1_2){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }else if(i == R.id.toiawaseC1_2){
            callMailer();
        }else if(i == R.id.taikaiC1_2){
            startActivity(new Intent(this,d5taikai.class));
            finish();
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
