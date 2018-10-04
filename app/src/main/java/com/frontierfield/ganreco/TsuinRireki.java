package com.frontierfield.ganreco;


import android.net.Uri;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by kkarimu on 2018/07/12.
 */

public class TsuinRireki {
    String ID; /*RDB tsuinrireki key*/
    Boolean t; /*true head*/
    int y_index, m_index, d_index;    //日にち
    String hospital;    //病院名
    String detail;  //診察内容
    String filepath;//実際に参照するpath
    String localpath; //写した写真のローカルpath
    String storagepath;     //firebasestrageのpath
    String fileName;
    //基本的にpathを持っててもらう
    //firebasestorageにアップロードが終わったら、firabaseの方を参照する形にする
    public TsuinRireki() {
    }

    public TsuinRireki(String ID, Boolean t, String hospital, String detail, int y_index, int m_index, int d_index) {
        this.ID = ID;
        this.t = t;
        this.y_index = y_index;
        this.m_index = m_index;
        this.d_index = d_index;
        this.hospital = hospital;
        this.detail = detail;
    }

    public String getWeek() {
        Calendar cal = Calendar.getInstance();
        Global_Util gu = new Global_Util();
        Integer year = gu.aYotei.get(y_index);
        Integer month = gu.aMonth[m_index] - 1;
        Integer day = gu.aDay[d_index];
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

    public int getYearIndex() {
        return y_index;
    }
    public void setYearIndex(int yearIndex){
        this.y_index=yearIndex;
    }

    public int getMonthIndex() {
        return m_index;
    }
    public void setMonthIndex(int monthIndex){
        this.m_index=monthIndex;
    }

    public int getDayIndex() {
        return d_index;
    }
    public void setDayIndex(int dayIndex){
        this.d_index=dayIndex;
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

    public String getFilepath(){return filepath;}
    public void setFilepath(String filePath){this.filepath=filePath;}

    public String getLocalpath(){return localpath;}
    public void setLocalpath(String localpath){this.localpath=localpath;}

    public String getStoragepath(){return storagepath;}
    public void setStoragepath(String storagepath){this.storagepath=storagepath;}

    public String getFileName(){return fileName;}
    public void setFileName(String fileName){this.fileName=fileName;}
}
