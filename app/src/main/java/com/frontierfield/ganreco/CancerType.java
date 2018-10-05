package com.frontierfield.ganreco;

// がん種別（今後、ステージなど項目が増えるかもしれない）
public class CancerType {

    private String strCancerName;

    public void setStrCancerName(String str){
        this.strCancerName = str;
    }

    public String getStrCancerName() {
        return this.strCancerName;
    }
}
