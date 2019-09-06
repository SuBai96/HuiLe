package com.mll.pojo;

import lombok.*;

import java.io.Serializable;
import java.util.Date;
import org.apache.solr.client.solrj.beans.Field;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Details implements Serializable {//商品详情类

    @Field
    private String id;
    @Field
    private Integer mp_id;//商品编号
    @Field
    private String mp_name;//商品名称
    @Field
    private String mp_descpiption;//商品介绍
    @Field
    private double mp_price;//商品价格
    private Integer mp_stock;//商品库存
    @Field
    private Integer mpc_id;//一级菜单编号
    @Field
    private Integer mpc_parentid;
    @Field
    private Integer mpc_grandid;
    private String mp_file_name;//图片
    private Integer mp_special;//是否为特价商品(1代表是0代表不是)
    private String mp_file_image;//介绍轮播图片
    private String mp_file_special;//型号图片
    private String mp_wheelplanting_image;//详情轮播图片
    private String mp_tpwenzi;//型号文字介绍
    private String mp_image_xiangqing;//商品详情介绍图片
    private String mm_model;//商品型号
    private String mpr_price;//商品价格
    //小图片
    @Field
    private String mp_image;
    //添加的时间
    private Date mp_time;
    //是否是热销产品
    @Field
    private int mp_buy_id;
    //菜单名

    private String mpc_name;

    private MLL_PRODUCT_CATEGORY mll_product_category;
}