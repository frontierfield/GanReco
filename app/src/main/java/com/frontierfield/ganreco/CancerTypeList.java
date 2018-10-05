package com.frontierfield.ganreco;

import java.util.List;

public final class CancerTypeList {

    private static CancerTypeList cancerTypeList = null;
    private List<CancerType> list;
    private int selectIndex;

    public static synchronized CancerTypeList getInstance() {
        if (cancerTypeList == null) {
            cancerTypeList = new CancerTypeList();
        }
        return cancerTypeList;
    }

    public List<CancerType> getList() {
        return list;
    }

    public void setList(List<CancerType> list) {
        this.list = list;
    }

    public int getSelectIndex() {
        return selectIndex;
    }

    public void setSelectIndex(int selectIndex) {
        this.selectIndex = selectIndex;
    }
}
