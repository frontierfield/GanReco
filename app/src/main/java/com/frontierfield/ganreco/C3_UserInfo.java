package com.frontierfield.ganreco;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class C3_UserInfo extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    ImageView backBtnHeader;
    TextView helpBtn;
    ImageView avater;
    EditText lastName;
    EditText firstName;
    Spinner year, month, day, sex;
    EditText zipfront;
    EditText ziprear;
    EditText address;
    EditText tel;
    Spinner cancerType;    // がんの種類
    TextView btnCancel;
    TextView btnSave;

    UserProfile userProfile;
    Global_Util globalUtil;
    CancerTypeList cancerTypeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c3);

        try {
            userProfile = UserProfile.getInstance();
            globalUtil = new Global_Util();
            cancerTypeList = CancerTypeList.getInstance();

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
            tel = findViewById(R.id.telE10N1);
            cancerType = findViewById(R.id.cancerTypeE10N);
            btnCancel = findViewById(R.id.cancelE10_N1);
            btnSave = findViewById(R.id.saveE10_N1);

            String strCancerTypeList[] = new String[cancerTypeList.getList().size()];
            for (int i = 0; i < cancerTypeList.getList().size(); i++) {
                strCancerTypeList[i] = cancerTypeList.getList().get(i).getStrCancerName();
            }

            ArrayAdapter<Integer> adapterYear = new ArrayAdapter<Integer>(this, R.layout.support_simple_spinner_dropdown_item, globalUtil.aYear);
            ArrayAdapter<Integer> adapterMonth = new ArrayAdapter<Integer>(this, R.layout.support_simple_spinner_dropdown_item, globalUtil.aMonth);
            ArrayAdapter<Integer> adapterDay = new ArrayAdapter<Integer>(this, R.layout.support_simple_spinner_dropdown_item, globalUtil.aDay);
            ArrayAdapter<String> adapterSex = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, globalUtil.aSex);
            ArrayAdapter<String> adapterCancerType = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, strCancerTypeList);
            year.setAdapter(adapterYear);
            month.setAdapter(adapterMonth);
            day.setAdapter(adapterDay);
            sex.setAdapter(adapterSex);
            cancerType.setAdapter(adapterCancerType);

            backBtnHeader.setOnClickListener(this);
            helpBtn.setOnClickListener(this);
            btnCancel.setOnClickListener(this);
            btnSave.setOnClickListener(this);

            year.setOnItemSelectedListener(this);
            month.setOnItemSelectedListener(this);
            day.setOnItemSelectedListener(this);
            sex.setOnItemSelectedListener(this);
            cancerType.setOnItemSelectedListener(this);

            LoadUserData();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void LoadUserData() {
        try {
            lastName.setText(userProfile.getLastName());
            firstName.setText(userProfile.getFirstName());
            zipfront.setText(userProfile.getZipfront());
            ziprear.setText(userProfile.getZiprear());
            address.setText(userProfile.getAddress());
            tel.setText(userProfile.getTel());

            if (userProfile.getYear_Index() != -1 && userProfile.getMonth_Index() != -1 && userProfile.getDay_Index() != -1 && userProfile.getSex_Index() != -1) {
                year.setSelection(userProfile.getYear_Index());
                month.setSelection(userProfile.getMonth_Index());
                day.setSelection(userProfile.getDay_Index());
                sex.setSelection(userProfile.getSex_Index());
            }

            if (userProfile.getSex_Index() == 0) {
                avater.setImageResource(R.drawable.icon_father);
            }
            else {
                avater.setImageResource(R.drawable.icon_mother);
            }

            cancerType.setSelection(cancerTypeList.getSelectIndex());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        try {
            int i = v.getId();
            if (i == R.id.backE10N1) {
                finish();
            }
            else if (i == R.id.helpE10N1) {
                startActivity(new Intent(this, d1help.class));
                finish();
            }
            else if (i == R.id.cancelE10_N1) {
                finish();
            }
            else if (i == R.id.saveE10_N1) {
                RegistryUserData();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void RegistryUserData() {
        try {
            int y = year.getSelectedItemPosition();
            int m = month.getSelectedItemPosition();
            int d = day.getSelectedItemPosition();
            int sexId = sex.getSelectedItemPosition();
            int cancerId = cancerType.getSelectedItemPosition();

            FirebaseUser firebaseUser;
            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            userProfile.setUID(firebaseUser.getUid());
            userProfile.setEmail(firebaseUser.getEmail());
            userProfile.setLastName(lastName.getText().toString());
            userProfile.setFirstName(firstName.getText().toString());
            userProfile.setYear_Index(y);
            userProfile.setMonth_Index(m);
            userProfile.setDay_Index(d);
            userProfile.setSex_Index(sexId);
            userProfile.setZipfront(zipfront.getText().toString());
            userProfile.setZiprear(ziprear.getText().toString());
            userProfile.setAddress(address.getText().toString());
            userProfile.setTel(tel.getText().toString());
            userProfile.setCancerType(cancerTypeList.getList().get(cancerId).getStrCancerName());

            if (userProfile.getLastName().isEmpty() || userProfile.getFirstName().isEmpty()) {
                Toast.makeText(C3_UserInfo.this, "未入力の項目があります",
                        Toast.LENGTH_SHORT).show();
            }
            else {
                SharedPreferences cache = this.getSharedPreferences("GanReco", this.MODE_PRIVATE);
                SharedPreferences.Editor editor = cache.edit();
                editor.putInt("regiUser", 1);
                editor.commit();

                cancerTypeList.setSelectIndex(cancerId);    // データ更新
                userProfile.setSaved(true);
                UserProfileRDB userProfileRDB = new UserProfileRDB();
                userProfileRDB.user_profile_add();
                finish();
            }
        }
        catch (Exception e){
            e.printStackTrace();
            finish();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent){}

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {}
}
