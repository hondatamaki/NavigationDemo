package com.example.navigationdemo.dao;

public class User {
    String wendu;
    String shidu;
    String guangzhao;
    String yanwu;
    String pm25;
    String co2;
    String ranqi;
    String hongwai;

    public User(String wendu, String shidu, String guangzhao, String yanwu, String pm25, String co2, String ranqi, String hongwai) {
        this.wendu = wendu;
        this.shidu = shidu;
        this.guangzhao = guangzhao;
        this.yanwu = yanwu;
        this.pm25 = pm25;
        this.co2 = co2;
        this.ranqi = ranqi;
        this.hongwai = hongwai;
    }

    public String getWendu() {
        return wendu;
    }

    public void setWendu(String wendu) {
        this.wendu = wendu;
    }

    public String getShidu() {
        return shidu;
    }

    public void setShidu(String shidu) {
        this.shidu = shidu;
    }

    public String getGuangzhao() {
        return guangzhao;
    }

    public void setGuangzhao(String guangzhao) {
        this.guangzhao = guangzhao;
    }

    public String getYanwu() {
        return yanwu;
    }

    public void setYanwu(String yanwu) {
        this.yanwu = yanwu;
    }

    public String getPm25() {
        return pm25;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    public String getCo2() {
        return co2;
    }

    public void setCo2(String co2) {
        this.co2 = co2;
    }

    public String getRanqi() {
        return ranqi;
    }

    public void setRanqi(String ranqi) {
        this.ranqi = ranqi;
    }

    public String getHongwai() {
        return hongwai;
    }

    public void setHongwai(String hongwai) {
        this.hongwai = hongwai;
    }

    @Override
    public String toString() {
        return "User{" +
                "wendu='" + wendu + '\'' +
                ", shidu='" + shidu + '\'' +
                ", guangzhao='" + guangzhao + '\'' +
                ", yanwu='" + yanwu + '\'' +
                ", pm25='" + pm25 + '\'' +
                ", co2='" + co2 + '\'' +
                ", ranqi='" + ranqi + '\'' +
                ", hongwai='" + hongwai + '\'' +
                '}';
    }
}
