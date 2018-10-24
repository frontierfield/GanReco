package com.frontierfield.ganreco;

import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
/*
通院予定リストの管理
 */
@Keep
public class TsuinYotei {
    String ID; /*RDB tsuinyotei key*/
    Boolean t; /*true head*/
    int year,month,day; //日付
    int time;   //時間
    String hospital;    //病院名
    String s_detail;    //診察内容の要約？？？
    String detail;  //診察内容
    String emoji_watch; //時計の絵文字(ネーミングセンス
    long unixtime;  //システム時間

    public TsuinYotei(){
    }
    public TsuinYotei(String ID, Boolean t, String hospital, String s_detail, String detail, int year, int month, int day, int time) {
        int unicode = 0x1F551;
        this.emoji_watch = new String(Character.toChars(unicode));

       this.ID = ID;
        this.t = t;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hospital = hospital;
        this.s_detail = s_detail;
        this.detail = detail;
        this.time = time;
        //this.unixtime = calc_unixtime_sec();
    }

    public String getWeek(){
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

    public long calc_unixtime_day(){
        Integer year = this.year;
        Integer month = this.month;
        Integer day = this.year;
        //calc
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy/MM/dd");
        Date d = null;
        try {
            String t_m,t_d;
            if(month < 10){
                t_m ="0" + month.toString();
            }else{
                t_m = month.toString();
            }
            if(day < 10){
                t_d = "0" + day.toString();
            }else{
                t_d = day.toString();
            }
            d = format.parse(year.toString() + "/"+t_m +"/"+t_d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long t_unixTime = d.getTime() / 1000;
        //Log.d("unixtime",d.toString() + " " + year.toString() + " " + month.toString() + " " + day.toString());

        return t_unixTime;
    }

    public long calc_unixtime_sec(){
        long t_unixtime = calc_unixtime_day();

        switch (time){
            case 0:
                t_unixtime += 3600*8;//8:00
                break;
            case 1:
                t_unixtime += 3600*8+1800;//8:30
                break;
            case 2:
                t_unixtime += 3600*9;//9:00
                break;
            case 3:
                t_unixtime += 3600*9+1800;//9:30
                break;
            case 4:
                t_unixtime += 3600*10;//10:00
                break;
            case 5:
                t_unixtime += 3600*10+1800;//10:30
                break;
            case 6:
                t_unixtime += 3600*11;//11:00
                break;
            case 7:
                t_unixtime += 3600*11+1800;//11:30
                break;
            case 8:
                t_unixtime += 3600*12;//12:00
                break;
            case 9:
                t_unixtime += 3600*12+1800;//12:30
                break;
            case 10:
                t_unixtime += 3600*13;//13:00
                break;
            case 11:
                t_unixtime += 3600*13+1800;//13:30
                break;
            case 12:
                t_unixtime += 3600* 14;//14:00
                break;
            case 13:
                t_unixtime += 3600*14+1800;//14:30
                break;
            case 14:
                t_unixtime += 3600*15;//15:00
                break;
            case 15:
                t_unixtime += 3600*15 + 1800;//15:30
                break;
            case 16:
                t_unixtime += 3600*16;//16:00
                break;
            case 17:
                t_unixtime += 3600*16+1800;//16:30
                break;
            case 18:
                t_unixtime += 3600*17;//17:00
                break;
            case 19:
                t_unixtime += 3600*17+1800;//17:30
                break;
            case 20:
                t_unixtime += 3600*18;//18:00
                break;
            case 21:
                t_unixtime += 3600*18+1800;//18:30
                break;
            case 22:
                t_unixtime += 3600*19; //19:00
                break;
            case 23:
                t_unixtime += 3600*19+1800;//19:30
                break;
            case 24:
                t_unixtime += 3600*20;//20:00
                break;
            case 25:
                t_unixtime += 3600*20+1800;//20:30
                break;
            case 26:
                t_unixtime += 3600*21;//21:00
                break;
            case 27:
                t_unixtime += 3600*21+1800;//21:30
                break;
            case 28:
                t_unixtime += 3600*22;//22:00 以降
                break;
        }

        return t_unixtime;
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

    public int getTime() {
        return time;
    }
    public void setTime(int time){
        this.time=time;
    }

    public String getHospital() {
        return hospital;
    }
    public void setHospital(String hospital){
        this.hospital=hospital;
    }

    public String getSDetail() {
        return s_detail;
    }
    public void setSDetail(String s_detail){
        this.s_detail=s_detail;
    }

    public String getDetail() {
        return detail;
    }
    public void setDetail(String detail){
        this.detail=detail;
    }

    public String getEmojiWatch(){
        return emoji_watch;
    }
    public void setEmojiWatch(String emojiwatch){
        this.emoji_watch=emojiwatch;
    }

    public long getUnixtime() {
        return unixtime;
    }
    public void setUnixtime(long unixtime){
        this.unixtime=unixtime;
    }
}
