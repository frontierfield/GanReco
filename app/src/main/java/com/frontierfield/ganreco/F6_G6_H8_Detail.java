package com.frontierfield.ganreco;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class F6_G6_H8_Detail extends Fragment implements  View.OnClickListener {
    ImageView imageViewShinryo;
    TextView hospitalName, date, detail;
    TextView btnCancel;
    TextView btnEdit;
    TextView contentF6;
    LinearLayout eraseBtn;
    ProgressBar progressBar;
    int position=-1;
    int tab = -1;
    TsuinRireki tsuinRireki;
    OkusuriRireki okusuriRireki;
    KensaRireki kensaRireki;
    Global_Util globalUtil;
    static Bitmap bitmap;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(layoutInflater, container, savedInstanceState);
        return layoutInflater.inflate(R.layout.f6, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        globalUtil = new Global_Util();

        contentF6=view.findViewById(R.id.ContentF6);
        eraseBtn = view.findViewById(R.id.eraseDataF6);
        btnCancel = view.findViewById(R.id.cancelF6);
        btnEdit = view.findViewById(R.id.editF6);
        hospitalName = view.findViewById(R.id.HospitalNameF6);
        date = view.findViewById(R.id.DateF6);
        detail = view.findViewById(R.id.DetailF6);
        imageViewShinryo = view.findViewById(R.id.imageViewSinryoF6);
        progressBar=view.findViewById(R.id.progressBar);

        btnCancel.setOnClickListener(this);
        btnEdit.setOnClickListener(this);
        eraseBtn.setOnClickListener(this);
        imageViewShinryo.setOnClickListener(this);

        Bundle bundle = getArguments();
        tab = bundle.getInt("tab", -1);
        position = bundle.getInt("position", -1);
        switch (tab){
            case 1:
                contentF6.setText("診療内容");
                LoadTsuinRirekiData();
                break;
            case 2:
                contentF6.setText("処方内容");
                LoadOkusuriRirekiData();
                break;
            case 3:
                contentF6.setText("検査結果");
                LoadKensaRirekiData();
                break;
        }
    }

    private void LoadTsuinRirekiData() {
        try {
            tsuinRireki = TsuinRirekiList.getSavedTsuinRireki(position);
            Integer year = globalUtil.aYotei.get(tsuinRireki.getYearIndex());
            Integer month = globalUtil.aMonth[tsuinRireki.getMonthIndex()];
            Integer day = globalUtil.aDay[tsuinRireki.getDayIndex()];

            hospitalName.setText(tsuinRireki.getHospital());
            date.setText(year.toString() + "/" +
                    month.toString() + "/" + day.toString());

            JsonLoadTask jsonLoadTask = new JsonLoadTask(detail, position,1);
            jsonLoadTask.execute("https://firebasestorage.googleapis.com/v0/b/ganreco-ea9fc.appspot.com" +
                    "/o/GeABbXGNuubgO2j7J8HCUACnGN92%2Fchozai%252Frrrrr.json?" +
                    "alt=media&token=0af9fad9-9206-4ab0-a65a-9a9f2c047bce");//jsonのダウンロードURIを渡す

            if (tsuinRireki.getStoragePath() != null) {
                PictureLoadTask pictureLoadTask = new PictureLoadTask(imageViewShinryo,progressBar);
                pictureLoadTask.execute(tsuinRireki.getStoragePath());
            } else {
                bitmap = globalUtil.getPreResizedBitmap(Uri.parse(tsuinRireki.getFilePath()), getContext());
                imageViewShinryo.setImageBitmap(bitmap);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void LoadOkusuriRirekiData() {
        try {
            okusuriRireki = OkusuriRirekiList.getSavedOkusuriRireki(position);
            Integer year = globalUtil.aYotei.get(okusuriRireki.getYearIndex());
            Integer month = globalUtil.aMonth[okusuriRireki.getMonthIndex()];
            Integer day = globalUtil.aDay[okusuriRireki.getDayIndex()];

            hospitalName.setText(okusuriRireki.getDrugstore());
            date.setText(year.toString() + "/" +
                    month.toString() + "/" + day.toString());

            JsonLoadTask jsonLoadTask = new JsonLoadTask(detail, position,2);
            jsonLoadTask.execute("https://firebasestorage.googleapis.com/v0/b/ganreco-ea9fc.appspot.com/o/" +
                    "json_result_chozai_Chouzai20181019%5B1%5D.json" +
                    "?alt=media&token=e4187194-f11a-4a1b-94df-e4630b717f0f");//jsonのダウンロードURIを渡す

            if (okusuriRireki.getStoragePath() != null) {
                PictureLoadTask pictureLoadTask = new PictureLoadTask(imageViewShinryo,progressBar);
                pictureLoadTask.execute(okusuriRireki.getStoragePath());
            } else {
                bitmap = globalUtil.getPreResizedBitmap(Uri.parse(okusuriRireki.getFilePath()), getContext());
                imageViewShinryo.setImageBitmap(bitmap);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void LoadKensaRirekiData() {
        try {
            kensaRireki = KensaRirekiList.getSavedKensaRireki(position);
            Integer year = globalUtil.aYotei.get(kensaRireki.getYearIndex());
            Integer month = globalUtil.aMonth[kensaRireki.getMonthIndex()];
            Integer day = globalUtil.aDay[kensaRireki.getDayIndex()];

            hospitalName.setText(kensaRireki.getHospital());
            date.setText(year.toString() + "/" +
                    month.toString() + "/" + day.toString());

            JsonLoadTask jsonLoadTask = new JsonLoadTask(detail, position,3);
            jsonLoadTask.execute("https://firebasestorage.googleapis.com/v0/b/ganreco-ea9fc.appspot.com" +
                    "/o/GeABbXGNuubgO2j7J8HCUACnGN92%2Fchozai%252Frrrrr.json?" +
                    "alt=media&token=0af9fad9-9206-4ab0-a65a-9a9f2c047bce");//jsonのダウンロードURIを渡す

            if (kensaRireki.getStoragePath() != null) {
                PictureLoadTask pictureLoadTask = new PictureLoadTask(imageViewShinryo,progressBar);
                pictureLoadTask.execute(kensaRireki.getStoragePath());
            } else {
                bitmap = globalUtil.getPreResizedBitmap(Uri.parse(kensaRireki.getFilePath()), getContext());
                imageViewShinryo.setImageBitmap(bitmap);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.cancelF6) {
            //goto efgh
            Intent intent = new Intent(getContext(), e_f_g_h_mainmenu.class);
            intent.putExtra("tab",tab);
            startActivity(intent);
        } else if (i == R.id.editF6) {
            switch (tab){
                case 1:
                    Intent intent1 = new Intent(getContext(), F4_Input.class);
                    intent1.putExtra("position", position);
                    startActivity(intent1);
                    break;
                case 2:
                    Intent intent2 = new Intent(getContext(), G4_Input.class);
                    intent2.putExtra("position", position);
                    startActivity(intent2);
                    break;
                case 3:
                    Intent intent3 = new Intent(getContext(), H6_Input.class);
                    intent3.putExtra("position", position);
                    startActivity(intent3);
                    break;
            }

        } else if (i == R.id.eraseDataF6) {
            EraseData();
            Intent intent = new Intent(getContext(), e_f_g_h_mainmenu.class);
            intent.putExtra("tab",tab);
            startActivity(intent);
        } else if (i == R.id.imageViewSinryoF6) {
            Intent intent = new Intent(getContext(), F5_G5_H7_Enlarge.class);
            startActivity(intent);
        }
    }

    private void EraseData() {
        switch (tab){
            case 1:
                TsuinRirekiList.deleteTsuinRireki(position);
                TsuinRirekiFirebaseStorage.deleteTsuinRirekiFirebaseStorage(tsuinRireki.getFileName());
                break;
            case 2:
                OkusuriRirekiList.deleteOkusuriRireki(position);
                OkusuriRirekiFirebaseStorage.deleteOkusuriRirekiFirebaseStorage(okusuriRireki.getFileName());
                break;
            case 3:
                KensaRirekiList.deleteKensaRireki(position);
                KensaRirekiFirebaseStorage.deleteKensaRirekiFirebaseStorage(kensaRireki.getFileName());
                break;
        }

    }
}

