package com.frontierfield.ganreco;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class HttpGetTask extends AsyncTask<URL, Integer, JSONArray> {

    private AsyncCallback asyncCallback = null;

    public HttpGetTask(AsyncCallback _asyncCallback) {
        asyncCallback = _asyncCallback;
    }

    public interface AsyncCallback {
        void preExecute();
        void postExecute(JSONArray result);
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
    protected void onPostExecute(JSONArray _result) {
        super.onPostExecute(_result);
        asyncCallback.postExecute(_result);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        asyncCallback.cancel();
    }

    @Override
    protected JSONArray doInBackground(URL... url) {
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
                return jsonArray;
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
