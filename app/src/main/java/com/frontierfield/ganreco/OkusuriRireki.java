package com.frontierfield.ganreco;

import android.support.annotation.Keep;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
@Keep
public class OkusuriRireki {
    String ID; /*RDB syohou key*/
    Boolean t; /*true head*/
    int year,month,day;//
    String s_detail;
    String detail;
    String filePath;
    String localPath;
    String storagePath;
    String fileName;

    String date;
    String name;
    String address;
    String pharmacy;
    String tel;

    public OkusuriRireki(){
    }
    public OkusuriRireki(String ID,Boolean t,String pharmacy,String detail,int year,int month,int day) {
        this.ID = ID;
        this.t = t;
        this.year = year;
        this.month = month;
        this.day = day;
        this.pharmacy=pharmacy;
        this.detail = detail;
    }
    public String getWeek() {
        Calendar cal = Calendar.getInstance();
        Global_Util gu = new Global_Util();
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

    public String getDetail(){
        return detail;
    }
    public void setDetail(String detail){
        this.detail=detail;
    }

    public String getSDetail(){
        return s_detail;
    }
    public void setSDetail(String sDetail){
        this.s_detail=sDetail;
    }

    public String getFilePath(){return filePath;}
    public void setFilePath(String filePath){this.filePath=filePath;}

    public String getLocalPath(){return localPath;}
    public void setLocalPath(String localPath){this.localPath=localPath;}

    public String getStoragePath(){return storagePath;}
    public void setStoragePath(String storagePath){this.storagePath=storagePath;}

    public String getFileName(){return fileName;}
    public void setFileName(String fileName){this.fileName=fileName;}

    public String getDate(){return date;}
    public void setDate(String date){this.date=date;}

    public String getName(){return name;}
    public void setName(String name){this.name=name;}

    public String getAddress(){return address;}
    public void setAddress(String address){this.address=address;}

    public String getPharmacy(){return pharmacy;}
    public void setPharmacy(String pharmacy){this.pharmacy=pharmacy;}

    public String getTel(){return tel;}
    public void setTel(String tel){this.tel=tel;}
}
