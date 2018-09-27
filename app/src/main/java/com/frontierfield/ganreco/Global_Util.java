package com.frontierfield.ganreco;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.ParcelFileDescriptor;
import android.renderscript.ScriptGroup;
import android.support.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by kkarimu on 2018/06/18.
 */

public class Global_Util {
    public static List<Integer> aYear,aYotei;
    public static final Integer[] aMonth = {1,2,3,4,5,6,7,8,9,10,11,12};
    public static final Integer[] aDay = {
            1, 2, 3, 4, 5, 6, 7, 8, 9,10,
            11,12,13,14,15,16,17,18,19,20,
            21,22,23,24,25,26,27,28,29,30,31};
    public static final String[] aStartTime = {
            "8:00","8:30",
            "9:00","9:30",
            "10:00","10:30",
            "11:00","11:30",
            "12:00","12:30",
            "13:00","13:30",
            "14:00","14:30",
            "15:00","15:30",
            "16:00","16:30",
            "17:00","17:30",
            "18:00","18:30",
            "19:00","19:30",
            "20:00","20:30",
            "21:00","21:30",
            "22:00以降"
    };
    public static final String[] aSex = {"男","女"};
    public static File photoDir;


    public Global_Util(){
        Calendar now = Calendar.getInstance();
        aYear = new ArrayList<Integer>();
        for(int i = 1900; i <= now.get(Calendar.YEAR); i++){
            aYear.add(i);
        }

        aYotei = new ArrayList<Integer>();
        for(int i = 2018;i <= 2030;i++){
            aYotei.add(i);
        }
    }
    public void CreateDirectoryForPicture(){
        File cameraFolder = new File(
                Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES),"GanReco");
        if(!cameraFolder.exists()) {
            cameraFolder.mkdirs();
        }
        this.photoDir = cameraFolder;
    }
    public Bitmap getBitmap(Uri uri,Context context){//uriを引数に向きが正しいbitmapを返す
        Bitmap bitmap=null;
        int orientation=ExifInterface.ORIENTATION_UNDEFINED;
        try {
            InputStream inputStream=context.getContentResolver().openInputStream(uri);
            //bitmap = BitmapFactory.decodeStream(new BufferedInputStream(inputStream));ここにあるうちはうまくいかない
            ExifInterface exifInterface = new ExifInterface(inputStream);
            orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,ExifInterface.ORIENTATION_UNDEFINED);

            InputStream inputStream1=context.getContentResolver().openInputStream(uri);//これ追加しないとだめだったなぜ
            bitmap = BitmapFactory.decodeStream(new BufferedInputStream(inputStream1));

        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(bitmap, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(bitmap, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(bitmap, 270);
            default:
                return bitmap;
        }
    }
    private static Bitmap rotateImage(Bitmap bitmap, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        bitmap.recycle();
        return rotatedImg;
    }

    public static Bitmap getPreResizedBitmap(Uri uri,Context context) throws IOException {    //bitmapをメモリ展開なしでリサイズ
        InputStream inputStream=context.getContentResolver().openInputStream(uri);

        // Optionsインスタンスを取得
        BitmapFactory.Options options = new BitmapFactory.Options();

        // Bitmapを生成せずにサイズを取得する
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(inputStream,null, options);

        if(options.outHeight == 0 || options.outWidth == 0) {
            // 等倍（リサイズしない）
            options.inSampleSize = 1;
        }else{
            // 設定するImageViewのサイズにあわせてリサイズ
            int bitmapScale=Math.max(options.outHeight / 50, options.outWidth / 50);
            for(int i=2;i<=bitmapScale;i*=2){
                options.inSampleSize=i;
            }
        }
        inputStream.close();
        // 実際にBitmapを生成する
        options.inJustDecodeBounds = false;
        inputStream=context.getContentResolver().openInputStream(uri);
        return BitmapFactory.decodeStream(inputStream,null, options);
    }
}
