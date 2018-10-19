package com.frontierfield.ganreco;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PictureLoadTask extends AsyncTask<String,Integer,Bitmap> {
    private ImageView imageView;
    private ProgressBar progressBar;
    public PictureLoadTask(ImageView imageView, ProgressBar progressBar){
        super();
        this.imageView=imageView;
        this.progressBar=progressBar;
    }

    @Override
    protected void onPreExecute(){
        //プログレスバー表示
        progressBar.setVisibility(ProgressBar.VISIBLE);
        imageView.setVisibility(ImageView.INVISIBLE);
    }
    @Override
    protected Bitmap doInBackground(String... _uri) {
        return downloadImage(_uri[0]);
    }
    @Override
    protected void onPostExecute(Bitmap result){
        imageView.setImageBitmap(result);
        F6_G6_H8_Detail.bitmap=result;
        progressBar.setVisibility(ProgressBar.INVISIBLE);
        imageView.setVisibility(ImageView.VISIBLE);
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
