package com.frontierfield.ganreco;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.URL;

public class F6_G6_H8_Detail extends Fragment implements  View.OnClickListener {
    ImageView imageViewShinryo;
    TextView hospitalName, date, detail;
    TextView btnCancel;
    TextView btnEdit;
    TextView contentF6;
    LinearLayout eraseBtn;
    ProgressBar progressBar;
    ProgressDialog progressDialog;
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
            Integer year = globalUtil.aYotei.get(tsuinRireki.getYear());
            Integer month = globalUtil.aMonth[tsuinRireki.getMonth()];
            Integer day = globalUtil.aDay[tsuinRireki.getDay()];

            hospitalName.setText(tsuinRireki.getHospital());
            date.setText(year.toString() + "/" +
                    month.toString() + "/" + day.toString());

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
            if(!okusuriRireki.isOCRComplete){
                progressDialog = new ProgressDialog(getContext());
                progressDialog.setIndeterminate(true);
                progressDialog.setTitle("撮影画像読み込み中...");
                progressDialog.show();
            }else {
                progressDialog.dismiss();
                hospitalName.setText(okusuriRireki.getPharmacy());
                detail.setText(okusuriRireki.getDetail());
                date.setText(okusuriRireki.getYear() + "/" +
                        (okusuriRireki.getMonth() + 1) + "/" + okusuriRireki.getDay());

                if (okusuriRireki.getStoragePath() != null) {
                    PictureLoadTask pictureLoadTask = new PictureLoadTask(imageViewShinryo, progressBar);
                    pictureLoadTask.execute(okusuriRireki.getStoragePath());
                } else {
                    bitmap = globalUtil.getPreResizedBitmap(Uri.parse(okusuriRireki.getFilePath()), getContext());
                    imageViewShinryo.setImageBitmap(bitmap);
                }
                btnEdit.setOnClickListener(this);
                eraseBtn.setOnClickListener(this);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void LoadKensaRirekiData() {
        try {
            kensaRireki = KensaRirekiList.getSavedKensaRireki(position);
            Integer year = globalUtil.aYotei.get(kensaRireki.getYear());
            Integer month = globalUtil.aMonth[kensaRireki.getMonth()];
            Integer day = globalUtil.aDay[kensaRireki.getDay()];

            hospitalName.setText(kensaRireki.getHospital());
            date.setText(year.toString() + "/" +
                    month.toString() + "/" + day.toString());

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
                TsuinRirekiList.deleteTsuinRireki(position,getContext());
                TsuinRirekiFirebaseStorage.deleteTsuinRirekiFirebaseStorage(tsuinRireki.getFileName());
                break;
            case 2:
                OkusuriRirekiList.deleteOkusuriRireki(position,getContext());
                OkusuriRirekiFirebaseStorage.deleteOkusuriRirekiFirebaseStorage(okusuriRireki.getFileName());
                break;
            case 3:
                KensaRirekiList.deleteKensaRireki(position,getContext());
                KensaRirekiFirebaseStorage.deleteKensaRirekiFirebaseStorage(kensaRireki.getFileName());
                break;
        }

    }
}

