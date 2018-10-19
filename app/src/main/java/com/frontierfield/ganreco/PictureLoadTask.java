package com.frontierfield.ganreco;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PictureLoadTask extends AsyncTask<String,String,Bitmap> {
    private int position;
    private ImageView imageView;

    public PictureLoadTask(int position,ImageView imageView){
        super();
        this.position=position;
        this.imageView=imageView;
    }

    @Override
    protected void onPreExecute(){
        //ダウンロード中に表示するもの
        //imageView.setImageDrawable();
        //ダイアログ表示なり、読み込み中表示の方法については後で検討
    }
    @Override
    protected Bitmap doInBackground(String... _uri) {
        return downloadImage(_uri[0]);
    }

    @Override
    protected void onPostExecute(Bitmap result){
        imageView.setImageBitmap(result);
    }
    private Bitmap downloadImage(String address) {
        Bitmap bmp = null;

        final StringBuilder result = new StringBuilder();

        HttpURLConnection urlConnection = null;

        try {
            URL url = new URL( address );

            // HttpURLConnection インスタンス生成
            urlConnection = (HttpURLConnection) url.openConnection();

            // タイムアウト設定
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(20000);

            // リクエストメソッド
            urlConnection.setRequestMethod("GET");

            // リダイレクトを自動で許可しない設定
            urlConnection.setInstanceFollowRedirects(false);

            // ヘッダーの設定(複数設定可能)
            urlConnection.setRequestProperty("Accept-Language", "jp");

            // 接続
            urlConnection.connect();

            int resp = urlConnection.getResponseCode();

            switch (resp){
                case HttpURLConnection.HTTP_OK:
                    InputStream is = null;
                    try{
                        is = urlConnection.getInputStream();
                        bmp = BitmapFactory.decodeStream(is);
                        is.close();
                    } catch(IOException e){
                        e.printStackTrace();
                    } finally{
                        if(is != null){
                            is.close();
                        }
                    }
                    break;
                case HttpURLConnection.HTTP_UNAUTHORIZED:
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return bmp;
    }
}
