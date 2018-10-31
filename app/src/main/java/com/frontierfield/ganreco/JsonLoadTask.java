package com.frontierfield.ganreco;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static java.security.AccessController.getContext;


public class JsonLoadTask extends AsyncTask<URL,String,Map<String,String>> {
    private int tab;
    private int position;
    public JsonLoadTask(int position,int tab){
        super();
        this.position=position;
        this.tab=tab;
    }
    @Override
    protected void onPreExecute(){
        //ダイアログ表示なり、読み込み中表示の方法については後で検討
        // プログレスダイアログを表示する
    }

    @Override
    protected Map<String,String> doInBackground(URL... url) {
        HttpURLConnection httpURLConnection = null;
        StringBuilder stringBuilder=new StringBuilder();
        try {
            httpURLConnection = (HttpURLConnection) url[0].openConnection();
            httpURLConnection.setReadTimeout(600000);
            httpURLConnection.setConnectTimeout(600000);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoInput(true);
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() == 200) {
                // リクエスト成功
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                StringBuffer stringBuffer = new StringBuffer();
                String line;
                while((line = br.readLine()) != null){
                    stringBuffer.append(line);
                }
                try {
                    inputStream.close();
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                JSONArray jsonArray = new JSONArray(stringBuffer.toString());
                JSONObject jsonObject = (JSONObject)jsonArray.toJSONObject(jsonArray);
                Map<String,String> result=new HashMap<String,String>();
                switch(tab){
                    case 2:
                        int loop = 0;
                        while (loop < jsonObject.length()){
                            JSONObject object= (JSONObject)jsonArray.get(loop);
                            if(!object.has("medicine")){
                                result.put("Medicine",new String(stringBuilder));
                                break;
                            }
                            stringBuilder.append(object.get("medicine"));
                            stringBuilder.append("  ");
                            stringBuilder.append(object.get("unit"));
                            stringBuilder.append("\n");
                            loop++;
                        }
                        for(int i=loop;i<jsonObject.length();i++){
                            JSONObject object = (JSONObject)jsonArray.get(i);
                            if(object.has("date")){
                                result.put("Date", (String)object.get("date"));
                                continue;
                            }
                            if(object.has("pharmacy")){
                                result.put("Pharmacy", (String)object.get("pharmacy"));
                                continue;
                            }
                            if(object.has("name")){
                                result.put("Name", (String)object.get("name"));
                                continue;
                            }
                            if(object.has("bun")){
                                result.put("Bun", (String)object.get("bun"));
                                continue;
                            }
                            if(object.has("address")){
                                result.put("Address", (String)object.get("address"));
                                continue;
                            }
                            if(object.has("tel")){
                                result.put("Tel", (String)object.get("tel"));
                                continue;
                            }
                            if(object.has("fax")){
                                result.put("Fax", (String)object.get("fax"));
                                continue;
                            }
                        }
                        return result;
                }
                return(result);
            } else {
                return null;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }finally {
            if(httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
    }
    @Override
    protected void onPostExecute(Map<String,String> result){
        try {
            String date=result.get("Date");
            switch (tab) {
                case 1:
                    TsuinRirekiList.getSavedTsuinRireki(position).setDetail(result.get(0));
                    break;
                case 2:
                    if(result.get("Medicine")!=null) {
                        OkusuriRirekiList.getSavedOkusuriRireki(position).setDetail(result.get("Medicine"));
                    }if(result.get("Date")!=null) {
                        OkusuriRirekiList.getSavedOkusuriRireki(position).setDate(result.get("Date"));
                        OkusuriRirekiList.getSavedOkusuriRireki(position).setYear(Integer.parseInt(extractYear(date)));
                        OkusuriRirekiList.getSavedOkusuriRireki(position).setMonth(Integer.parseInt(extractManth(date))-1);
                        OkusuriRirekiList.getSavedOkusuriRireki(position).setDay(Integer.parseInt(extractDay(date)));
                    }if(result.get("Name")!=null) {
                        OkusuriRirekiList.getSavedOkusuriRireki(position).setName(result.get("Name"));
                    }if(result.get("Address")!=null) {
                        OkusuriRirekiList.getSavedOkusuriRireki(position).setAddress(result.get("Address"));
                    }if(result.get("Phamacy")!=null) {
                        OkusuriRirekiList.getSavedOkusuriRireki(position).setPharmacy(result.get("Pharmacy"));
                    }if(result.get("Tel")!=null) {
                        OkusuriRirekiList.getSavedOkusuriRireki(position).setTel(result.get("Tel"));
                    }
                    OkusuriRirekiList.getSavedOkusuriRireki(position).setIsOCRComplete(true);
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
    protected String extractYear(String date){
        int beginindex=date.indexOf("平成")+2;//「平成」の次から
        int endindex=date.indexOf("年");
        int year=Integer.parseInt(date.substring(beginindex,endindex))+1988;//平成を西暦に
        return String.valueOf(year);
    }
    protected String extractManth(String date){
        int beginindex=date.indexOf("年")+1;//「年」の次から
        int endindex=date.indexOf("月");
        return date.substring(beginindex,endindex);
    }
    protected String extractDay(String date){
        int beginindex=date.indexOf("月")+1;//「月」の次から
        int endindex=date.indexOf("日");
        return date.substring(beginindex,endindex);
    }
}
