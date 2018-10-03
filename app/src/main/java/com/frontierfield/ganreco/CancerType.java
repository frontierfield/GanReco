package com.frontierfield.ganreco;

public class CancerType {

    private int selectedID;
    private String strCancerName;

    public void setSelectedID(int selectedID) {
        this.selectedID = selectedID;
    }

    public int getSelectedID() {
        return this.selectedID;
    }

    public void setStrCancerName(String str){
        this.strCancerName = str;
    }

    public String getStrCancerName() {
        return this.strCancerName;
    }
}
