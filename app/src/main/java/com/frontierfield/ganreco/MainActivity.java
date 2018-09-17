package com.frontierfield.ganreco;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.hotspot2.pps.Credential;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AdditionalUserInfo;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthRegistrar;
import com.google.firebase.auth.FirebaseUser;

import java.util.Map;

// エントリーポイント
public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnCompleteListener {

    private FirebaseAnalytics firebaseAnalytics;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private EditText email;
    private EditText password;
    private int verification = -1;
    private SharedPreferences cache;

    // X軸最低スワイプ距離
    private static final int SWIPE_MIN_DISTANCE = 50;
    // X軸最低スワイプスピード
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    // タッチイベントを処理するためのインタフェース
    private GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        try {
            super.onCreate(savedInstanceState);

            firebaseAnalytics = FirebaseAnalytics.getInstance(this);
            firebaseAuth = FirebaseAuth.getInstance();
            firebaseUser = firebaseAuth.getCurrentUser();

            Bundle analyticsData = new Bundle();
            analyticsData.putString("Event_type", "App_open");

            firebaseAnalytics.logEvent("CustomEvent", analyticsData);

            cache = this.getSharedPreferences("GanReco", this.MODE_PRIVATE);    // ユーザ毎のキャッシュ格納
            ///////////
            //cache.edit().remove("firstInstall").commit();
            //////////
            int firstInstall = cache.getInt("firstInstall", 0);        // インストール時＝０
            final String inputed_email = cache.getString("email", null);
            final String inputed_password = cache.getString("password", null);
            verification = cache.getInt("verification", -1);

            if (firstInstall == 0) {
                // ウォークスルー画面
                mGestureDetector = new GestureDetector(this, mOnGestureListener);
                Intent intent = new Intent(MainActivity.this, a1_sokyuFragment.class);
                startActivity(new Intent(intent));
                finish();
                //firebaseUser.reload();
            }
            else {
                setContentView(R.layout.a8);
                email = findViewById(R.id.editEmailB1);
                password = findViewById(R.id.editPasswordB1);

                TextView btnLogin = findViewById(R.id.buttonLogin);
                TextView btnForgotPassword = findViewById(R.id.forgotPass);
                TextView btnSignUp = findViewById(R.id.signUP);

                email.setText(inputed_email);
                password.setText(inputed_password);

                btnLogin.setOnClickListener(this);
                btnForgotPassword.setOnClickListener(this);
                btnSignUp.setOnClickListener(this);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.signUP) { // 新規ユーザー登録 A4
            startActivity(new Intent(this,a4_registry_user.class));
            finish();
        }
        else if(i == R.id.forgotPass){  // パスワード失念時 A9
            startActivity(new Intent(this,a9_forgotpassword.class));
            finish();
        }
        else if(i == R.id.buttonLogin){
            // ログイン
            String strEmail = email.getText().toString();
            String strPassword = password.getText().toString();
            if(strEmail != null && strPassword != null) {
                firebaseAuth.signInWithEmailAndPassword(strEmail, strPassword).addOnCompleteListener(this);
            }
        }
    }

    @Override
    public void onComplete(@NonNull Task task) {
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseUser.reload();
        boolean b = firebaseUser.isEmailVerified();

        if (!firebaseUser.isEmailVerified()) {
            Toast.makeText(MainActivity.this, "ユーザー登録を行ってください", Toast.LENGTH_SHORT).show();
            firebaseUser.delete();
            return;
        }

        if (task.isSuccessful()) {
            verification = cache.getInt("verification",-1);
            SharedPreferences.Editor editor = cache.edit();
            editor.putString("email",email.getText().toString());
            editor.putString("password",password.getText().toString());
            editor.putInt("firstInstall",1);
            editor.putInt("verification",2);
            editor.commit();

            //userProfileの初期化
            user_profile_realtimedatabase upr = new user_profile_realtimedatabase();
            upr.get_user_profile_and_input_static();
            //shinsatsu
            tsuin_yotei ty = new tsuin_yotei();
            ty.get_tsuinData_and_input_static();

            // がんレコ　メイン画面　B1B2
            startActivity(new Intent(this,b1_2mainmenu.class));
            finish();
        }
        else { // ユーザー認証失敗
            // If sign in fails, display a message to the user.
            //Log.w(TAG, "signInWithEmail:failure", task.getException());
            Toast.makeText(MainActivity.this, "ログインに失敗しました", Toast.LENGTH_SHORT).show();
            //updateUI(null);
        }
    }

    // タッチイベントのリスナー
    private final GestureDetector.SimpleOnGestureListener mOnGestureListener = new GestureDetector.SimpleOnGestureListener() {
        // フリックイベント
        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
            try {
                // 移動距離・スピードを出力
                float distance_x = Math.abs((event1.getX() - event2.getX()));
                float velocity_x = Math.abs(velocityX);

                // 開始位置から終了位置の移動距離が指定値より大きい
                // X軸の移動速度が指定値より大きい
                if  (event1.getX() - event2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    //textView2.setText("右から左");
                }
                // 終了位置から開始位置の移動距離が指定値より大きい
                // X軸の移動速度が指定値より大きい
                else if (event2.getX() - event1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    //textView2.setText("左から右");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
    };
}
