package com.frontierfield.ganreco;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;


public class JsonLoadTask extends AsyncTask<String,String,Map<String,String>> {
    private int tab;
    private int position;
    private TextView detail;
    private TextView facility;
    private TextView date;
    public JsonLoadTask(TextView detail,TextView facility,TextView date,int position,int tab){
        super();
        this.detail=detail;
        this.facility=facility;
        this.date=date;
        this.position=position;
        this.tab=tab;
    }
    @Override
    protected void onPreExecute(){
        detail.setText("読み込み中・・・");
        //ダイアログ表示なり、読み込み中表示の方法については後で検討
    }

    @Override
    protected Map<String,String> doInBackground(String... _uri) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(_uri[0]);
        StringBuilder stringBuilder=new StringBuilder();
        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                httpResponse.getEntity().writeTo(outputStream);
                outputStream.close();
                JSONArray jsonArray = new JSONArray(outputStream.toString());
                Map<String,String> result=new HashMap<String,String>();
                switch(tab){
                    case 2:
                        for(int i=0;i<jsonArray.length()-1;i++){
                            JSONObject jsonObject= (JSONObject) jsonArray.get(i);
                            stringBuilder.append(jsonObject.get("medicine"));
                            stringBuilder.append("  ");
                            stringBuilder.append(jsonObject.get("unit"));
                            stringBuilder.append("\n");
                            if(i==2){
                                result.put("SDetail",new String(stringBuilder));
                            }
                        }
                        JSONArray insideJsonArray=(JSONArray) jsonArray.get(jsonArray.length()-1);
                        JSONObject jsonObject=(JSONObject) insideJsonArray.get(0);
                        result.put("Date",(String) jsonObject.get("date"));
                        jsonObject=(JSONObject) insideJsonArray.get(2);
                        result.put("Name",(String) jsonObject.get("name"));
                        jsonObject=(JSONObject) insideJsonArray.get(4);
                        result.put("Address",(String) jsonObject.get("address"));
                        jsonObject=(JSONObject) insideJsonArray.get(5);
                        result.put("Pharmacy",(String) jsonObject.get("pharmacy"));
                        jsonObject=(JSONObject) insideJsonArray.get(6);
                        result.put("Tel",(String) jsonObject.get("tel"));
                        result.put("Detail",new String(stringBuilder));
                        return result;
                }
                return(result);
            } else {
                httpResponse.getEntity().getContent().close();
                throw new IOException();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPostExecute(Map<String,String> result){
        try {
            detail.setText(result.get("Detail"));
            facility.setText(result.get("Pharmacy"));
            date.setText(result.get("Date"));
            switch (tab) {
                case 1:
                    TsuinRirekiList.getSavedTsuinRireki(position).setDetail(result.get(0));
                    break;
                case 2:
                    OkusuriRirekiList.getSavedOkusuriRireki(position).setDetail(result.get("Detail"));
                    OkusuriRirekiList.getSavedOkusuriRireki(position).setSDetail(result.get("SDetail"));
                    OkusuriRirekiList.getSavedOkusuriRireki(position).setDate(result.get("Date"));
                    OkusuriRirekiList.getSavedOkusuriRireki(position).setName(result.get("Name"));
                    OkusuriRirekiList.getSavedOkusuriRireki(position).setAddress(result.get("Address"));
                    OkusuriRirekiList.getSavedOkusuriRireki(position).setPharmacy(result.get("Pharmacy"));
                    OkusuriRirekiList.getSavedOkusuriRireki(position).setTel(result.get("Tel"));
                    OkusuriRirekiRDB.saveOkusuriRirekiRDB();
                    break;
                case 3:
                    KensaRirekiList.getSavedKensaRireki(position).setDetail(result.get(0));
                    break;
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
