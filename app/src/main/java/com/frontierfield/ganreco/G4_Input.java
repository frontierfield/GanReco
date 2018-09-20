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

public class G4_Input extends AppCompatActivity implements View.OnClickListener {
    ImageView backBtnHeader , ImageViewSinryo;
    TextView helpBtn;
    Spinner year, month, day;
    TextView btnCancel;
    TextView btnAdd;
    LinearLayout eraseBtn;
    EditText okusuri,drugstore;

    UserProfile up;
    Global_Util gu;

    OkusuriRireki okusuriRireki;
    int position=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.g4_input);

        up=new UserProfile();
        gu=new Global_Util();

        backBtnHeader = findViewById(R.id.backG4);
        year = findViewById(R.id.yearG4);
        month = findViewById(R.id.monthG4);
        day = findViewById(R.id.dayG4);

        eraseBtn = findViewById(R.id.eraseDataG4);
        btnCancel = findViewById(R.id.cancelG4);
        btnAdd = findViewById(R.id.addG4);
        drugstore = findViewById(R.id.editTextYakkyokuNameG4);
        okusuri = findViewById(R.id.editTextSyohouG4);
        ImageViewSinryo = findViewById(R.id.imageViewSinryoG4);

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
        ImageViewSinryo.setOnClickListener(this);

        LoadOkusuriData();
    }

    private void LoadOkusuriData() {
         /*upからyotei load*/
        Intent intent = getIntent();
        position = intent.getIntExtra("OkusuriRirekiID",-1);

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
            okusuriRireki=OkusuriRirekiList.getSavedOkusuriRireki(position);
            year.setSelection(okusuriRireki.y_index);
            month.setSelection(okusuriRireki.m_index);
            day.setSelection(okusuriRireki.d_index);
            drugstore.setText(okusuriRireki.drugstore);
            okusuri.setText(okusuriRireki.detail);
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if(i == R.id.backG4){
            //goto efgh
            finish();
        }else if(i == R.id.cancelG4){
            //goto efgh
            finish();
        }else if(i == R.id.addG4){
            RegistryData();
        }else if(i == R.id.eraseDataG4){
            EraseData();
        }else if(i == R.id.imageViewSinryoG4){
            //focus
        }
    }

    private void RegistryData() {
        if(position == -1) {//新しく追加
            //この時点で保存するデータを、spinnerでの場所の番号にしとくからややこしいことになってる
            okusuriRireki = new OkusuriRireki(null,false,drugstore.getText().toString(),
                    okusuri.getText().toString(), year.getSelectedItemPosition(), month.getSelectedItemPosition(),
                    day.getSelectedItemPosition());
        }else{//すでにあるデータ変更
            okusuriRireki.detail = okusuri.getText().toString();
            okusuriRireki.drugstore = drugstore.getText().toString();
            okusuriRireki.y_index = year.getSelectedItemPosition();
            okusuriRireki.m_index = month.getSelectedItemPosition();
            okusuriRireki.d_index = day.getSelectedItemPosition();
            OkusuriRirekiList.deleteOkusuriRireki(position);
        }
        OkusuriRirekiList.addOkusuriRireki(okusuriRireki);
        finish();
    }
    private void EraseData(){
        if(position == -1){
            /*新規なら特になにもなし*/
            finish();
        }else {
            OkusuriRirekiList.deleteOkusuriRireki(position);
            //年月日で最後の一つなら、タグも消す
            finish();
        }
    }
}
