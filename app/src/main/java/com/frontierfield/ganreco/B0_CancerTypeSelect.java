package com.frontierfield.ganreco;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import java.util.List;

public class B0_CancerTypeSelect extends AppCompatActivity implements View.OnClickListener {

    Spinner cancerSpinner;    // がんの種類

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b0_cancertypeselect);

        try {
            CsvReader parser = new CsvReader();
            parser.reader(getApplicationContext());
            List<ListCancerName> objects = parser.objects;
            String strList[] = new String[objects.size()];
            for (int i = 0; i < objects.size(); i++) {
                ListCancerName data = objects.get(i);
                strList[i] = data.getStrCancerName();
            }
            cancerSpinner = findViewById(R.id.cancerTypeList);
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, strList);
            //ArrayAdapter<ListCancerName> arrayAdapter = new ArrayAdapter<ListCancerName>(this, R.layout.support_simple_spinner_dropdown_item, objects);
            cancerSpinner.setAdapter(arrayAdapter);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if(i == R.id.buttonSetCancerType) {
            startActivity(new Intent(getApplicationContext(),b1_2mainmenu.class));
            finish();
        }
    }
}
