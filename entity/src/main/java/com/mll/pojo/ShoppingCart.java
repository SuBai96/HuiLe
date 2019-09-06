package com.mll.pojo;


public class ShoppingCart {//购物车类
    private int ms_id;//购物车编号
    private int ms_pid;//商品编号
    private int ms_uid;//用户编号
    private int ms_number;//商品数量
    private double ms_price;//商品价格
    private String ms_colour;//商品颜色
    private String ms_model;//商品型号
    private String mp_image;//商品图片
    private String mp_name;//商品名称

    public int getMs_id() {
        return ms_id;
    }

    public int getMs_pid() {
        return ms_pid;
    }

    public int getMs_uid() {
        return ms_uid;
    }

    public int getMs_number() {
        return ms_number;
    }

    public double getMs_price() {
        return ms_price;
    }

    public String getMs_colour() {
        return ms_colour;
    }

    public String getMs_model() {
        return ms_model;
    }

    public String getMp_image() {
        return mp_image;
    }

    public String getMp_name() {
        return mp_name;
    }

    public void setMs_id(int ms_id) {
        this.ms_id = ms_id;
    }

    public void setMs_pid(int ms_pid) {
        this.ms_pid = ms_pid;
    }

    public void setMs_uid(int ms_uid) {
        this.ms_uid = ms_uid;
    }

    public void setMs_number(int ms_number) {
        this.ms_number = ms_number;
    }

    public void setMs_price(double ms_price) {
        this.ms_price = ms_price;
    }

    public void setMs_colour(String ms_colour) {
        this.ms_colour = ms_colour;
    }

    public void setMs_model(String ms_model) {
        this.ms_model = ms_model;
    }

    public void setMp_image(String mp_image) {
        this.mp_image = mp_image;
    }

    public void setMp_name(String mp_name) {
        this.mp_name = mp_name;
    }
}
