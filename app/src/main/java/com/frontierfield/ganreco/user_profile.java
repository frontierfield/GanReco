package com.frontierfield.ganreco;

import android.util.Log;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by kkarimu on 2018/06/17.
 */


//データ保持専用
public class user_profile {

    public static String UID = null;
    public static String lastName = null;
    public static String firstName = null;
    public static String email = null;
    public static int year_Index = -1, month_Index = -1, day_Index = -1;
    public static int sex_Index = -1;
    public static String zipfront = null;
    public static String ziprear = null;
    public static String address = null;
    public static boolean isSave = false;

    public void DebugOutput() {
        Global_Util GU = new Global_Util();

        Log.d("UID", "" + UID + "");
        Log.d("lastName", "" + lastName + "");
        Log.d("firstName", "" + firstName + "");
        Log.d("email", "" + email + "");
        if (year_Index != -1 && month_Index != -1 && day_Index != -1 && sex_Index != -1) {
            Log.d("year", "" + GU.aYear.get(year_Index) + "");
            Log.d("month", "" + GU.aMonth[month_Index] + "");
            Log.d("day", "" + GU.aDay[day_Index] + "");
            Log.d("sex", "" + GU.aSex[sex_Index] + "");
        }
        Log.d("zipfront", "" + zipfront + "");
        Log.d("ziprear", "" + ziprear + "");
        Log.d("address", "" + address + "");

        if (List_tsuin_yotei != null) {
            Integer s = List_tsuin_yotei.size();
            Log.d("Tsuin Yotei size:", s.toString());

            for (int i = 0; i < List_tsuin_yotei.size(); i++) {
                Log.d("Tsuin yotei ID:", List_tsuin_yotei.get(i).ID);
                Log.d("Tsuin yotei hospital:", List_tsuin_yotei.get(i).hospital);
            }
        }
        if (List_tsuin_rireki != null) {
            Integer s = List_tsuin_rireki.size();
            Log.d("Tsuin Yotei size:", s.toString());

            for (int i = 0; i < List_tsuin_rireki.size(); i++) {
                Log.d("Tsuin yotei ID:", List_tsuin_rireki.get(i).ID);
                Log.d("Tsuin yotei hospital:", List_tsuin_rireki.get(i).hospital);
            }
        }
        if (List_syohou_rireki != null) {
            Integer s = List_syohou_rireki.size();
            Log.d("Tsuin Yotei size:", s.toString());

            for (int i = 0; i < List_syohou_rireki.size(); i++) {
                Log.d("Tsuin yotei ID:", List_syohou_rireki.get(i).ID);
                Log.d("Tsuin yotei hospital:", List_syohou_rireki.get(i).drugstore);
            }
        }
        if (List_kensa_rireki != null) {
            Integer s = List_kensa_rireki.size();
            Log.d("Tsuin Yotei size:", s.toString());

            for (int i = 0; i < List_kensa_rireki.size(); i++) {
                Log.d("Tsuin yotei ID:", List_kensa_rireki.get(i).ID);
                Log.d("Tsuin yotei hospital:", List_kensa_rireki.get(i).hospital);
            }
        }

    }

    public static List<tsuin_yotei> List_tsuin_yotei;
    public static List<tsuin_rireki> List_tsuin_rireki;
    public static List<syohou_rireki> List_syohou_rireki;
    public static List<kensa_rireki> List_kensa_rireki;

    public user_profile() {
    }

    public int CalcAge(int _year, int _month, int _day) {
        Calendar now = Calendar.getInstance();
        int age = now.get(Calendar.YEAR) - _year;

        if (now.get(Calendar.MONTH) < _month ||
                (now.get(Calendar.MONTH) == _month && now.get(Calendar.DATE) < _day)) {
            age--;
        }

        return age;
    }
}

