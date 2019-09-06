package com.mll.pojo;

import lombok.Getter;
        import lombok.Setter;
        import lombok.ToString;

@Setter
@ToString
@Getter
public class Order_Detail {//订单中商品信息

    private Integer mod_id;//订单详情id
    private String mo_id;//订单表id
    private String mp_id;//商品id
    private String mp_xinghao;//商品型号
    private int mod_count;//商品数量
    private int mod_xiaoji;//商品小计
    private String mod_image;
    private Details details;

}
