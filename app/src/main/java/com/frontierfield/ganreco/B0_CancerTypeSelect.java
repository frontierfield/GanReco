package com.frontierfield.ganreco;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class B0_CancerTypeSelect extends AppCompatActivity implements View.OnClickListener {

    Spinner cancerTypeList;    // がんの種類

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b0_cancertypeselect);

        CsvReader parser = new CsvReader();
        parser.reader(getApplicationContext());
        List<ListCancerName> objects = new ArrayList<ListCancerName>();
        cancerTypeList = findViewById(R.id.cancerTypeList);
        cancerTypeList.setAdapter((SpinnerAdapter) objects);
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
