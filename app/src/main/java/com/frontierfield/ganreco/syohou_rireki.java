package com.frontierfield.ganreco;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by kkarimu on 2018/07/12.
 */

public class syohou_rireki {
    static String ID; /*RDB syohou key*/
    static Boolean t; /*true head*/
    static int y_index,m_index,d_index;//
    static String drugstore;//
    static String s_detail;
    static String detail;

    String uripath;
    String storagepath;
    String thum_path;

    private static List<syohou_rireki> SyohouRirekiList= new ArrayList<syohou_rireki>();

    public syohou_rireki(){
    }
    public syohou_rireki(String ID,Boolean t,String drugstore,String detail,int y_index,int m_index,int d_index,int time) {
        this.ID = ID;
        this.t = t;
        this.y_index = y_index;
        this.m_index = m_index;
        this.d_index = d_index;
        this.drugstore = drugstore;
        this.detail = detail;
    }
    public static List<syohou_rireki> getInstance(){
        return SyohouRirekiList;  //singleton
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
    public void syohou_rireki_add(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        FirebaseUser mAuthUser;
        mAuthUser = FirebaseAuth.getInstance().getCurrentUser();

        if(ID != null) {
            myRef.child("syohou_rireki").
                    child(mAuthUser.getUid()).
                    child(ID).
                    setValue(this);
        }else{
            DatabaseReference newtr =
                    myRef.child("syohou_rireki").
                            child(mAuthUser.getUid()).
                            push();
            this.ID = newtr.getKey();
            newtr.setValue(this);
        }
    }
}
