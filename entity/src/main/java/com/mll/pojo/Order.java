package com.mll.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Setter
@ToString
@Getter
public class Order {//订单详情表
    private String mo_id;//订单号
    private Integer mu_user_id;//用户编号
    private String mr_name;//订单姓名
    private String mr_address;//收货人地址
    private String mr_addressDetail;//收货人详细地址
    private String mr_mobile;//收货人电话
    private String mo_create_time;//下单时间
    private String mo_sum;//订单总金额
    private int mo_type;//订单状态
    private int mo_fangshi;//支付方式
    private List<Order_Detail> lists;
}
