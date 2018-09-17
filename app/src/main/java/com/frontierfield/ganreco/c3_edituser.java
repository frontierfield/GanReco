package com.frontierfield.ganreco;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

/**
 * Created by kkarimu on 2018/06/16.
 */

public class c3_edituser extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    ImageView backBtnHeader;
    TextView helpBtn;
    ImageView avater;
    EditText lastName;
    EditText firstName;
    Spinner year, month, day, sex;
    EditText zipfront;
    EditText ziprear;
    EditText address;
    TextView btnCancel;
    TextView btnAdd;

    user_profile up;
    Global_Util gu;

    int y = -1 ,m = -1, d = -1,sexid = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.c3);

        up = new user_profile();
        gu = new Global_Util();

        backBtnHeader = findViewById(R.id.backE10N1);
        helpBtn = findViewById(R.id.helpE10N1);
        avater = findViewById(R.id.avaterUserE10N1);
        lastName = findViewById(R.id.lastnameE10N1);
        firstName = findViewById(R.id.firstnameE10N1);
        year = findViewById(R.id.yearE10N1);
        month = findViewById(R.id.monthE10N1);
        day = findViewById(R.id.dayE10N1);
        sex = findViewById(R.id.sexE10N1);
        zipfront = findViewById(R.id.zipfrontE10N1);
        ziprear = findViewById(R.id.ziprearE10N1);
        address = findViewById(R.id.addressE10N1);
        btnCancel = findViewById(R.id.cancelE10_N1);
        btnAdd = findViewById(R.id.addE10_N1);

        ArrayAdapter<Integer> adapterYear = new ArrayAdapter<Integer>(this,R.layout.support_simple_spinner_dropdown_item,gu.aYear);
        ArrayAdapter<Integer> adapterMonth = new ArrayAdapter<Integer>(this,R.layout.support_simple_spinner_dropdown_item,gu.aMonth);
        ArrayAdapter<Integer> adapterDay = new ArrayAdapter<Integer>(this,R.layout.support_simple_spinner_dropdown_item,gu.aDay);
        ArrayAdapter<String> adapterSex = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,gu.aSex);
        year.setAdapter(adapterYear);
        month.setAdapter(adapterMonth);
        day.setAdapter(adapterDay);
        sex.setAdapter(adapterSex);

        backBtnHeader.setOnClickListener(this);
        helpBtn.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnAdd.setOnClickListener(this);

        year.setOnItemSelectedListener(this);
        month.setOnItemSelectedListener(this);
        day.setOnItemSelectedListener(this);
        sex.setOnItemSelectedListener(this);

        LoadUserData();

    }

    private void LoadUserData() {
        lastName.setText(up.lastName);
        firstName.setText(up.firstName);
        zipfront.setText(up.zipfront);
        ziprear.setText(up.ziprear);
        address.setText(up.address);
        if(up.year_Index != -1 && up.month_Index != -1 && up.day_Index != -1 && up.sex_Index != -1){
            year.setSelection(up.year_Index);
            month.setSelection(up.month_Index);
            day.setSelection(up.day_Index);
            sex.setSelection(up.sex_Index);
        }
        if(up.sex_Index == 0){
            avater.setImageResource(R.drawable.icon_father);
        }else{
            avater.setImageResource(R.drawable.icon_mother);
        }
    }



    @Override
    public void onClick(View v) {
        int i = v.getId();
        if(i == R.id.backE10N1){
            finish();
        }else if(i == R.id.helpE10N1){
            startActivity(new Intent(this,d1help.class));
            finish();
        }else if(i == R.id.cancelE10_N1){
            finish();
        }else if(i == R.id.addE10_N1){
            RegistryUserData();
            user_profile.isSave = true;
        }
    }

    private void RegistryUserData() {
        y = year.getSelectedItemPosition();
        m = month.getSelectedItemPosition();
        d = day.getSelectedItemPosition();
        sexid = sex.getSelectedItemPosition();

        FirebaseUser mAuthUser;
        mAuthUser = FirebaseAuth.getInstance().getCurrentUser();
        up.UID = mAuthUser.getUid();
        up.lastName = lastName.getText().toString();
        up.firstName = firstName.getText().toString();
        up.year_Index = y;
        up.month_Index = m;
        up.day_Index = d;
        up.sex_Index = sexid;
        up.zipfront = zipfront.getText().toString();
        up.ziprear = ziprear.getText().toString();
        up.address = address.getText().toString();

        if(up.lastName == null || up.firstName == null || y == -1 || m == -1 || d == -1 || sexid == -1){
            Toast.makeText(c3_edituser.this, "未入力の項目があります",
                    Toast.LENGTH_SHORT).show();
        }else {
            SharedPreferences cache = this.getSharedPreferences("GanReco",this.MODE_PRIVATE);
            SharedPreferences.Editor editor = cache.edit();
            editor.putInt("regiUser",1);
            editor.commit();

            user_profile_realtimedatabase upr = new user_profile_realtimedatabase(up);
            upr.user_profile_add();
            finish();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent){

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        /**
        int i = view.getId();
        if(i == R.id.yearE10N1) {
            y = position;
            Log.d("year",""+gu.aYear.get(position)+"");
        }else if(i == R.id.monthE10N1){
            m = position;
            Log.d("month",""+gu.aMonth[position]+"");
        }else if(i == R.id.dayE10N1){
            d = position;
            Log.d("day",""+gu.aDay[position]+"");
        }else if(i == R.id.sexE10N1){
            sexid = position;
            Log.d("sex",""+gu.aSex[position]+"");
        }
         **/


    }
}
