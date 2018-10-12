package com.frontierfield.ganreco;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class A9_ForgotPassword extends AppCompatActivity implements View.OnClickListener, OnCompleteListener {
    FirebaseAuth mAuth;
    String inputed_email;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences cache = this.getSharedPreferences("GanReco",this.MODE_PRIVATE);
        inputed_email = cache.getString("email",null);
        mAuth = FirebaseAuth.getInstance();

        setContentView(R.layout.a9);

        TextView btnSendmail = findViewById(R.id.sendmailForget);
        EditText emailForget = findViewById(R.id.editEmailForget);
        TextView btnBack = findViewById(R.id.backForget);

        emailForget.setText(inputed_email);

        btnSendmail.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.sendmailForget) { // パスワードリセット
            mAuth.sendPasswordResetEmail(inputed_email).addOnCompleteListener(this);
        }
        else if(i == R.id.backForget){
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }
    }

    @Override
    public void onComplete(@NonNull Task task) {
            if (task.isSuccessful()) {
                startActivity(new Intent(this,MainActivity.class));
                Toast.makeText(A9_ForgotPassword.this, "メールを送信しました",
                        Toast.LENGTH_SHORT).show();
                finish();
            } else {
                // If sign in fails, display a message to the user.
                //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                Toast.makeText(A9_ForgotPassword.this, "メールの送信に失敗しました",
                        Toast.LENGTH_SHORT).show();
                //updateUI(null);
            }
    }
}
