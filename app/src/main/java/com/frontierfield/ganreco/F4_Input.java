package com.frontierfield.ganreco;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.util.Calendar;

public class F4_Input extends AppCompatActivity implements  View.OnClickListener{
    ImageView backBtnHeader,ImageViewShinryo;
    TextView btnCancel;
    TextView btnAdd;
    LinearLayout eraseBtn;
    EditText shinsatsu,hospital;
    DatePicker datePicker;

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
        datePicker=findViewById(R.id.date_pickerF4);

        eraseBtn = findViewById(R.id.eraseDataF6);
        btnCancel = findViewById(R.id.cancelF4);
        btnAdd = findViewById(R.id.addF4);
        hospital = findViewById(R.id.editTextHospNameF4);
        shinsatsu = findViewById(R.id.editTextShinsatsuF4);
        ImageViewShinryo = findViewById(R.id.imageViewSinryoF6);

        backBtnHeader.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        eraseBtn.setOnClickListener(this);
        ImageViewShinryo.setOnClickListener(this);

        LoadTsuinRirekiData();
    }

    private void LoadTsuinRirekiData() {
        Intent intent = getIntent();
        position = intent.getIntExtra("position",-1);
        filePath=intent.getStringExtra("filePath");
        fileName=intent.getStringExtra("fileName");

        if(position == -1){//新規追加
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
            datePicker.init(tsuinRireki.getYear(), tsuinRireki.getMonth(), tsuinRireki.getDay(), new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {

                }
            });
            hospital.setText(tsuinRireki.getHospital());
            shinsatsu.setText(tsuinRireki.getDetail());
            if(tsuinRireki.getFilePath() != null){
                try {
                    bitmap = F6_G6_H8_Detail.bitmap;
                    ImageViewShinryo.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
            Intent intent=new Intent(this,F5_G5_H7_Enlarge.class);
            if(cameraUri!=null) {intent.putExtra("cameraUri",cameraUri.toString());}
            startActivity(intent);
        }
    }

    private void RegistryData() {
        if(position == -1) {//新しく追加
            //この時点で保存するデータを、spinnerでの場所の番号にしとくからややこしいことになってる
            tsuinRireki = new TsuinRireki(null,false,hospital.getText().toString(),
                    shinsatsu.getText().toString(),datePicker.getYear(), datePicker.getMonth(),
                    datePicker.getDayOfMonth());
            tsuinRireki.setFilePath(filePath);
            tsuinRireki.setLocalPath(filePath);
            tsuinRireki.setFileName(fileName);
            TsuinRirekiFirebaseStorage.saveTsuinRirekiFirebaseStorage(bitmap,fileName,this);

        }else{//すでにあるデータ変更
            tsuinRireki.detail = shinsatsu.getText().toString();
            tsuinRireki.hospital = hospital.getText().toString();
            tsuinRireki.year = datePicker.getYear();
            tsuinRireki.month = datePicker.getMonth();
            tsuinRireki.day = datePicker.getDayOfMonth();
            TsuinRirekiList.deleteTsuinRireki(position,this);
        }
        TsuinRirekiList.addTsuinRireki(tsuinRireki);
        finish();
    }
    private void EraseData(){
        if(position == -1){
            /*新規なら特になにもなし*/
            finish();
        }else {
            TsuinRirekiList.deleteTsuinRireki(position,this);
            finish();
        }
    }
}
