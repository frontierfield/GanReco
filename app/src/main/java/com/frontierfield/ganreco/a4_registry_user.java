package com.frontierfield.ganreco;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Debug;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthRegistrar;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class a4_registry_user extends AppCompatActivity implements View.OnClickListener {

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

        TextView btnLogin = findViewById(R.id.btnRegNewUser_A4);
        TextView btnb1 = findViewById(R.id.toLoginDisp_A4);
        btnLogin.setOnClickListener(this);
        btnb1.setOnClickListener(this);
    }

    private void createAccount(String email, String password) {

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "createUserWithEmail:success");
                            firebaseUser = firebaseAuth.getCurrentUser();
                            Objects.requireNonNull(firebaseUser).sendEmailVerification();

                            SharedPreferences.Editor editor = cache.edit();
                            editor.putString("email",inputEmail.getText().toString());
                            editor.putString("password",inputPassword.getText().toString());
                            editor.putInt("firstInstall",1);
                            editor.commit();

                            startActivity(new Intent(a4_registry_user.this,a5_registry_precomp.class));
                            //firebaseUser.reload();
                        }
                        else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(a4_registry_user.this, "アカウント作成に失敗しました",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btnRegNewUser_A4) {
            createAccount(inputEmail.getText().toString(), inputPassword.getText().toString());
        }
        //else if(i == R.id.backB2){
        else{
            SharedPreferences.Editor editor = cache.edit();
            editor.putInt("firstInstall",1);
            editor.commit();
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }
    }
}
