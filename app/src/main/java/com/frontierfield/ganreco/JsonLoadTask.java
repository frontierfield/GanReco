package com.frontierfield.ganreco;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

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
                for(int i=0;i<jsonArray.length()-1;i++){
                    JSONObject jsonObject= (JSONObject) jsonArray.get(i);
                    stringBuilder.append(jsonObject.get("matchword"));
                    stringBuilder.append("  ");
                    stringBuilder.append(jsonObject.get("relationWord"));
                    stringBuilder.append("\n");
                }
                return new String(stringBuilder);
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
