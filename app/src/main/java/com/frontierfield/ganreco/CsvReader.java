package com.frontierfield.ganreco;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {
    List<CancerType> objects = new ArrayList<CancerType>();
    public void reader(Context context) {
        AssetManager assetManager = context.getResources().getAssets();
        try {
            // CSVファイルの読み込み
            InputStream inputStream = assetManager.open("CancerTypes.csv");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferReader.readLine()) != null) {

                //カンマ区切りで１つづつ配列に入れる
                CancerType data = new CancerType();
                String[] RowData = line.split(",");

                //CSVの左([0]番目)から順番にセット
                data.setStrCancerName(RowData[0]);
                objects.add(data);
            }
            bufferReader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
