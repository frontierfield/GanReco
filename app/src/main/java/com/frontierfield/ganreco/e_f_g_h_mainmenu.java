package com.frontierfield.ganreco;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class e_f_g_h_mainmenu extends AppCompatActivity implements View.OnClickListener,ViewPager.OnPageChangeListener {
    private final static int RESULT_CAMERA = 1001;
    private final static int REQUEST_PERMISSION = 1002;
    private Uri cameraUri;
    private File cameraFile;
    private String filePath;
    private String fileName;
    int kensaSyubetu=0;
    TextView title;
    FloatingActionButton fab;
    ViewPager viewPager;
    TabLayout tabLayout;
    int detailKey;
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
        title=findViewById(R.id.titleH_I_J_K1_2);

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
        tabLayout.setupWithViewPager(viewPager);//tablayoutとviewpager紐づけ

        Intent intent = getIntent();
        int tab=intent.getIntExtra("tab",0);
        detailKey = intent.getIntExtra("detailKey",0);
        int tsuinRirekiID=intent.getIntExtra("TsuinRirekiID",-1);
        Bundle bundle=new Bundle();
        bundle.putInt("detailKey",detailKey);
        bundle.putInt("TsuinRirekiID",tsuinRirekiID);
        E_F_G_H_ListFragmentAdapter efghListFragmentAdapter = new E_F_G_H_ListFragmentAdapter(getSupportFragmentManager(),bundle);
        viewPager.setAdapter(efghListFragmentAdapter);
        viewPager.addOnPageChangeListener(this);
        viewPager.setCurrentItem(tab);

        if(detailKey==0) {
            fab.setOnClickListener(this);
        }else{
            ViewGroup viewGroup=(ViewGroup)fab.getParent();
            viewGroup.removeView(fab);
        }
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
                startActivity(new Intent(this, E3_Input.class));
            }if(viewPager.getCurrentItem()==3){
                final String[] items = {"腫瘍マーカー", "その他検診","キャンセル"};
                new AlertDialog.Builder(this).setTitle("検査種別の選択")
                        .setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case 0://
                                        kensaSyubetu=1;
                                        if(Build.VERSION.SDK_INT >= 23){
                                            checkPermission();
                                        }else{
                                            cameraIntent();
                                        }
                                    case 1://その他検診
                                        kensaSyubetu=2;
                                        if(Build.VERSION.SDK_INT >= 23){
                                            checkPermission();
                                        }else{
                                            cameraIntent();
                                        }
                                    case 2://キャンセル
                                }
                            }
                        })
                        .show();
            }
            else if(viewPager.getCurrentItem() > 0){
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
    //ストレージ権限なしカメラ呼び出し
    private void cameraIntent() {
        Global_Util gu = new Global_Util();
        gu.CreateDirectoryForPicture();

        // 保存ファイル名
        fileName = new SimpleDateFormat(
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
                switch (viewPager.getCurrentItem()){
                    case 1:
                        nextIntent = new Intent(this,F4_Input.class);
                        nextIntent.putExtra("filePath",cameraUri.toString());
                        nextIntent.putExtra("fileName",fileName);
                        startActivity(nextIntent);
                        break;
                    case 2:
                        nextIntent = new Intent(this,G4_Input.class);
                        nextIntent.putExtra("filePath",filePath);
                        startActivity(nextIntent);
                        break;
                    case 3:
                        nextIntent = new Intent(this,H6_Input.class);
                        nextIntent.putExtra("filePath",filePath);
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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch(position){
            case 0:
                title.setText("通院予定");
                break;
            case 1:
                if(detailKey==position){title.setText("診療詳細");}
                else{ title.setText("通院履歴");}
                break;
            case 2:
                if(detailKey==position){title.setText("処方詳細");}
                else{title.setText("お薬履歴");}
                break;
            case 3:
                if(detailKey==position){title.setText("検査結果詳細");}
                else{title.setText("検査履歴");}
                break;
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}