package com.frontierfield.ganreco;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import static com.frontierfield.ganreco.tsuin_yotei.getInstance;

/**
 * Created by kkarimu on 2018/07/09.
 */

public class e3_yotei extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    ImageView backBtnHeader;
    TextView helpBtn;
    Spinner year, month, day, time;
    TextView btnCancel;
    TextView btnAdd;
    LinearLayout eraseBtn;
    EditText shinsatsu,hospital;

    user_profile up;
    Global_Util gu;

    tsuin_yotei ty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.e3_input);

        up=new user_profile();
        gu=new Global_Util();

        backBtnHeader = findViewById(R.id.backE3);
        year = findViewById(R.id.yearE3);
        month = findViewById(R.id.monthE3);
        day = findViewById(R.id.dayE3);

        eraseBtn = findViewById(R.id.eraseDataE3);
        btnCancel = findViewById(R.id.cancelE3);
        btnAdd = findViewById(R.id.addE3);
        hospital = findViewById(R.id.editTextHospNameE3);
        shinsatsu = findViewById(R.id.editTextShinsatsuE3);
        time = findViewById(R.id.spinnerStartTimeE3);
        helpBtn = findViewById(R.id.helpE3);

        ArrayAdapter<Integer> adapterYear = new ArrayAdapter<Integer>(this,R.layout.support_simple_spinner_dropdown_item,gu.aYotei);
        ArrayAdapter<Integer> adapterMonth = new ArrayAdapter<Integer>(this,R.layout.support_simple_spinner_dropdown_item,gu.aMonth);
        ArrayAdapter<Integer> adapterDay = new ArrayAdapter<Integer>(this,R.layout.support_simple_spinner_dropdown_item,gu.aDay);
        ArrayAdapter<String> adapterTime = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,gu.aStartTime);

        year.setAdapter(adapterYear);
        month.setAdapter(adapterMonth);
        day.setAdapter(adapterDay);
        time.setAdapter(adapterTime);

        backBtnHeader.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        eraseBtn.setOnClickListener(this);
        helpBtn.setOnClickListener(this);

        year.setOnItemSelectedListener(this);
        month.setOnItemSelectedListener(this);
        day.setOnItemSelectedListener(this);

        LoadYoteiData();

    }
//e_mainでタップされたとき用？っぽい
    private void LoadYoteiData() {
        /*upからyotei load*/
        Intent intent = getIntent();
        int position = intent.getIntExtra("yotei_id",-1);

        if(position == -1){//新規追加
            year.setSelection(0);
            Calendar now = Calendar.getInstance();
            int nowY = now.get(Calendar.YEAR);
            for(int i = 0;i < gu.aYotei.size();i++){
                if(nowY == i){
                    year.setSelection(i);
                }
            }


        }else{//通院予定変更したいときもともと入ってたデータ表示させる
            ty=tsuin_yotei.getSavedTsuinYotei(position);
            year.setSelection(ty.y_index);
            month.setSelection(ty.m_index);
            day.setSelection(ty.d_index);
            time.setSelection(ty.time);
            hospital.setText(ty.hospital);
            shinsatsu.setText(ty.detail);

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

        if(ty == null) {
            //new add
            //この時点で保存するデータを、spinnerでの場所の番号にしとくからややこしいことになってる
            ty = new tsuin_yotei(null,false,hospital.getText().toString(),"",
                    shinsatsu.getText().toString(), year.getSelectedItemPosition(), month.getSelectedItemPosition(),
                    day.getSelectedItemPosition(), time.getSelectedItemPosition());
            ty.unixtime = ty.calc_unixtime_sec();
            DateTag(ty);
        }else{
            //edit add
            ty.detail = shinsatsu.getText().toString();
            ty.hospital = hospital.getText().toString();
            ty.y_index = year.getSelectedItemPosition();
            ty.m_index = month.getSelectedItemPosition();
            ty.d_index = day.getSelectedItemPosition();
            ty.time = time.getSelectedItemPosition();
            ty.unixtime = ty.calc_unixtime_sec();
            DateTag(ty);
        }
        //診察内容が長ければ短くしたやつ表示しとく
        if(shinsatsu.getText().toString().length() < 5){
            ty.s_detail = shinsatsu.getText().toString();
        }else {
            ty.s_detail = shinsatsu.getText().toString().substring(0, 5);
        }
        tsuin_yotei.addTsuinYotei(ty);
        //List<tsuin_yotei> tylist=tsuin_yotei.getInstance();//singleton
        //tylist.add(ty);
        //ty.tusinData_add();created by kkarimu
        finish();
    }
    private void EraseData(){
        if(ty.ID != null){
            tsuin_yotei.deleteTsuinYotei(ty);
            finish();
        }else {
            /*新規なら特になにもなし*/
            finish();
        }
    }
    private void DateTag(tsuin_yotei ty){//同じ年月日のデータがないかどうかチェック
        //一番最初のタグが付かない問題
        //値渡し参照渡しの沼
        List<tsuin_yotei> tylist=tsuin_yotei.getInstance();
        tsuin_yotei tyTag=new tsuin_yotei(null,true,hospital.getText().toString(),"",
                shinsatsu.getText().toString(), year.getSelectedItemPosition(), month.getSelectedItemPosition(),
                day.getSelectedItemPosition(), time.getSelectedItemPosition());//値コピーの方法。
        if(tylist.size()==0)tylist.add(tyTag);
        for(int i=0;i<tylist.size();i++){
            if(tylist.get(i).y_index!=ty.y_index||tylist.get(i).m_index!=ty.m_index||tylist.get(i).d_index!=ty.d_index){
                tylist.add(tyTag);
                break;
            }
        }
        //タグになるlistをadd<-ほんとはtrueだったらタグもつけるってしたかったけどlistviewでそれはなんかできなさそうだった
    }
}
