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

public class G4_Input extends AppCompatActivity implements View.OnClickListener {
    ImageView backBtnHeader , ImageViewShinryo;
    TextView helpBtn;
    TextView btnCancel;
    TextView btnAdd;
    LinearLayout eraseBtn;
    EditText okusuri,drugstore;
    DatePicker datePicker;

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
        datePicker=findViewById(R.id.date_pickerG4);

        eraseBtn = findViewById(R.id.eraseDataG4);
        btnCancel = findViewById(R.id.cancelG4);
        btnAdd = findViewById(R.id.addG4);
        drugstore = findViewById(R.id.editTextYakkyokuNameG4);
        okusuri = findViewById(R.id.editTextSyohouG4);
        ImageViewShinryo = findViewById(R.id.imageViewSinryoG4);

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
            okusuriRireki=OkusuriRirekiList.getSavedOkusuriRireki(position);
            datePicker.init(okusuriRireki.getYear(), okusuriRireki.getMonth(), okusuriRireki.getDay(), new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {

                }
            });
            drugstore.setText(okusuriRireki.pharmacy);
            okusuri.setText(okusuriRireki.detail);
            if(okusuriRireki.getFilePath() != null){
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
            if(cameraUri!=null) {intent.putExtra("cameraUri",cameraUri.toString());}
            startActivity(intent);
        }
    }

    private void RegistryData() {
        if(position == -1) {//新しく追加
            //この時点で保存するデータを、spinnerでの場所の番号にしとくからややこしいことになってる
            okusuriRireki = new OkusuriRireki(null,false,drugstore.getText().toString(),
                    okusuri.getText().toString(), datePicker.getYear(), datePicker.getMonth(),
                    datePicker.getDayOfMonth());
            okusuriRireki.setFilePath(filePath);
            okusuriRireki.setLocalPath(filePath);
            okusuriRireki.setFileName(fileName);
            OkusuriRirekiFirebaseStorage.saveOkusuriRirekiFirebaseStorage(bitmap,fileName,this);
        }else{//すでにあるデータ変更
            okusuriRireki.detail = okusuri.getText().toString();
            okusuriRireki.pharmacy = drugstore.getText().toString();
            okusuriRireki.year = datePicker.getYear();
            okusuriRireki.month = datePicker.getMonth();
            okusuriRireki.day = datePicker.getDayOfMonth();
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
