package com.frontierfield.ganreco;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;

public class F6_TsuinRirekiDetail extends Fragment implements  View.OnClickListener {
    ImageView imageViewShinryo;
    TextView hospitalName, date, detail;
    TextView btnCancel;
    TextView btnEdit;
    LinearLayout eraseBtn;

    int position = -1;
    TsuinRireki tsuinRireki;
    Global_Util globalUtil;
    Bitmap bitmap;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(layoutInflater, container, savedInstanceState);
        return layoutInflater.inflate(R.layout.f6, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        globalUtil = new Global_Util();

        eraseBtn = view.findViewById(R.id.eraseDataF6);
        btnCancel = view.findViewById(R.id.cancelF6);
        btnEdit = view.findViewById(R.id.editF6);
        hospitalName = view.findViewById(R.id.HospitalNameF6);
        date = view.findViewById(R.id.DateF6);
        detail = view.findViewById(R.id.DetailF6);
        imageViewShinryo = view.findViewById(R.id.imageViewSinryoF6);

        btnCancel.setOnClickListener(this);
        btnEdit.setOnClickListener(this);
        eraseBtn.setOnClickListener(this);
        imageViewShinryo.setOnClickListener(this);

        LoadTsuinRirekiData();

    }

    private void LoadTsuinRirekiData() {
        Bundle bundle = getArguments();
        position = bundle.getInt("TsuinRirekiID", -1);
        try {
            tsuinRireki = TsuinRirekiList.getSavedTsuinRireki(position);
            Integer year = globalUtil.aYotei.get(tsuinRireki.getYearIndex());
            Integer month = globalUtil.aMonth[tsuinRireki.getMonthIndex()];
            Integer day = globalUtil.aDay[tsuinRireki.getDayIndex()];

            hospitalName.setText(tsuinRireki.getHospital());
            date.setText(year.toString() + "/" +
                    month.toString() + "/" + day.toString());
            detail.setText(tsuinRireki.detail);//ここにOCRの結果を突っ込む
            bitmap = globalUtil.getPreResizedBitmap(Uri.parse(tsuinRireki.getFilePath()), getContext());
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageViewShinryo.setImageBitmap(bitmap);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.cancelF6) {
            //goto efgh
            Intent intent = new Intent(getContext(), e_f_g_h_mainmenu.class);
            intent.putExtra("tab",1);
            startActivity(intent);
        } else if (i == R.id.editF6) {
            Intent intent = new Intent(getContext(), F4_Input.class);
            intent.putExtra("TsuinRirekiID", position);
            startActivity(intent);
        } else if (i == R.id.eraseDataF6) {
            EraseData();
            Intent intent = new Intent(getContext(), e_f_g_h_mainmenu.class);
            intent.putExtra("tab",1);
            startActivity(intent);
        } else if (i == R.id.imageViewSinryoF6) {
            Intent intent = new Intent(getContext(), F5_Enlarge.class);
            intent.putExtra("cameraUri", tsuinRireki.getFilePath());
            startActivity(intent);
        }
    }

    private void EraseData() {
        TsuinRirekiList.deleteTsuinRireki(position);
    }
}
