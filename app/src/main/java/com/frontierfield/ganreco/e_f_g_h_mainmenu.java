package com.frontierfield.ganreco;

import android.*;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.gesture.GestureUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class e_f_g_h_mainmenu extends AppCompatActivity implements View.OnClickListener {
    private final static int RESULT_CAMERA = 1001;
    private final static int REQUEST_PERMISSION = 1002;
    private Uri cameraUri;
    private File cameraFile;
    private String filePath;

    FloatingActionButton fab;
    ViewPager viewPager;
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_f_g_h_mainmenu);

        LinearLayout fotterHome = findViewById(R.id.homesetFotterHIJK);
        LinearLayout fotterInput = findViewById(R.id.inputsetFotterHIKJ);
        LinearLayout fotterYoyaku = findViewById(R.id.yoyakusetFotterHIJK);
        LinearLayout fotterHospital = findViewById(R.id.hospitalSetFotterHIJK);
        LinearLayout fotterMypage = findViewById(R.id.mypagesetFotterHIJK);
        ImageView bell = findViewById(R.id.button_SelectedUserHIJK);
        ImageView backBtn = findViewById(R.id.shape_ivHIJK);

        fotterHome.setOnClickListener(this);
        fotterInput.setOnClickListener(this);
        fotterYoyaku.setOnClickListener(this);
        fotterHospital.setOnClickListener(this);
        fotterMypage.setOnClickListener(this);
        bell.setOnClickListener(this);
        backBtn.setOnClickListener(this);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        viewPager = findViewById(R.id.viewpagerHIJKMain);
        tabLayout = findViewById(R.id.tabHIJKMain);

        FragmentManager fm = getSupportFragmentManager();
        HistoryFragmentAdapter historyFragmentAdapter=new HistoryFragmentAdapter(fm);
        viewPager.setAdapter(historyFragmentAdapter);

        Intent intent = getIntent();
        Integer id = intent.getIntExtra("id",0);
        //viewPager.setCurrentItem(id);

        tabLayout.setupWithViewPager(viewPager);//tablayoutとviewpager紐づけ

        fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.homesetFotterHIJK) {
            startActivity(new Intent(this,b1_2mainmenu.class));
            finish();
        }else if(i == R.id.inputsetFotterHIKJ){
            startActivity(new Intent(this,b3_input.class));
            finish();
        }else if (i == R.id.yoyakusetFotterHIJK){
            //startActivity(new Intent(this,e_f_g_h_mainmenu.class));
            //finish();
        }else if(i == R.id.hospitalSetFotterHIJK){

        }else if(i == R.id.mypagesetFotterHIJK){
            startActivity(new Intent(this,c1_2mypage.class));
            finish();
        }else if(i == R.id.button_SelectedUserHIJK){
            //d3 help
        }else if(i == R.id.shape_ivHIJK){
            startActivity(new Intent(this,b1_2mainmenu.class));
            finish();
        }else if(i == R.id.fab){
            if(viewPager.getCurrentItem() == 0) {
                startActivity(new Intent(this, e3_yotei.class));
            }else if(viewPager.getCurrentItem() > 0){
               if(Build.VERSION.SDK_INT >= 23){
                   checkPermission();
               }else{
                   cameraIntent();
               }
            }
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

    private void cameraIntent() {
        Global_Util gu = new Global_Util();
        gu.CreateDirectoryForPicture();

        // 保存ファイル名
        String fileName = new SimpleDateFormat(
                "ddHHmmss", Locale.US).format(new Date());

        //file名作成
        switch (viewPager.getCurrentItem()){
            case 1:
                filePath = String.format("%s/yotei_%s.jpg", gu.photoDir.getPath(),fileName);
                break;
            case 2:
                filePath = String.format("%s/okusuri_%s.jpg", gu.photoDir.getPath(),fileName);
                break;
            case 3:
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

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent intent) {
        Intent nextIntent;
        if (requestCode == RESULT_CAMERA) {

            if(cameraUri != null){
                switch (viewPager.getCurrentItem()){
                    case 1:
                        nextIntent = new Intent(this,f4_input.class);
                        nextIntent.putExtra("filePath",filePath);
                        startActivity(nextIntent);
                        break;
                    case 2:
                        nextIntent = new Intent(this,g4_input.class);
                        nextIntent.putExtra("filePath",filePath);
                        startActivity(nextIntent);
                        break;
                    case 3:
                       // filePath = "kensa" + filePath;//腫瘍マーカーとその他で分けるかも
                }
            }
            else{
                Log.d("debug","cameraUri == null");
            }
        }
    }
}