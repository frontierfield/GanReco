package com.frontierfield.ganreco;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import static com.frontierfield.ganreco.R.id.btnUserInfoRed;

public class B1_2_GanrecoMain extends AppCompatActivity implements View.OnClickListener {
    SharedPreferences cache;
    LinearLayout gotoMypage;
    UserProfile userProfile = UserProfile.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b1_2main);

        // Applicationライフサイクル保護対応
        // Lifecycleを取得し、LifecycleObserverをimplementsしたクラスを渡す
        getLifecycle().addObserver(new ObserveLifecycle());
        Log.d("GanrecoMain_Lifecycle", "Activity::onCreate");

        LinearLayout fotterHome = findViewById(R.id.homesetFotterD1_2);         // [Tab bar] HOME ボタン
        LinearLayout fotterInput = findViewById(R.id.inputsetFotterD1_2);       // [Tab bar] 入力　ボタン
        LinearLayout fotterYoyaku = findViewById(R.id.yoyakusetFotterD1_2);     // [Tab bar] 予定/履歴 ボタン
        LinearLayout fotterHospital = findViewById(R.id.hospitalSetFotterD1_2); // [Tab bar] 病院検索 ボタン
        LinearLayout fotterMypage = findViewById(R.id.mypagesetFotterD1_2);     // [Tab bar] マイページ ボタン
        gotoMypage = findViewById(R.id.gotomypage_b1_2);
        ImageView bell = findViewById(R.id.button_bell_ivD1_2);
        // ユーザ情報初期登録の赤い部分
        TextView btn0_b1to2 = findViewById(R.id.btnUserInfoRedArrow);
        TextView btn1_b1to2 = findViewById(R.id.textView4);
        TextView btn2_b1to2 = findViewById(R.id.textView5);
        LinearLayout btn3_b1to2 = findViewById(btnUserInfoRed);

        LinearLayout schedule = findViewById(R.id.scheduleset);     // [レイアウト] 通院予定
        LinearLayout okusuri = findViewById(R.id.okusuri);          // [レイアウト] お薬履歴
        LinearLayout kensaset = findViewById(R.id.kensaset);        // [レイアウト] 検査履歴
        LinearLayout tsuin = findViewById(R.id.tsuinset);           // [レイアウト] 通院履歴

        fotterHome.setOnClickListener(this);
        fotterInput.setOnClickListener(this);
        fotterYoyaku.setOnClickListener(this);
        fotterHospital.setOnClickListener(this);
        fotterMypage.setOnClickListener(this);

        schedule.setOnClickListener(this);
        okusuri.setOnClickListener(this);
        kensaset.setOnClickListener(this);
        tsuin.setOnClickListener(this);
        btn0_b1to2.setOnClickListener(this);
        btn1_b1to2.setOnClickListener(this);
        btn2_b1to2.setOnClickListener(this);
        btn3_b1to2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        // Tab Bar のボタン
        if (i == R.id.homesetFotterD1_2) {
            //current
        }else if(i == R.id.inputsetFotterD1_2){
            startActivity(new Intent(this,b3_input.class));
            finish();
        }else if (i == R.id.yoyakusetFotterD1_2){
            Intent intent = new Intent(this,e_f_g_h_mainmenu.class);
            intent.putExtra("id",0);
            startActivity(intent);
            finish();
        }else if(i == R.id.hospitalSetFotterD1_2){

        }else if(i == R.id.mypagesetFotterD1_2){
            startActivity(new Intent(this,c1_2mypage.class));
            finish();
        }
        // ベルボタン
        else if(i == R.id.button_bell_ivD1_2){
            //d3 help
        }
        // 通院予定
        else if(i == R.id.scheduleset){
            //efgh
            Intent intent = new Intent(this,e_f_g_h_mainmenu.class);
            intent.putExtra("id",0);
            startActivity(intent);
            finish();
        }
        // お薬履歴
        else if(i == R.id.okusuri){
            //efgh
            Intent intent = new Intent(this,e_f_g_h_mainmenu.class);
            intent.putExtra("id",2);
            startActivity(intent);
            finish();
        }
        // 検査履歴
        else if(i == R.id.kensaset){
            //efgh
            Intent intent = new Intent(this,e_f_g_h_mainmenu.class);
            intent.putExtra("id",3);
            startActivity(intent);
            finish();
        }
        // 通院履歴
        else if(i == R.id.tsuinset){
            //efgh
            Intent intent = new Intent(this,e_f_g_h_mainmenu.class);
            intent.putExtra("id",1);
            startActivity(intent);
            finish();
        }
        // 「ユーザー情報が未登録です」ボタン
        else if(i == R.id.btnUserInfoRedArrow || i == R.id.textView4 || i == R.id.textView5 || i == btnUserInfoRed){
            startActivity(new Intent(this, C3_UserInfo.class));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d("GanrecoMain_Lifecycle", "Activity::onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d("GanrecoMain_Lifecycle", "Activity::onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("GanrecoMain_Lifecycle", "Activity::onResume");
        cache = this.getSharedPreferences("GanReco",this.MODE_PRIVATE);
        ViewGroup.LayoutParams gotoMypageParam = gotoMypage.getLayoutParams();
        if (userProfile.isSaved()) {    // ユーザ情報登録済み
            gotoMypageParam.height = 0;
            gotoMypage.setLayoutParams(gotoMypageParam);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d("GanrecoMain_Lifecycle", "Activity::onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d("GanrecoMain_Lifecycle", "Activity::onStop");
    }
}