package com.frontierfield.ganreco;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by kkarimu on 2018/07/09.
 */

public class E3_Input extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    ImageView backBtnHeader;
    TextView helpBtn;
    Spinner time;
    TextView btnCancel;
    TextView btnAdd;
    LinearLayout eraseBtn;
    EditText shinsatsu,hospital;
    DatePicker datePicker;

    UserProfile userProfile;
    Global_Util globalUtil;

    TsuinYotei tsuinYotei;

    int position=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.e3_input);

        userProfile = UserProfile.getInstance();
        globalUtil = new Global_Util();

        backBtnHeader = findViewById(R.id.backE3);
        datePicker=findViewById(R.id.date_pickerE3);

        eraseBtn = findViewById(R.id.eraseDataE3);
        btnCancel = findViewById(R.id.cancelE3);
        btnAdd = findViewById(R.id.addE3);
        hospital = findViewById(R.id.editTextHospNameE3);
        shinsatsu = findViewById(R.id.editTextShinsatsuE3);
        time = findViewById(R.id.spinnerStartTimeE3);
        helpBtn = findViewById(R.id.helpE3);

        ArrayAdapter<String> adapterTime = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,globalUtil.aStartTime);

        time.setAdapter(adapterTime);

        backBtnHeader.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        eraseBtn.setOnClickListener(this);
        helpBtn.setOnClickListener(this);

        LoadTsuinYoteiData();

    }
//e_mainでタップされたとき用
    private void LoadTsuinYoteiData() {
        Intent intent = getIntent();
        position = intent.getIntExtra("positionID",-1);

        if(position == -1){//新規追加

        }else{//通院予定変更したいとき//もともと入ってたデータ表示させる
            tsuinYotei=TsuinYoteiList.getSavedTsuinYotei(position);
            datePicker.init(tsuinYotei.getYear(), tsuinYotei.getMonth(), tsuinYotei.getDay(), new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {

                }
            });
            time.setSelection(tsuinYotei.time);
            hospital.setText(tsuinYotei.hospital);
            shinsatsu.setText(tsuinYotei.detail);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if(i == R.id.backE3){
            finish();
        }else if(i == R.id.cancelE3){
            finish();
        }else if(i == R.id.addE3){
            RegistryData();
        }else if(i == R.id.eraseDataE3){
            EraseData();
        }else if(i == R.id.helpE3){
            startActivity(new Intent(this,d1help.class));
            finish();
        }
    }

    private void RegistryData() {
        if(position == -1) {//新しく追加
            //この時点で保存するデータを、spinnerでの場所の番号にしとくからややこしいことになってる
            tsuinYotei = new TsuinYotei(null,false,hospital.getText().toString(),"",
                    shinsatsu.getText().toString(),datePicker.getYear(), datePicker.getMonth(),
                    datePicker.getDayOfMonth(), time.getSelectedItemPosition());
            tsuinYotei.unixtime = tsuinYotei.calc_unixtime_sec();
        }else{//すでにあるデータ変更
            tsuinYotei.detail = shinsatsu.getText().toString();
            tsuinYotei.hospital = hospital.getText().toString();
            tsuinYotei.year = datePicker.getYear();
            tsuinYotei.month = datePicker.getMonth();
            tsuinYotei.day = datePicker.getDayOfMonth();
            tsuinYotei.time = time.getSelectedItemPosition();
            tsuinYotei.unixtime = tsuinYotei.calc_unixtime_sec();
            TsuinYoteiList.deleteTsuinYotei(position);
        }
        //診察内容が長ければ短くしたやつ表示しとく
        if(shinsatsu.getText().toString().length() < 5){
            tsuinYotei.s_detail = shinsatsu.getText().toString();
        }else {
            tsuinYotei.s_detail = shinsatsu.getText().toString().substring(0, 5);
        }
        TsuinYoteiList.addTsuinYotei(tsuinYotei);
        //ty.tusinData_add();created by kkarimu
        finish();
    }
    private void EraseData(){
        if(position == -1){
            /*新規なら特になにもなし*/
            finish();
        }else {
            TsuinYoteiList.deleteTsuinYotei(position);
            //年月日で最後の一つなら、タグも消す
            finish();
        }
    }
}
