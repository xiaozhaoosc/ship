package com.android.companyship.bean;

import java.io.Serializable;

/**
 * Created by lenovo on 2016/9/9.
 */
public class AppointBean implements Serializable {

    private String boxnumber;//箱号
    private String boxshape;//箱形
    private String boxsize;//尺寸
    private String nationsnumber;//联合国编号
    private String risklevel;//危险等级

    public String getBoxnumber() {
        return boxnumber;
    }

    public void setBoxnumber(String boxnumber) {
        this.boxnumber = boxnumber;
    }

    public String getBoxshape() {
        return boxshape;
    }

    public void setBoxshape(String boxshape) {
        this.boxshape = boxshape;
    }

    public String getBoxsize() {
        return boxsize;
    }

    public void setBoxsize(String boxsize) {
        this.boxsize = boxsize;
    }

    public String getNationsnumber() {
        return nationsnumber;
    }

    public void setNationsnumber(String nationsnumber) {
        this.nationsnumber = nationsnumber;
    }

    public String getRisklevel() {
        return risklevel;
    }

    public void setRisklevel(String risklevel) {
        this.risklevel = risklevel;
    }
}
