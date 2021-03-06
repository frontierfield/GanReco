package com.frontierfield.ganreco;

import android.os.AsyncTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

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
            httpURLConnection.setReadTimeout(100000);
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
                Map<String,String> result=new HashMap<String,String>();
                switch(tab){
                    case 2:
                        int loop = 0;
                        while (loop < jsonArray.length()){
                            JSONObject object= (JSONObject)jsonArray.get(loop);
                            if(!object.has("medicine")){
                                result.put("Medicine",new String(stringBuilder));
                                if(!result.containsKey("SDetail")){
                                    result.put("SDetail",new String(stringBuilder));
                                }
                                break;
                            }
                            stringBuilder.append(object.get("medicine"));
                            stringBuilder.append("  ");
                            stringBuilder.append(object.get("unit"));
                            stringBuilder.append("\n");
                            if(loop==2){
                                result.put("SDetail",new String(stringBuilder));
                            }
                            loop++;
                        }
                        for(int i=loop;i<jsonArray.length();i++){
                            JSONObject object = (JSONObject)jsonArray.get(i);
                            if(object.has("date")&&!result.containsKey("Date")){
                                result.put("Date", (String)object.get("date"));
                                continue;
                            }
                            if(object.has("pharmacy")&&!result.containsKey("Pharmacy")){
                                result.put("Pharmacy", (String)object.get("pharmacy"));
                                continue;
                            }
                            if(object.has("name")&&!result.containsKey("Name")){
                                result.put("Name", (String)object.get("name"));
                                continue;
                            }
                            if(object.has("bun")&&!result.containsKey("Bun")){
                                result.put("Bun", (String)object.get("bun"));
                                continue;
                            }
                            if(object.has("address")&&!result.containsKey("Address")){
                                result.put("Address", (String)object.get("address"));
                                continue;
                            }
                            if(object.has("tel")&&!result.containsKey("Tel")){
                                result.put("Tel", (String)object.get("tel"));
                                continue;
                            }
                            if(object.has("fax")&&!result.containsKey("Fax")){
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
        if(result!=null) {
            try {
                String date = result.get("Date");
                switch (tab) {
                    case 1:
                        TsuinRirekiList.getSavedTsuinRireki(position).setDetail(result.get(0));
                        break;
                    case 2:
                        if (result.get("Medicine") != null) {
                            OkusuriRirekiList.getSavedOkusuriRireki(position).setDetail(result.get("Medicine"));
                            OkusuriRirekiList.getSavedOkusuriRireki(position).setSDetail(result.get("SDetail"));
                        }
                        if (result.get("Date") != null) {
                            OkusuriRirekiList.getSavedOkusuriRireki(position).setDate(result.get("Date"));
                            /*
                            if (date.contains("平成") & date.contains("年")) {
                                OkusuriRirekiList.getSavedOkusuriRireki(position).setYear(Integer.parseInt(extractYear(date)));
                            }
                            if (date.contains("年") & date.contains("月")) {
                                OkusuriRirekiList.getSavedOkusuriRireki(position).setMonth(Integer.parseInt(extractManth(date)) - 1);
                            }
                            if (date.contains("月") & date.contains("日")) {
                                OkusuriRirekiList.getSavedOkusuriRireki(position).setDay(Integer.parseInt(extractDay(date)));
                            }*/
                        }
                        if (result.get("Name") != null) {
                            OkusuriRirekiList.getSavedOkusuriRireki(position).setName(result.get("Name"));
                        }
                        if (result.get("Address") != null) {
                            OkusuriRirekiList.getSavedOkusuriRireki(position).setAddress(result.get("Address"));
                        }
                        if (result.get("Pharmacy") != null) {
                            OkusuriRirekiList.getSavedOkusuriRireki(position).setPharmacy(result.get("Pharmacy"));
                        }
                        if (result.get("Tel") != null) {
                            OkusuriRirekiList.getSavedOkusuriRireki(position).setTel(result.get("Tel"));
                        }
                        OkusuriRirekiList.getSavedOkusuriRireki(position).setIsOCRComplete(true);
                        OkusuriRirekiRDB.saveOkusuriRirekiRDB();
                        break;
                    case 3:
                        KensaRirekiList.getSavedKensaRireki(position).setDetail(result.get(0));
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{

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
