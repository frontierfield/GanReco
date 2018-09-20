package com.frontierfield.ganreco;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

// エントリーポイント
public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnCompleteListener {

    private FirebaseAnalytics firebaseAnalytics;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private String uid;
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
            uid=firebaseUser.getUid().toString();

            Bundle analyticsData = new Bundle();
            analyticsData.putString("Event_type", "App_open");

            firebaseAnalytics.logEvent("CustomEvent", analyticsData);


            cache = this.getSharedPreferences("GanReco", this.MODE_PRIVATE);    // ユーザ毎のキャッシュ格納
            ///////////
            //cache.edit().remove("firstInstall").commit();
            //////////
            int firstInstall = cache.getInt("firstInstall", 0);        // インストール時＝０
            final String inputed_email = cache.getString("email", null);
            String inputed_password=null;
            verification = cache.getInt("verification", -1);

            String pass = cache.getString("pass", null);

            // パスワード複合化処理
            SecretKeySpec keySpec = new SecretKeySpec(uid.getBytes(), "AES"); // キーファイル生成 暗号化で使った文字列と同様にする
            Cipher cipher = null;
            try {
                cipher = Cipher.getInstance("AES");
                cipher.init(Cipher.DECRYPT_MODE, keySpec);
                byte[] decByte = Base64.decode(pass, Base64.DEFAULT); // byte配列にデコード
                byte[] decrypted = cipher.doFinal(decByte); // 複合化
                inputed_password = new String(decrypted); // Stringに変換
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            }

            if (firstInstall == 0) {
                // ウォークスルー画面
                mGestureDetector = new GestureDetector(this, mOnGestureListener);
                Intent intent = new Intent(MainActivity.this, a1_sokyuFragment.class);
                startActivity(new Intent(intent));
                finish();
                //firebaseUser.reload();
            }
            else if(inputed_email != null && inputed_password != null){//ログイン状態のデータがあれば
                firebaseAuth.signInWithEmailAndPassword(inputed_email, inputed_password).addOnCompleteListener(this);
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
        //ログイン成功時
        if (task.isSuccessful()) {
            if(cache.getInt("verification",-1)!=2) {
                verification = cache.getInt("verification", -1);
                SharedPreferences.Editor editor = cache.edit();
                //パスワードの暗号化一応しとく
                SecretKeySpec keySpec=new SecretKeySpec(uid.getBytes(),"AES");
                try {
                    Cipher cipher=Cipher.getInstance("AES");
                    cipher.init(Cipher.ENCRYPT_MODE,keySpec);
                    byte[] encrypted=cipher.doFinal(password.getText().toString().getBytes());
                    String pass= Base64.encodeToString(encrypted,Base64.DEFAULT);
                    editor.putString("password", pass);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
                }

                editor.putString("email", email.getText().toString());
                editor.putInt("firstInstall", 1);
                editor.putInt("verification", 2);
                editor.commit();
            }
            //userProfileの初期化
            UserProfileRDB upr = new UserProfileRDB();
            upr.get_user_profile_and_input_static();
            //保存されてるデータもってくる
            TsuinYoteiRDB.getSavedTsuinYoteiRDB();
            TsuinRirekiRDB.getSavedTsuinRirekiRDB();
            OkusuriRirekiRDB.getSavedOkusuriRirekiRDB();
            KensaRirekiRDB.getSavedKensaRirekiRDB();

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
