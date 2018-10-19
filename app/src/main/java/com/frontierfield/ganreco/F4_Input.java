package com.frontierfield.ganreco;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class F4_Input extends AppCompatActivity implements  View.OnClickListener{
    ImageView backBtnHeader,ImageViewShinryo;
    Spinner year, month, day;
    TextView btnCancel;
    TextView btnAdd;
    LinearLayout eraseBtn;
    EditText shinsatsu,hospital;

    UserProfile userProfile;
    Global_Util globalUtil;

    TsuinRireki tsuinRireki;

    int position=-1;

    String filePath;
    String fileName;
    private Uri cameraUri;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.f4_input);

        userProfile = UserProfile.getInstance();
        globalUtil = new Global_Util();

        backBtnHeader = findViewById(R.id.backF4);
        year = findViewById(R.id.yearF4);
        month = findViewById(R.id.monthF4);
        day = findViewById(R.id.dayF4);

        eraseBtn = findViewById(R.id.eraseDataF6);
        btnCancel = findViewById(R.id.cancelF4);
        btnAdd = findViewById(R.id.addF4);
        hospital = findViewById(R.id.editTextHospNameF4);
        shinsatsu = findViewById(R.id.editTextShinsatsuF4);
        ImageViewShinryo = findViewById(R.id.imageViewSinryoF6);

        ArrayAdapter<Integer> adapterYear = new ArrayAdapter<Integer>(this,R.layout.support_simple_spinner_dropdown_item,globalUtil.aYotei);
        ArrayAdapter<Integer> adapterMonth = new ArrayAdapter<Integer>(this,R.layout.support_simple_spinner_dropdown_item,globalUtil.aMonth);
        ArrayAdapter<Integer> adapterDay = new ArrayAdapter<Integer>(this,R.layout.support_simple_spinner_dropdown_item,globalUtil.aDay);

        year.setAdapter(adapterYear);
        month.setAdapter(adapterMonth);
        day.setAdapter(adapterDay);

        backBtnHeader.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        eraseBtn.setOnClickListener(this);
        ImageViewShinryo.setOnClickListener(this);

        LoadTsuinRirekiData();
    }

    private void LoadTsuinRirekiData() {
        Intent intent = getIntent();
        position = intent.getIntExtra("TsuinRirekiID",-1);
        filePath=intent.getStringExtra("filePath");
        fileName=intent.getStringExtra("fileName");

        if(position == -1){//新規追加
            year.setSelection(0);
            Calendar now = Calendar.getInstance();
            int nowY = now.get(Calendar.YEAR);
            for(int i = 0;i < globalUtil.aYotei.size();i++){//????
                if(nowY == i){
                    year.setSelection(i);
                }
            }
            if(filePath != null){
                // capture画像のファイルパス
                cameraUri =Uri.parse(filePath);
                try {
                    bitmap=globalUtil.getPreResizedBitmap(cameraUri,this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ImageViewShinryo.setImageBitmap(bitmap);
            }
        }else{//通院予定変更したいとき//もともと入ってたデータ表示させる
            tsuinRireki=TsuinRirekiList.getSavedTsuinRireki(position);
            year.setSelection(tsuinRireki.getYearIndex());
            month.setSelection(tsuinRireki.getMonthIndex());
            day.setSelection(tsuinRireki.getDayIndex());
            hospital.setText(tsuinRireki.getHospital());
            shinsatsu.setText(tsuinRireki.getDetail());
            if(tsuinRireki.getFilePath() != null){
                // capture画像のファイルパス
                cameraUri =Uri.parse(tsuinRireki.getFilePath());
                try {
                    bitmap=globalUtil.getPreResizedBitmap(cameraUri,this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ImageViewShinryo.setImageBitmap(bitmap);
            }
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if(i == R.id.backF4){
            //goto efgh
            Intent intent = new Intent(this, e_f_g_h_mainmenu.class);
            intent.putExtra("tab",1);
            startActivity(intent);
            finish();
        }else if(i == R.id.cancelF4){
            //goto efgh
            Intent intent = new Intent(this, e_f_g_h_mainmenu.class);
            intent.putExtra("tab",1);
            startActivity(intent);
            finish();
        }else if(i == R.id.addF4){
            RegistryData();
            Intent intent = new Intent(this, e_f_g_h_mainmenu.class);
            intent.putExtra("tab",1);
            startActivity(intent);
        }else if(i == R.id.eraseDataF6){
            EraseData();
            Intent intent = new Intent(this, e_f_g_h_mainmenu.class);
            intent.putExtra("tab",1);
            startActivity(intent);
        }else if(i == R.id.imageViewSinryoF6){
            Intent intent=new Intent(this,F5_Enlarge.class);
            intent.putExtra("cameraUri",cameraUri.toString());
            startActivity(intent);
        }
    }

    private void RegistryData() {
        if(position == -1) {//新しく追加
            //この時点で保存するデータを、spinnerでの場所の番号にしとくからややこしいことになってる
            tsuinRireki = new TsuinRireki(null,false,hospital.getText().toString(),
                    shinsatsu.getText().toString(), year.getSelectedItemPosition(), month.getSelectedItemPosition(),
                    day.getSelectedItemPosition());
            tsuinRireki.setFilePath(filePath);
            tsuinRireki.setLocalPath(filePath);
            tsuinRireki.setFileName(fileName);
            TsuinRirekiFirebaseStorage.saveTsuinRirekiFirebaseStorage(bitmap,fileName,this);

        }else{//すでにあるデータ変更
            tsuinRireki.detail = shinsatsu.getText().toString();
            tsuinRireki.hospital = hospital.getText().toString();
            tsuinRireki.y_index = year.getSelectedItemPosition();
            tsuinRireki.m_index = month.getSelectedItemPosition();
            tsuinRireki.d_index = day.getSelectedItemPosition();
            TsuinRirekiList.deleteTsuinRireki(position);
        }
        TsuinRirekiList.addTsuinRireki(tsuinRireki);
        finish();
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
