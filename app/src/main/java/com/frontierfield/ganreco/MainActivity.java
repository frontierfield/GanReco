package com.frontierfield.ganreco;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
    private EditText email;
    private EditText password;
    private SharedPreferences cache;
    protected int verification = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        try {
            super.onCreate(savedInstanceState);

            // Applicationライフサイクル保護対応
            // Lifecycleを取得し、LifecycleObserverをimplementsしたクラスを渡す
            getLifecycle().addObserver(new ObserveLifecycle());
            Log.d("MainActivity_Lifecycle", "Activity::onCreate");

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
            String inputed_password=null;
            verification = cache.getInt("verification", -1);

            String pass = cache.getString("password", null);

            if (firstInstall == 0) {
                // ウォークスルー画面
                Intent intent = new Intent(MainActivity.this, A1_A2_A3_WalkThrough.class);
                startActivity(new Intent(intent));
                finish();
            }
            else if(inputed_email != null && pass != null){//ログイン状態のデータがあれば
                // パスワード複合化処理
                SecretKeySpec keySpec = new SecretKeySpec("abcdefg098765432".getBytes(), "AES"); // キーファイル生成 暗号化で使った文字列と同様にする
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
            startActivity(new Intent(this,A4_RegistryUser.class));
            finish();
        }
        else if(i == R.id.forgotPass){  // パスワード失念時 A9
            startActivity(new Intent(this,A9_ForgotPassword.class));
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
                SecretKeySpec keySpec=new SecretKeySpec("abcdefg098765432".getBytes(),"AES");
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
            TsuinRirekiRDB.getSavedTsuinRirekiRDB();//releaseで落ちる原因
            OkusuriRirekiRDB.getSavedOkusuriRirekiRDB();
            KensaRirekiRDB.getSavedKensaRirekiRDB();

            // がんレコ　メイン画面　B1B2
            startActivity(new Intent(this,B1_2_GanrecoMain.class));
            finish();
        }
        else { // ユーザー認証失敗
            // If sign in fails, display a message to the user.
            //Log.w(TAG, "signInWithEmail:failure", task.getException());
            Toast.makeText(MainActivity.this, "ログインに失敗しました", Toast.LENGTH_SHORT).show();
            //updateUI(null);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MainActivity_Lifecycle", "Activity::onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("MainActivity_Lifecycle", "Activity::onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MainActivity_Lifecycle", "Activity::onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("MainActivity_Lifecycle", "Activity::onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("MainActivity_Lifecycle", "Activity::onStop");
    }

    //「戻る」ボタン無効化
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction()==KeyEvent.ACTION_DOWN) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_BACK:
                    // ダイアログ表示など特定の処理を行いたい場合はここに記述
                    // 親クラスのdispatchKeyEvent()を呼び出さずにtrueを返す
                    return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }
}
