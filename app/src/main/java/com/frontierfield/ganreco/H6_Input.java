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

import java.util.Calendar;

/**
 * Created by kkarimu on 2018/07/12.
 */

public class H6_Input extends AppCompatActivity implements View.OnClickListener {
    ImageView backBtnHeader , ImageViewKensa;
    TextView helpBtn;
    Spinner year, month, day;
    TextView btnCancel;
    TextView btnAdd;
    LinearLayout eraseBtn;
    EditText kensa,hospital;

    UserProfile up;
    Global_Util gu;

    KensaRireki kensaRireki;
    int position=-1;
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
        hospital = findViewById(R.id.editTextKensaNameH6);
        kensa = findViewById(R.id.editTextKensaH6);
        ImageViewKensa = findViewById(R.id.imageViewKensaH6);

        ArrayAdapter<Integer> adapterYear = new ArrayAdapter<Integer>(this,R.layout.support_simple_spinner_dropdown_item,gu.aYotei);
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
        Intent intent = getIntent();
        position = intent.getIntExtra("KensaRirekiID",-1);

        if(position == -1){//新規追加
            year.setSelection(0);
            Calendar now = Calendar.getInstance();
            int nowY = now.get(Calendar.YEAR);
            for(int i = 0;i < gu.aYotei.size();i++){//????
                if(nowY == i){
                    year.setSelection(i);
                }
            }
        }else{//通院予定変更したいとき//もともと入ってたデータ表示させる
            kensaRireki=KensaRirekiList.getSavedKensaRireki(position);
            year.setSelection(kensaRireki.y_index);
            month.setSelection(kensaRireki.m_index);
            day.setSelection(kensaRireki.d_index);
            hospital.setText(kensaRireki.hospital);
            kensa.setText(kensaRireki.detail);
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

    private void RegistryData() {
        if(position == -1) {//新しく追加
            //この時点で保存するデータを、spinnerでの場所の番号にしとくからややこしいことになってる
            kensaRireki = new KensaRireki(null,false,hospital.getText().toString(),
                    kensa.getText().toString(), year.getSelectedItemPosition(), month.getSelectedItemPosition(),
                    day.getSelectedItemPosition());
        }else{//すでにあるデータ変更
            kensaRireki.detail = kensa.getText().toString();
            kensaRireki.hospital = hospital.getText().toString();
            kensaRireki.y_index = year.getSelectedItemPosition();
            kensaRireki.m_index = month.getSelectedItemPosition();
            kensaRireki.d_index = day.getSelectedItemPosition();
            KensaRirekiList.deleteKensaRireki(position);
        }
        KensaRirekiList.addKensaRireki(kensaRireki);
        finish();
    }
    private void EraseData(){
        if(position == -1){
            /*新規なら特になにもなし*/
            finish();
        }else {
            KensaRirekiList.deleteKensaRireki(position);
            //年月日で最後の一つなら、タグも消す
            finish();
        }
    }
}
