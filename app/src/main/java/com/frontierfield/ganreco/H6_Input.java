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

public class H6_Input extends AppCompatActivity implements View.OnClickListener {
    ImageView backBtnHeader , ImageViewKensa;
    TextView helpBtn;
    TextView btnCancel;
    TextView btnAdd;
    LinearLayout eraseBtn;
    EditText kensa,hospital;
    DatePicker datePicker;

    UserProfile userProfile;
    Global_Util globalUtil;

    KensaRireki kensaRireki;
    int position=-1;

    String filePath;
    String fileName;
    private Uri cameraUri;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.h6_input);

        userProfile = UserProfile.getInstance();
        globalUtil = new Global_Util();

        backBtnHeader = findViewById(R.id.backH6);
        datePicker=findViewById(R.id.date_pickerH6);

        eraseBtn = findViewById(R.id.eraseDataH6);
        btnCancel = findViewById(R.id.cancelH6);
        btnAdd = findViewById(R.id.addH6);
        hospital = findViewById(R.id.editTextKensaNameH6);
        kensa = findViewById(R.id.editTextKensaH6);
        ImageViewKensa = findViewById(R.id.imageViewKensaH6);

        backBtnHeader.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        eraseBtn.setOnClickListener(this);
        ImageViewKensa.setOnClickListener(this);

        LoadKensaData();
    }

    private void LoadKensaData() {
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
                ImageViewKensa.setImageBitmap(bitmap);
            }
        }else{//通院予定変更したいとき//もともと入ってたデータ表示させる
            kensaRireki=KensaRirekiList.getSavedKensaRireki(position);
            datePicker.init(kensaRireki.getYear(), kensaRireki.getMonth(), kensaRireki.getDay(), new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {

                }
            });
            hospital.setText(kensaRireki.hospital);
            kensa.setText(kensaRireki.detail);
            if(kensaRireki.getFilePath() != null){
                try {
                    bitmap = F6_G6_H8_Detail.bitmap;
                    ImageViewKensa.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if(i == R.id.backH6){
            //goto efgh
            Intent intent = new Intent(this, e_f_g_h_mainmenu.class);
            intent.putExtra("tab",3);
            startActivity(intent);
            finish();
        }
        else if(i == R.id.cancelH6){
            //goto efgh
            Intent intent = new Intent(this, e_f_g_h_mainmenu.class);
            intent.putExtra("tab",3);
            startActivity(intent);
            finish();
        }
        else if(i == R.id.addH6){
            RegistryData();
            Intent intent = new Intent(this, e_f_g_h_mainmenu.class);
            intent.putExtra("tab",3);
            startActivity(intent);
        }
        else if(i == R.id.eraseDataH6){
            EraseData();
            Intent intent = new Intent(this, e_f_g_h_mainmenu.class);
            intent.putExtra("tab",3);
            startActivity(intent);
        }
        else if(i == R.id.imageViewKensaH6){
            Intent intent=new Intent(this,F5_G5_H7_Enlarge.class);
            if(cameraUri!=null) {intent.putExtra("cameraUri",cameraUri.toString());}
            startActivity(intent);
        }
    }

    private void RegistryData() {
        if(position == -1) {//新しく追加
            //この時点で保存するデータを、spinnerでの場所の番号にしとくからややこしいことになってる
            kensaRireki = new KensaRireki(null,false,hospital.getText().toString(),
                    kensa.getText().toString(),datePicker.getYear(), datePicker.getMonth(),
                    datePicker.getDayOfMonth());
            kensaRireki.setFilePath(filePath);
            kensaRireki.setLocalPath(filePath);
            kensaRireki.setFileName(fileName);
            KensaRirekiFirebaseStorage.saveKensaRirekiFirebaseStorage(bitmap,fileName,this);
        }else{//すでにあるデータ変更
            kensaRireki.detail = kensa.getText().toString();
            kensaRireki.hospital = hospital.getText().toString();
            kensaRireki.year = datePicker.getYear();
            kensaRireki.month = datePicker.getMonth();
            kensaRireki.day = datePicker.getDayOfMonth();
            KensaRirekiList.deleteKensaRireki(position,this);
        }
        KensaRirekiList.addKensaRireki(kensaRireki);
        finish();
    }
    private void EraseData(){
        if(position == -1){
            /*新規なら特になにもなし*/
            finish();
        }else {
            KensaRirekiList.deleteKensaRireki(position,this);
            finish();
        }
    }
}
