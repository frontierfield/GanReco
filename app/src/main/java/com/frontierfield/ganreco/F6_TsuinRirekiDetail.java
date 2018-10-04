package com.frontierfield.ganreco;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;

public class F6_TsuinRirekiDetail extends AppCompatActivity implements  View.OnClickListener{
    ImageView imageViewShinryo;
    TextView hospitalName,date,detail;
    TextView btnCancel;
    TextView btnEdit;
    LinearLayout eraseBtn;

    int position=-1;
    TsuinRireki tsuinRireki;
    Global_Util globalUtil;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.f6);

        globalUtil = new Global_Util();

        eraseBtn = findViewById(R.id.eraseDataF6);
        btnCancel = findViewById(R.id.cancelF6);
        btnEdit = findViewById(R.id.editF6);
        hospitalName = findViewById(R.id.HospitalNameF6);
        date = findViewById(R.id.DateF6);
        detail=findViewById(R.id.DetailF6);
        imageViewShinryo = findViewById(R.id.imageViewSinryoF6);

        btnCancel.setOnClickListener(this);
        btnEdit.setOnClickListener(this);
        eraseBtn.setOnClickListener(this);
        imageViewShinryo.setOnClickListener(this);

        LoadTsuinRirekiData();
    }

    private void LoadTsuinRirekiData(){
        Intent intent = getIntent();
        position = intent.getIntExtra("TsuinRirekiID",-1);
        tsuinRireki=TsuinRirekiList.getSavedTsuinRireki(position);
        Integer year = globalUtil.aYotei.get(tsuinRireki.getYearIndex());
        Integer month = globalUtil.aMonth[tsuinRireki.getMonthIndex()];
        Integer day = globalUtil.aDay[tsuinRireki.getDayIndex()];

        hospitalName.setText(tsuinRireki.getHospital());
        date.setText(year.toString() + "/" +
                        month.toString() + "/" + day.toString());
        detail.setText(tsuinRireki.detail);//ここにOCRの結果を突っ込む

        try {
            bitmap=globalUtil.getPreResizedBitmap(Uri.parse(tsuinRireki.getFilepath()),this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageViewShinryo.setImageBitmap(bitmap);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if(i == R.id.cancelF6){
            //goto efgh
            finish();
        }else if(i == R.id.editF6){
            Intent intent=new Intent(this,F4_Input.class);
            intent.putExtra("TsuinRirekiID",position);
            startActivity(intent);
            finish();
        }else if(i == R.id.eraseDataF6){
            EraseData();
        }else if(i == R.id.imageViewSinryoF6){
            Intent intent=new Intent(this,F5_Enlarge.class);
            intent.putExtra("cameraUri",tsuinRireki.getFilepath());
            startActivity(intent);
        }

    }
    private void EraseData(){
        if(position == -1){
            /*新規なら特になにもなし*/
            finish();
        }else {
            TsuinRirekiList.deleteTsuinRireki(position);
            finish();
        }
    }
}
