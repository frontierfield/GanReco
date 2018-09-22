package com.frontierfield.ganreco;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.File;
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
    public Bitmap getBitmap(File file){//ファイルパスを引数に向きが正しいbitmapを返す
        ExifInterface exifInterface=null;
        try {
            exifInterface=new ExifInterface(String.valueOf(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int orientation=Integer.parseInt(exifInterface.getAttribute(ExifInterface.TAG_ORIENTATION));

        InputStream inputStream=null;
        Bitmap ans;
        try {
            inputStream=new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Bitmap bitmap= BitmapFactory.decodeStream(new BufferedInputStream(inputStream));
        Matrix matrix=new Matrix();
        matrix.postRotate(90);//90度回転
        ans=Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
        return ans;
    }
    //リモートのinputStreamではExifInterfaceは動作しない
    //Uriに対してのみ使用する
    public Integer rotationPhoto(Uri uri, Context c){
        InputStream in = null;
        int rotation = 0;
        try {
            in = c.getContentResolver().openInputStream(uri);
            ExifInterface exifInterface = new ExifInterface(in);
            // Now you can extract any Exif tag you want
            // Assuming the image is a JPEG or supported raw format

            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotation = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotation = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotation = 270;
                    break;
            }

        } catch (IOException e) {
            // Handle any errors
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ignored) {}
            }
        }
        return rotation;
    }
}
