package com.android.companyship.bean;

import java.io.Serializable;

/**
 * Created by lenovo on 2016/9/8.
 */
public class Examinebean implements Serializable {

    private String declno;//报检号
    private String shipname;//船名
    private String shipno;//航次
    private String packageno;//箱量
    private String carryno;//提单号
    private String iccard;//箱子类型
    private String yytime;//预约时间
    private String spflag;//不要显示出来
    private String ready03;//ready03  食检|木包装
    private String ready01;//"工业品科", //ready01
    private String sendcyflag;//计划是否发送
    private String dd;//单证情况 不为空则显 示已到，为空显示未到
    private String dh;//
    private String spmark;//预约备注
    private String cyreslut;//查验结果
    private String cytime;//查验操作时间
    private String cyman;//查验操作人
    private String cymark1;//查验备注
    private String ddd;//是否到货

    public String getDeclno() {
        return declno;
    }

    public void setDeclno(String declno) {
        this.declno = declno;
    }

    public String getShipname() {
        return shipname;
    }

    public void setShipname(String shipname) {
        this.shipname = shipname;
    }

    public String getShipno() {
        return shipno;
    }

    public void setShipno(String shipno) {
        this.shipno = shipno;
    }

    public String getPackageno() {
        return packageno;
    }

    public void setPackageno(String packageno) {
        this.packageno = packageno;
    }

    public String getCarryno() {
        return carryno;
    }

    public void setCarryno(String carryno) {
        this.carryno = carryno;
    }

    public String getIccard() {
        return iccard;
    }

    public void setIccard(String iccard) {
        this.iccard = iccard;
    }

    public String getYytime() {
        return yytime;
    }

    public void setYytime(String yytime) {
        this.yytime = yytime;
    }

    public String getSpflag() {
        return spflag;
    }

    public void setSpflag(String spflag) {
        this.spflag = spflag;
    }

    public String getReady03() {
        return ready03;
    }

    public void setReady03(String ready03) {
        this.ready03 = ready03;
    }

    public String getReady01() {
        return ready01;
    }

    public void setReady01(String ready01) {
        this.ready01 = ready01;
    }

    public String getSendcyflag() {
        return sendcyflag;
    }

    public void setSendcyflag(String sendcyflag) {
        this.sendcyflag = sendcyflag;
    }

    public String getDd() {
        return dd;
    }

    public void setDd(String dd) {
        this.dd = dd;
    }

    public String getDh() {
        return dh;
    }

    public void setDh(String dh) {
        this.dh = dh;
    }

    public String getSpmark() {
        return spmark;
    }

    public void setSpmark(String spmark) {
        this.spmark = spmark;
    }

    public String getCyreslut() {
        return cyreslut;
    }

    public void setCyreslut(String cyreslut) {
        this.cyreslut = cyreslut;
    }

    public String getCyman() {
        return cyman;
    }

    public void setCyman(String cyman) {
        this.cyman = cyman;
    }

    public String getCytime() {
        return cytime;
    }

    public void setCytime(String cytime) {
        this.cytime = cytime;
    }

    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public String getCymark1() {
        return cymark1;
    }

    public void setCymark1(String cymark1) {
        this.cymark1 = cymark1;
    }
}
