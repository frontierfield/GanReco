package com.frontierfield.ganreco;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

public class F4_Input extends AppCompatActivity implements  View.OnClickListener {
    ImageView backBtnHeader,ImageViewShinryo;
    TextView helpBtn;
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
    private File cameraFile;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.f4_input);

        userProfile=UserProfile.getInstance();
        globalUtil=new Global_Util();

        backBtnHeader = findViewById(R.id.backF4);
        year = findViewById(R.id.yearF4);
        month = findViewById(R.id.monthF4);
        day = findViewById(R.id.dayF4);

        eraseBtn = findViewById(R.id.eraseDataF4);
        btnCancel = findViewById(R.id.cancelF4);
        btnAdd = findViewById(R.id.addF4);
        hospital = findViewById(R.id.editTextHospNameF4);
        shinsatsu = findViewById(R.id.editTextShinsatsuF4);
        ImageViewShinryo = findViewById(R.id.imageViewSinryoF4);

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
                cameraFile = new File(filePath);
                cameraUri = FileProvider.getUriForFile(
                        this,
                        getApplicationContext().getPackageName() + ".fileprovider",
                        cameraFile);
                try {
                    bitmap=globalUtil.getPreResizedBitmap(cameraUri,this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ImageViewShinryo.setImageBitmap(bitmap);
            }
        }else{//通院予定変更したいとき//もともと入ってたデータ表示させる
            tsuinRireki=TsuinRirekiList.getSavedTsuinRireki(position);
            year.setSelection(tsuinRireki.y_index);
            month.setSelection(tsuinRireki.m_index);
            day.setSelection(tsuinRireki.d_index);
            hospital.setText(tsuinRireki.hospital);
            shinsatsu.setText(tsuinRireki.detail);
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if(i == R.id.backF4){
            //goto efgh
            finish();
        }else if(i == R.id.cancelF4){
            //goto efgh
            finish();
        }else if(i == R.id.addF4){
            RegistryData();
        }else if(i == R.id.eraseDataF4){
            EraseData();
        }else if(i == R.id.imageViewSinryoF4){

        }
    }

    private void RegistryData() {
        if(position == -1) {//新しく追加
            //この時点で保存するデータを、spinnerでの場所の番号にしとくからややこしいことになってる
            tsuinRireki = new TsuinRireki(null,false,hospital.getText().toString(),
                    shinsatsu.getText().toString(), year.getSelectedItemPosition(), month.getSelectedItemPosition(),
                    day.getSelectedItemPosition(),cameraUri);
            tsuinRireki.setFilepath(filePath);
            tsuinRireki.setLocalpath(filePath);
            tsuinRireki.setFileName(fileName);
            TsuinRirekiFirebaseStorage.saveTsuinRirekiFirebaseStorage(bitmap,fileName);

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
            //年月日で最後の一つなら、タグも消す
            finish();
        }
    }
}
