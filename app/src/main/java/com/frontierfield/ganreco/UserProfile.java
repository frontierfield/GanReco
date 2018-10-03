package com.frontierfield.ganreco;

import java.util.List;

public class UserProfile {

    private static UserProfile userProfile = null;

    private String UID = null;
    private String lastName = null;
    private String firstName = null;
    private String email = null;
    private int year_Index = -1, month_Index = -1,day_Index = -1;
    private int sex_Index = -1;
    private String zipfront = null;
    private String ziprear = null;
    private String address = null;
    private String tel = null;
    private String cancerType = null;

    // 登録ボタン押下状況
    private boolean isSaved = false;

    private List<TsuinYotei> tsuinYoteiList;

    private UserProfile() {}

    public static synchronized UserProfile getInstance() {
        if (userProfile == null) {
            userProfile = new UserProfile();
        }
        return userProfile;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getUID() {
        return UID;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setYear_Index(int year_Index) {
        this.year_Index = year_Index;
    }

    public int getYear_Index() {
        return year_Index;
    }

    public void setMonth_Index(int month_Index) {
        this.month_Index = month_Index;
    }

    public int getMonth_Index() {
        return month_Index;
    }

    public void setDay_Index(int day_Index) {
        this.day_Index = day_Index;
    }

    public int getDay_Index() {
        return day_Index;
    }

    public void setSex_Index(int sex_Index) {
        this.sex_Index = sex_Index;
    }

    public int getSex_Index() {
        return sex_Index;
    }

    public void setZipfront(String zipfront) {
        this.zipfront = zipfront;
    }

    public String getZipfront() {
        return zipfront;
    }

    public void setZiprear(String ziprear) {
        this.ziprear = ziprear;
    }

    public String getZiprear() {
        return ziprear;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTel() {
        return tel;
    }

    public void setSaved(boolean saved) {
        isSaved = saved;
    }

    public boolean isSaved() {
        return isSaved;
    }

    public void setCancerType(String cancerType) {
        this.cancerType = cancerType;
    }

    public String getCancerType() {
        return cancerType;
    }

    public void setTsuinYoteiList(List<TsuinYotei> tsuinYoteiList) {
        this.tsuinYoteiList = tsuinYoteiList;
    }

    public List<TsuinYotei> getTsuinYoteiList() {
        return tsuinYoteiList;
    }
}
