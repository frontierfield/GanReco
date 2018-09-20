package com.frontierfield.ganreco;

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
/**
 * Created by kkarimu on 2018/07/07.
 */
/*
通院予定リストの管理
 */
public class tsuin_yotei {
    String ID; /*RDB tsuinyotei key*/
    Boolean t; /*true head*/
    int y_index,m_index,d_index; //日付
    int time;   //時間
    String hospital;    //病院名
    String s_detail;    //診察内容の要約？？？
    String detail;  //診察内容
    String emoji_watch; //時計の絵文字(ネーミングセンス
    long unixtime;  //システム時間

    public tsuin_yotei(){
    }
    public tsuin_yotei(String ID,Boolean t,String hospital,String s_detail,String detail,int y_index,int m_index,int d_index,int time) {
        int unicode = 0x1F551;
        this.emoji_watch = new String(Character.toChars(unicode));

       this.ID = ID;
        this.t = t;
        this.y_index = y_index;
        this.m_index = m_index;
        this.d_index = d_index;
        this.hospital = hospital;
        this.s_detail = s_detail;
        this.detail = detail;
        this.time = time;
        //this.unixtime = calc_unixtime_sec();
    }

    public String getWeek(){
        Calendar cal = Calendar.getInstance();
        Global_Util gu = new Global_Util();
        Integer year = gu.aYotei.get(y_index);
        Integer month = gu.aMonth[m_index] -1;
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

    public long calc_unixtime_day(){
        Global_Util gu = new Global_Util();
        Integer year = gu.aYotei.get(y_index);
        Integer month = gu.aMonth[m_index];
        Integer day = gu.aDay[d_index];

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
        Log.d("unixtime",d.toString() + " " + year.toString() + " " + month.toString() + " " + day.toString());

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
    public int getYearIndex() {
        return y_index;
    }
    public void setYearIndex(int yearIndex){
        this.y_index=yearIndex;
    }

    public int getManthIndex() {
        return m_index;
    }
    public void setManthIndex(int manthIndex){
        this.m_index=manthIndex;
    }

    public int getDayIndex() {
        return d_index;
    }
    public void setDayIndex(int dayIndex){
        this.d_index=dayIndex;
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


/*
    public void tsuinData_delete(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        FirebaseUser mAuthUser;
        mAuthUser = FirebaseAuth.getInstance().getCurrentUser();

        myRef.child("tsuin_yotei").child(mAuthUser.getUid()).child(ID).removeValue();
        user_profile up = new user_profile();
        for(int i = 0;i < up.List_tsuin_yotei.size();i++){
            if(up.List_tsuin_yotei.get(i).ID.equals(this.ID)) {
                up.List_tsuin_yotei.remove(i);
            }
        }
    }

    public void tusinData_add(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        FirebaseUser mAuthUser;
        mAuthUser = FirebaseAuth.getInstance().getCurrentUser();

        if(ID != null) {
            myRef.child("tsuin_yotei").child(mAuthUser.getUid()).child(ID).setValue(this);
            user_profile up = new user_profile();
            for(int i = 0;i < up.List_tsuin_yotei.size();i++){
                if(up.List_tsuin_yotei.get(i).ID.equals(this.ID)){
                    up.List_tsuin_yotei.get(i).hospital = this.hospital;
                    up.List_tsuin_yotei.get(i).s_detail = this.s_detail;
                    up.List_tsuin_yotei.get(i).detail = this.detail;
                    up.List_tsuin_yotei.get(i).y_index = this.y_index;
                    up.List_tsuin_yotei.get(i).m_index = this.m_index;
                    up.List_tsuin_yotei.get(i).d_index = this.d_index;
                    up.List_tsuin_yotei.get(i).time = this.time;
                    up.List_tsuin_yotei.get(i).unixtime = this.unixtime;
                }
            }
        }else{
            DatabaseReference newty = myRef.child("tsuin_yotei").child(mAuthUser.getUid()).push();
            this.ID = newty.getKey();
            Task task = newty.setValue(this);

            //taskが完了次第upに入れたいが、どうやるのかわからない
            //listener実装してもいいが、このクラスに実装するといろんなところでリスナーが呼ばれておかしくなる可能性大
            //関数を引数にする方法もあるが、それだと、thisが呼べない
            //task awaitをやりたいが、メインスレッドでawaitを使うのは禁止されている
            UserProfile up = new UserProfile();
            int addIndex = 0;
            for(int i = 0;i < up.tsuinYoteiList.size();i++){
                if(this.unixtime > up.tsuinYoteiList.get(i).unixtime){
                    addIndex = i + 1;
                }else if(i == up.tsuinYoteiList.size()-1 && this.unixtime > up.tsuinYoteiList.get(i).unixtime){
                    addIndex = up.tsuinYoteiList.size();
                }
            }

            up.tsuinYoteiList.add(addIndex, this);
        }
    }
    public void get_tsuinData_and_input_static(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        FirebaseUser mAuthUser;
        mAuthUser = FirebaseAuth.getInstance().getCurrentUser();

        myRef.child("tsuin_yotei").child(mAuthUser.getUid()).orderByChild("unixtime").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        user_profile up = new user_profile();
                        up.List_tsuin_yotei = new ArrayList<tsuin_yotei>() {
                        };
                        Iterable<DataSnapshot> snapshot_children = dataSnapshot.getChildren();
                        for (DataSnapshot t : snapshot_children) {
                            String t_y, t_m, t_d, t_t;

                            int i_y = -1, i_m = -1, i_d = -1, i_t = -1;

                            t_y = t.child("y_index").getValue(true).toString();
                            t_m = t.child("m_index").getValue(true).toString();
                            t_d = t.child("d_index").getValue(true).toString();
                            t_t = t.child("time").getValue(true).toString();

                            if (t_y != null) {
                                i_y = Integer.parseInt(t_y);
                            }
                            if (t_m != null) {
                                i_m = Integer.parseInt(t_m);
                            }
                            if (t_d != null) {
                                i_d = Integer.parseInt(t_d);
                            }
                            if (t_t != null) {
                                i_t = Integer.parseInt(t_t);
                            }

                            tsuin_yotei temp = new tsuin_yotei(
                                    t.getKey(),
                                    false,
                                    t.child("hospital").getValue(true).toString(),
                                    t.child("s_detail").getValue().toString(),
                                    t.child("detail").getValue(true).toString(),
                                    i_y,
                                    i_m,
                                    i_d,
                                    i_t
                            );
                           // up.tsuinYoteiList.add(temp);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );
    }*/
}
