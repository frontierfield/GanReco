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

import java.io.IOException;
import java.util.Calendar;

public class G4_Input extends AppCompatActivity implements View.OnClickListener {
    ImageView backBtnHeader , ImageViewShinryo;
    TextView helpBtn;
    Spinner year, month, day;
    TextView btnCancel;
    TextView btnAdd;
    LinearLayout eraseBtn;
    EditText okusuri,drugstore;

    UserProfile userProfile;
    Global_Util globalUtil;

    OkusuriRireki okusuriRireki;
    int position=-1;

    String filePath;
    String fileName;
    private Uri cameraUri;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.g4_input);

        userProfile = UserProfile.getInstance();
        globalUtil = new Global_Util();

        backBtnHeader = findViewById(R.id.backG4);
        year = findViewById(R.id.yearG4);
        month = findViewById(R.id.monthG4);
        day = findViewById(R.id.dayG4);

        eraseBtn = findViewById(R.id.eraseDataG4);
        btnCancel = findViewById(R.id.cancelG4);
        btnAdd = findViewById(R.id.addG4);
        drugstore = findViewById(R.id.editTextYakkyokuNameG4);
        okusuri = findViewById(R.id.editTextSyohouG4);
        ImageViewShinryo = findViewById(R.id.imageViewSinryoG4);

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

        LoadOkusuriData();
    }

    private void LoadOkusuriData() {
         /*upからyotei load*/
        Intent intent = getIntent();
        position = intent.getIntExtra("OkusuriRirekiID",-1);
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
            okusuriRireki=OkusuriRirekiList.getSavedOkusuriRireki(position);
            year.setSelection(okusuriRireki.y_index);
            month.setSelection(okusuriRireki.m_index);
            day.setSelection(okusuriRireki.d_index);
            drugstore.setText(okusuriRireki.drugstore);
            okusuri.setText(okusuriRireki.detail);
            if(okusuriRireki.getFilePath() != null){
                // capture画像のファイルパス
                cameraUri =Uri.parse(okusuriRireki.getFilePath());
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
        if(i == R.id.backG4){
            //goto efgh
            Intent intent = new Intent(this, e_f_g_h_mainmenu.class);
            intent.putExtra("tab",2);
            startActivity(intent);
            finish();
        }
        else if(i == R.id.cancelG4){
            //goto efgh
            Intent intent = new Intent(this, e_f_g_h_mainmenu.class);
            intent.putExtra("tab",2);
            startActivity(intent);
            finish();
        }
        else if(i == R.id.addG4){
            RegistryData();
            Intent intent = new Intent(this, e_f_g_h_mainmenu.class);
            intent.putExtra("tab",2);
            startActivity(intent);
        }
        else if(i == R.id.eraseDataG4){
            EraseData();
            Intent intent = new Intent(this, e_f_g_h_mainmenu.class);
            intent.putExtra("tab",2);
            startActivity(intent);
        }
        else if(i == R.id.imageViewSinryoG4){
            Intent intent=new Intent(this,F5_G5_H7_Enlarge.class);
            intent.putExtra("cameraUri",cameraUri.toString());
            startActivity(intent);
        }
    }

    private void RegistryData() {
        if(position == -1) {//新しく追加
            //この時点で保存するデータを、spinnerでの場所の番号にしとくからややこしいことになってる
            okusuriRireki = new OkusuriRireki(null,false,drugstore.getText().toString(),
                    okusuri.getText().toString(), year.getSelectedItemPosition(), month.getSelectedItemPosition(),
                    day.getSelectedItemPosition());
            okusuriRireki.setFilePath(filePath);
            okusuriRireki.setLocalPath(filePath);
            okusuriRireki.setFileName(fileName);
            OkusuriRirekiFirebaseStorage.saveOkusuriRirekiFirebaseStorage(bitmap,fileName,this);
        }else{//すでにあるデータ変更
            okusuriRireki.detail = okusuri.getText().toString();
            okusuriRireki.drugstore = drugstore.getText().toString();
            okusuriRireki.y_index = year.getSelectedItemPosition();
            okusuriRireki.m_index = month.getSelectedItemPosition();
            okusuriRireki.d_index = day.getSelectedItemPosition();
            OkusuriRirekiList.deleteOkusuriRireki(position);
        }
        OkusuriRirekiList.addOkusuriRireki(okusuriRireki);
        finish();
    }
    private void EraseData(){
        if(position == -1){
            /*新規なら特になにもなし*/
            finish();
        }else {
            OkusuriRirekiList.deleteOkusuriRireki(position);
            
            finish();
        }
    }
}
