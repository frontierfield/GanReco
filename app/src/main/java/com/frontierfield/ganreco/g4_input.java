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

public class g4_input extends AppCompatActivity implements View.OnClickListener {
    ImageView backBtnHeader , ImageViewSinryo;
    TextView helpBtn;
    Spinner year, month, day;
    TextView btnCancel;
    TextView btnAdd;
    LinearLayout eraseBtn;
    EditText syohou,hospital;

    user_profile up;
    Global_Util gu;

    syohou_rireki sr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.g4_input);

        up=new user_profile();
        gu=new Global_Util();

        backBtnHeader = findViewById(R.id.backG4);
        year = findViewById(R.id.yearG4);
        month = findViewById(R.id.monthG4);
        day = findViewById(R.id.dayG4);

        eraseBtn = findViewById(R.id.eraseDataG4);
        btnCancel = findViewById(R.id.cancelG4);
        btnAdd = findViewById(R.id.addG4);
        hospital = findViewById(R.id.editTextYakkyokuNameG4);
        syohou = findViewById(R.id.editTextSyohouG4);
        ImageViewSinryo = findViewById(R.id.imageViewSinryoF4);

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
        ImageViewSinryo.setOnClickListener(this);

        LoadSyohouData();
    }

    private void LoadSyohouData() {
         /*upからyotei load*/
        Intent intent = getIntent();
        String syohou_id = intent.getStringExtra("syohou_id");

        if(syohou_id == null){

        }else{

        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if(i == R.id.backE3){
            //goto efgh
            finish();
        }else if(i == R.id.cancelE3){
            //goto efgh
            finish();
        }else if(i == R.id.addE3){
            RegistryData();
        }else if(i == R.id.eraseDataE3){
            EraseData();
        }else if(i == R.id.imageViewSinryoF4){
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
