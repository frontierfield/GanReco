package com.frontierfield.ganreco;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

public class B0_CancerTypeSelect extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    Spinner cancerSpinner;    // がんの種類
    TextView button;

    ArrayAdapter<String> arrayAdapter;
    CancerType cancerType;
    String strList[];
    CancerTypeList staticList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b0_cancertypeselect);
        button = findViewById(R.id.buttonSetCancerType);
        staticList = CancerTypeList.getInstance();

        try {
            CsvReader parser = new CsvReader();
            parser.reader(getApplicationContext());
            List<CancerType> cancerTypeList = parser.objects;
            staticList.setList(cancerTypeList);
            strList = new String[cancerTypeList.size()];

            for (int i = 0; i < cancerTypeList.size(); i++) {
                cancerType = cancerTypeList.get(i);
                strList[i] = cancerType.getStrCancerName();
            }

            cancerSpinner = (Spinner)findViewById(R.id.cancerTypeList);
            arrayAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, strList);
            //ArrayAdapter<CancerType> arrayAdapter = new ArrayAdapter<CancerType>(this, R.layout.support_simple_spinner_dropdown_item, objects);
            cancerSpinner.setAdapter(arrayAdapter);
            cancerSpinner.setOnItemSelectedListener(this);
            button.setOnClickListener(this);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        int cancerTypeIndex = cancerSpinner.getSelectedItemPosition();
        int i = v.getId();
        if(i == R.id.buttonSetCancerType) {
            UserProfile userProfile = UserProfile.getInstance();
            userProfile.setCancerType(strList[cancerTypeIndex]);
            staticList.setSelectIndex(cancerTypeIndex);
            startActivity(new Intent(getApplicationContext(), B1_2_GanrecoMain.class));
            finish();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent){}

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {}
}
