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
import java.util.concurrent.ExecutionException;


public class JsonLoadTask extends AsyncTask<String,String,String> {
    private int tab;
    private int position;
    private TextView textView;
    public JsonLoadTask(TextView textView,int position,int tab){
        super();
        this.textView=textView;
        this.position=position;
        this.tab=tab;
    }
    @Override
    protected void onPreExecute(){
        textView.setText("読み込み中・・・");
        //ダイアログ表示なり、読み込み中表示の方法については後で検討
    }

    @Override
    protected String doInBackground(String... _uri) {
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
                switch(tab){
                    case 2:
                        for(int i=0;i<jsonArray.length()-1;i++){
                            JSONObject jsonObject= (JSONObject) jsonArray.get(i);
                            stringBuilder.append(jsonObject.get("medicine"));
                            stringBuilder.append("  ");
                            stringBuilder.append(jsonObject.get("unit"));
                            stringBuilder.append("\n");
                        }
                        JSONArray insideJsonArray=(JSONArray) jsonArray.get(jsonArray.length()-1);
                        JSONObject jsonObject=(JSONObject) insideJsonArray.get(0);
                        OkusuriRirekiList.getInstance().get(position).setDate((String) jsonObject.get("date"));
                        jsonObject=(JSONObject) insideJsonArray.get(2);
                        OkusuriRirekiList.getInstance().get(position).setName((String) jsonObject.get("name"));
                        jsonObject=(JSONObject) insideJsonArray.get(4);
                        OkusuriRirekiList.getInstance().get(position).setAddress((String) jsonObject.get("address"));
                        jsonObject=(JSONObject) insideJsonArray.get(5);
                        OkusuriRirekiList.getInstance().get(position).setPharmacy((String) jsonObject.get("pharmacy"));
                        jsonObject=(JSONObject) insideJsonArray.get(6);
                        OkusuriRirekiList.getInstance().get(position).setTel((String) jsonObject.get("tel"));
                        return new String(stringBuilder);
                }
                return("未実装");
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
    protected void onPostExecute(String result){
        try {
            textView.setText(result);
            switch (tab) {
                case 1:
                    TsuinRirekiList.getSavedTsuinRireki(position).setDetail(result);
                    break;
                case 2:
                    OkusuriRirekiList.getSavedOkusuriRireki(position).setDetail(result);
                    break;
                case 3:
                    KensaRirekiList.getSavedKensaRireki(position).setDetail(result);
                    break;
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
