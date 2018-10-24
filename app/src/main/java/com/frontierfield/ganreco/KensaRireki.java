package com.frontierfield.ganreco;

import android.support.annotation.Keep;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
@Keep
public class KensaRireki {
    String ID; /*RDB kensarireki key*/
    Boolean t; /*true head*/
    int year, month, day;
    String hospital;
    String detail;
    String filePath;
    String localPath;
    String storagePath;
    String fileName;

    public KensaRireki() {
    }

    public KensaRireki(String ID, Boolean t, String hospital, String detail, int year, int month, int day) {
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
