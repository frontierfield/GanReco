package com.frontierfield.ganreco;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class a7_registry_comp extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a7);

        TextView btn = findViewById(R.id.buttonLoginB5);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(getApplicationContext(),b1_2mainmenu.class));
        finish();
    }
}
