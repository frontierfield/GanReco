package com.frontierfield.ganreco;


import android.net.Uri;
import android.support.annotation.Keep;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Keep
public class TsuinRireki {
    String ID; /*RDB tsuinrireki key*/
    Boolean t; /*true head*/
    int year, month, day;    //日にち
    String hospital;    //病院名
    String detail;  //診察内容
    String filePath;//実際に参照するPath
    String localPath; //写した写真のローカルPath
    String storagePath;     //firebasestrageのPath
    String fileName;
    //基本的にPathを持っててもらう
    //firebasestorageにアップロードが終わったら、firabaseの方を参照する形にする
    public TsuinRireki() {
    }

    public TsuinRireki(String ID, Boolean t, String hospital, String detail, int year, int month, int day) {
        this.ID = ID;
        this.t = t;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hospital = hospital;
        this.detail = detail;
    }

    public String getWeek() {
        Calendar cal = Calendar.getInstance();
        Integer year = this.year;
        Integer month = this.month;
        Integer day = this.day;
        cal.set(year, month, day);
        String r = "";
        switch (cal.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SUNDAY:     // Calendar.SUNDAY:1 （値。意味はない）
                //日曜日
                r = "(日)";
                break;
            case Calendar.MONDAY:     // Calendar.MONDAY:2
                //月曜日
                r = "(月)";
                break;
            case Calendar.TUESDAY:    // Calendar.TUESDAY:3
                //火曜日
                r = "(火)";
                break;
            case Calendar.WEDNESDAY:  // Calendar.WEDNESDAY:4
                //水曜日
                r = "(水)";
                break;
            case Calendar.THURSDAY:   // Calendar.THURSDAY:5
                //木曜日
                r = "(木)";
                break;
            case Calendar.FRIDAY:     // Calendar.FRIDAY:6
                //金曜日
                r = "(金)";
                break;
            case Calendar.SATURDAY:   // Calendar.SATURDAY:7
                //土曜日
                r = "(土)";
                break;
        }
        return r;
    }

    public String getID() {
        return ID;
    }
    public void setID(String id){
        this.ID=id;
    }

    public Boolean getHead() {
        return t;
    }
    public void setHead(Boolean t){
        this.t=t;
    }

    public int getYear() {
        return year;
    }
    public void setYear(int year){
        this.year=year;
    }

    public int getMonth() {
        return month;
    }
    public void setMonth(int month){
        this.month=month;
    }

    public int getDay() {
        return day;
    }
    public void setDay(int day){
        this.day=day;
    }

    public String getHospital() {
        return hospital;
    }
    public void setHospital(String hospital){
        this.hospital=hospital;
    }

    public String getDetail(){
        return detail;
    }
    public void setDetail(String detail){
        this.detail=detail;
    }

    public String getFilePath(){return filePath;}
    public void setFilePath(String filePath){this.filePath=filePath;}

    public String getLocalPath(){return localPath;}
    public void setLocalPath(String localPath){this.localPath=localPath;}

    public String getStoragePath(){return storagePath;}
    public void setStoragePath(String storagePath){this.storagePath=storagePath;}

    public String getFileName(){return fileName;}
    public void setFileName(String fileName){this.fileName=fileName;}
}
