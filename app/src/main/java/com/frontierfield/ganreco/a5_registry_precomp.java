package com.frontierfield.ganreco;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//「メール起動」ボタンで強制的にGmailを開く処理不要のためクリックイベント削除。
public class a5_registry_precomp extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences cache;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a5);

        TextView btnNext = findViewById(R.id.buttonNext);
        btnNext.setOnClickListener(this);

        SharedPreferences cache = this.getSharedPreferences("GanReco", this.MODE_PRIVATE);
        SharedPreferences.Editor editor = cache.edit();
        editor.putInt("firstInstall", 1);
        editor.commit();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        try {
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            firebaseUser.reload();

            if (i == R.id.buttonNext) {
                if (firebaseUser.isEmailVerified()) {
                    cache = this.getSharedPreferences("GanReco", this.MODE_PRIVATE);
                    SharedPreferences.Editor editor = cache.edit();
                    editor.putInt("firstInstall",1);
                    editor.commit();
                    // 会員登録完了 A7
                    startActivity(new Intent(this, a7_registry_comp.class));
                    finish();
                }
                else{
                    Toast.makeText(a5_registry_precomp.this, "メールで受信したリンクをクリックしてください",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    // タッチイベント
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        try {
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            firebaseUser.reload();

            boolean flg = firebaseUser.isEmailVerified();
            if (firebaseUser.isEmailVerified()) {
                // 会員登録完了 A7
                startActivity(new Intent(this, a7_registry_comp.class));
                finish();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    */
}
