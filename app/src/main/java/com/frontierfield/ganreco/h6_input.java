package com.frontierfield.ganreco;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by kkarimu on 2018/07/12.
 */

public class h6_input extends AppCompatActivity implements View.OnClickListener {
    ImageView backBtnHeader , ImageViewKensa;
    TextView helpBtn;
    Spinner year, month, day;
    TextView btnCancel;
    TextView btnAdd;
    LinearLayout eraseBtn;
    EditText kensa,shisetsu;

    UserProfile up;
    Global_Util gu;

    kensa_rireki kr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.h6_input);

        up=new UserProfile();
        gu=new Global_Util();

        backBtnHeader = findViewById(R.id.backH6);
        year = findViewById(R.id.yearH6);
        month = findViewById(R.id.monthH6);
        day = findViewById(R.id.dayH6);

        eraseBtn = findViewById(R.id.eraseDataH6);
        btnCancel = findViewById(R.id.cancelH6);
        btnAdd = findViewById(R.id.addH6);
        shisetsu = findViewById(R.id.editTextKensaNameH6);
        kensa = findViewById(R.id.editTextKensaH6);
        ImageViewKensa = findViewById(R.id.imageViewKensaH6);

        ArrayAdapter<Integer> adapterYear = new ArrayAdapter<Integer>(this,R.layout.support_simple_spinner_dropdown_item,gu.aYear);
        ArrayAdapter<Integer> adapterMonth = new ArrayAdapter<Integer>(this,R.layout.support_simple_spinner_dropdown_item,gu.aMonth);
        ArrayAdapter<Integer> adapterDay = new ArrayAdapter<Integer>(this,R.layout.support_simple_spinner_dropdown_item,gu.aDay);

        year.setAdapter(adapterYear);
        month.setAdapter(adapterMonth);
        day.setAdapter(adapterDay);

        backBtnHeader.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        eraseBtn.setOnClickListener(this);
        ImageViewKensa.setOnClickListener(this);

        LoadKensaData();
    }

    private void LoadKensaData() {
         /*upからkensa load*/
        Intent intent = getIntent();
        String kensa_id = intent.getStringExtra("kensa_id");

        if(kensa_id == null){

        }else{

        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if(i == R.id.backH6){
            //goto efgh
            finish();
        }else if(i == R.id.cancelH6){
            //goto efgh
            finish();
        }else if(i == R.id.addH6){
            RegistryData();
        }else if(i == R.id.eraseDataH6){
            EraseData();
        }else if(i == R.id.imageViewKensaH6){
            //focus
        }
    }

    private void EraseData() {
        finish();
    }

    private void RegistryData() {
        finish();
    }
}
