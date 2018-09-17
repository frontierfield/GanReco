package com.frontierfield.ganreco;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
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

import java.io.File;

/**
 * Created by kkarimu on 2018/07/12.
 */

public class f4_input extends AppCompatActivity implements  View.OnClickListener {
    ImageView backBtnHeader,ImageViewShinryo;
    TextView helpBtn;
    Spinner year, month, day;
    TextView btnCancel;
    TextView btnAdd;
    LinearLayout eraseBtn;
    EditText shinsatsu,hospital;

    UserProfile up;
    Global_Util gu;

    tsuin_rireki tr;

    String filePath;
    private Uri cameraUri;
    private File cameraFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.f4_input);

        up=new UserProfile();
        gu=new Global_Util();

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


        ArrayAdapter<Integer> adapterYear = new ArrayAdapter<Integer>(this,R.layout.support_simple_spinner_dropdown_item,gu.aYear);
        ArrayAdapter<Integer> adapterMonth = new ArrayAdapter<Integer>(this,R.layout.support_simple_spinner_dropdown_item,gu.aMonth);
        ArrayAdapter<Integer> adapterDay = new ArrayAdapter<Integer>(this,R.layout.support_simple_spinner_dropdown_item,gu.aDay);

        year.setAdapter(adapterYear);
        month.setAdapter(adapterMonth);
        day.setAdapter(adapterDay);

        backBtnHeader.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        eraseBtn.setOnClickListener(this);
        ImageViewShinryo.setOnClickListener(this);

        LoadShinryoData();
    }

    private void LoadShinryoData() {
         /*upからyotei load*/
        Intent intent = getIntent();
        String shinryo_id = intent.getStringExtra("shinryo_id");
        filePath = intent.getStringExtra("filePath");

        if(filePath != null){
            // capture画像のファイルパス
            cameraFile = new File(filePath);
            cameraUri = FileProvider.getUriForFile(
                this,
                getApplicationContext().getPackageName() + ".fileprovider",
                cameraFile);

            ImageViewShinryo.setImageURI(cameraUri);

            Global_Util gu = new Global_Util();

            Log.d("",gu.rotationPhoto(cameraUri,this).toString());



        }
        if(shinryo_id == null){
            //new
        }else{
            //edit
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
            //focus
        }
    }

    private void EraseData() {
        finish();
    }

    private void RegistryData() {
        registerDatabase(filePath);
        finish();
    }

    // アンドロイドのデータベースへ登録する
    private void registerDatabase(String file) {
        ContentValues contentValues = new ContentValues();
        ContentResolver contentResolver = this.getContentResolver();
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        contentValues.put("_data", file);
        contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);
    }
}
