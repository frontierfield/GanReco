package com.frontierfield.ganreco;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class A4_RegistryUser extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private EditText inputEmail;
    private EditText inputPassword;
    private SharedPreferences cache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a4);

        firebaseAuth = FirebaseAuth.getInstance();
        cache = this.getSharedPreferences("GanReco", this.MODE_PRIVATE);
        inputEmail = findViewById(R.id.editEmailB2);
        inputPassword = findViewById(R.id.editPasswordB2);

        TextView btnRegister = findViewById(R.id.btnRegNewUser_A4);
        TextView btnToLogin = findViewById(R.id.btnToLoginA8);
        btnRegister.setOnClickListener(this);
        btnToLogin.setOnClickListener(this);
    }

    private void createAccount(String email, String password) {

        try {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                firebaseUser = firebaseAuth.getCurrentUser();
                                Objects.requireNonNull(firebaseUser).sendEmailVerification();

                                SharedPreferences.Editor editor = cache.edit();
                                editor.putString("email", inputEmail.getText().toString());
                                editor.putString("password", inputPassword.getText().toString());
                                editor.putInt("firstInstall", 1);
                                editor.commit();

                                startActivity(new Intent(A4_RegistryUser.this, A5_RegistryPrecomplete.class));
                                finish();
                            }
                            else {
                                Toast.makeText(A4_RegistryUser.this, "アカウント作成に失敗しました",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btnRegNewUser_A4) {
            createAccount(inputEmail.getText().toString(), inputPassword.getText().toString());
        }
        else if(i == R.id.btnToLoginA8){
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }
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
