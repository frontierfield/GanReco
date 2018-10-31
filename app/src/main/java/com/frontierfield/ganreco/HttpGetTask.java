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

class HttpGetTask extends AsyncTask<URL, Integer, Map<String,String>> {

    private AsyncCallback asyncCallback = null;
    private int tab;

    public HttpGetTask(AsyncCallback _asyncCallback,int tab) {
        asyncCallback = _asyncCallback;
        this.tab=tab;
    }

    public interface AsyncCallback {
        void preExecute();
        void postExecute(Map<String,String> result);
        void progressUpdate(int progress);
        void cancel();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        asyncCallback.preExecute();
    }

    @Override
    protected void onProgressUpdate(Integer... _progress) {
        super.onProgressUpdate(_progress);
        asyncCallback.progressUpdate(_progress[0]);
    }

    @Override
    protected void onPostExecute(Map<String,String> _result) {
        super.onPostExecute(_result);
        asyncCallback.postExecute(_result);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        asyncCallback.cancel();
    }

    @Override
    protected Map<String,String> doInBackground(URL... url) {
        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = (HttpURLConnection) url[0].openConnection();
            httpURLConnection.setReadTimeout(100000);
            httpURLConnection.setConnectTimeout(600000);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoInput(true);
            httpURLConnection.connect();

            if(httpURLConnection.getResponseCode() == 200) {
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
                StringBuilder stringBuilder = new StringBuilder();
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

                return result;
            }
            else{   // リクエスト失敗
                return null;
            }
        }
        catch(IOException e) {
            e.printStackTrace();
            return null;
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        finally {
            if(httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
    }

    public void setOnCallBack(AsyncCallback _cbj) {
        asyncCallback = _cbj;
    }
}
