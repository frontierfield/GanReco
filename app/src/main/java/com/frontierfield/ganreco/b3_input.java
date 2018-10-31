package com.frontierfield.ganreco;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by kkarimu on 2018/06/25.
 */

public class b3_input extends AppCompatActivity implements View.OnClickListener {
    private final static int RESULT_CAMERA = 1001;
    private final static int REQUEST_PERMISSION = 1002;
    private Uri cameraUri;
    private File cameraFile;
    private String filePath;
    private String fileName;
    private int tab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b3);

        LinearLayout fotterHome = findViewById(R.id.homesetFotterB3);
        LinearLayout fotterInput = findViewById(R.id.inputsetFotterB3);
        LinearLayout fotterYoyaku = findViewById(R.id.yoyakusetFotterB3);
        LinearLayout fotterHospital = findViewById(R.id.hospitalSetFotterB3);
        LinearLayout fotterMypage = findViewById(R.id.mypagesetFotterB3);
        ImageView bell = findViewById(R.id.button_bell_ivB3);

        LinearLayout scheduleset = findViewById(R.id.schedulesetB3);
        LinearLayout okusuriset = findViewById(R.id.okusurisetB3);
        LinearLayout kensaset = findViewById(R.id.kensasetB3);
        LinearLayout tsuinset = findViewById(R.id.tsuinsetB3);

        fotterHome.setOnClickListener(this);
        fotterInput.setOnClickListener(this);
        fotterYoyaku.setOnClickListener(this);
        fotterHospital.setOnClickListener(this);
        fotterMypage.setOnClickListener(this);
        bell.setOnClickListener(this);
        scheduleset.setOnClickListener(this);
        okusuriset.setOnClickListener(this);
        kensaset.setOnClickListener(this);
        tsuinset.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.homesetFotterB3) {
            startActivity(new Intent(this,B1_2_GanrecoMain.class));
            finish();
        }else if(i == R.id.inputsetFotterB3){
            //startActivity(new Intent(this,b3_input.class));
            //finish();
        }else if (i == R.id.yoyakusetFotterB3){
            startActivity(new Intent(this,e_f_g_h_mainmenu.class));
            finish();
        }else if(i == R.id.hospitalSetFotterB3){

        }else if(i == R.id.mypagesetFotterB3){
            startActivity(new Intent(this,c1_2mypage.class));
            finish();
        }else if(i == R.id.button_bell_ivB3){
            //d3 help
        }else if(i == R.id.schedulesetB3){
            Intent intent = new Intent(this,e_f_g_h_mainmenu.class);
            intent.putExtra("tab",0);
            startActivity(intent);
            startActivity(new Intent(this,E3_Input.class));
            finish();
        }else if(i == R.id.okusurisetB3||i == R.id.kensasetB3||i == R.id.tsuinsetB3){
            switch (i){
                case R.id.okusurisetB3:
                    tab=2;
                    break;
                case R.id.kensasetB3:
                    tab=3;
                    break;
                case R.id.tsuinsetB3:
                    tab=1;
                    break;
            }
            if(Build.VERSION.SDK_INT >= 23){
                checkPermission();
            }else{
                cameraIntent();
            }
            //Intent intent = new Intent(this,e_f_g_h_mainmenu.class);
            //startActivity(intent);
            //finish();
        }
    }

    private void checkPermission() {
        // 既に許可している
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED){
            cameraIntent();
        }
        // 拒否していた場合
        else{
            requestPermission();
        }
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION);

        } else {
            Toast toast = Toast.makeText(this,
                    "許可されないとアプリが実行できません",
                    Toast.LENGTH_SHORT);
            toast.show();

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,},
                    REQUEST_PERMISSION);

        }
    }

    // 結果の受け取り
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        Log.d("debug","onRequestPermissionsResult()");

        if (requestCode == REQUEST_PERMISSION) {
            // 使用が許可された
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                cameraIntent();
            } else {
                // それでも拒否された時の対応
                Toast toast = Toast.makeText(this,
                        "これ以上なにもできません", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
    //ストレージ権限なしカメラ呼び出し
    private void cameraIntent() {
        Global_Util gu = new Global_Util();
        gu.CreateDirectoryForPicture();

        // 保存ファイル名
        fileName = new SimpleDateFormat(
                "ddHHmmss", Locale.US).format(new Date());

        //file名作成
        switch (tab){
            case 1:
                filePath = String.format("%s/yotei_%s.jpg", gu.photoDir.getPath(),fileName);
                break;
            case 2:
                filePath = String.format("%s/okusuri_%s.jpg", gu.photoDir.getPath(),fileName);
                break;
            case 3:
                filePath = String.format("%s/kensa_%s.jpg", gu.photoDir.getPath(),fileName);
                //filePath = "kensa_" + filePath;//腫瘍マーカーとその他で分けるかも
                break;
        }

        // capture画像のファイルパス
        cameraFile = new File(filePath);
        //cameraUri = new Uri;

        cameraUri = FileProvider.getUriForFile(
                this,
                getApplicationContext().getPackageName() + ".fileprovider",
                cameraFile);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
        startActivityForResult(intent, RESULT_CAMERA);
    }
    //写真撮影後
    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        Intent nextIntent;
        if (requestCode == RESULT_CAMERA) {
            if(filePath != null){
                switch (tab){
                    case 1:
                        nextIntent = new Intent(this,F4_Input.class);
                        nextIntent.putExtra("filePath",cameraUri.toString());
                        nextIntent.putExtra("fileName",fileName);
                        startActivity(nextIntent);
                        break;
                    case 2:
                        nextIntent = new Intent(this,G4_Input.class);
                        nextIntent.putExtra("filePath",cameraUri.toString());
                        nextIntent.putExtra("fileName",fileName);
                        startActivity(nextIntent);
                        break;
                    case 3:
                        nextIntent = new Intent(this,H6_Input.class);
                        nextIntent.putExtra("filePath",cameraUri.toString());
                        nextIntent.putExtra("fileName",fileName);
                        startActivity(nextIntent);
                        break;
                    // filePath = "kensa" + filePath;//腫瘍マーカーとその他で分けるかも
                }
            }
            else{
                Log.d("debug","data.getExtras() == null");
            }
        }
    }
}
