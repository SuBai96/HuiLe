package com.mll.service;


import com.mll.pojo.Details;
import com.mll.pojo.Order;
import com.mll.pojo.Order_Detail;
import com.mll.pojo.ShoppingCart;
import org.apache.ibatis.annotations.Param;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface ShoppingCartService {
    public int insetShoppingCart(ShoppingCart cart);//加入购物车(新增)

    public int updateNumber(String ms_colour, String ms_model);//如果是同一件商品则不添加到购物车,修改数量即可(型号跟颜色一致的情况下)

    public int deleteShoppingCart(@Param("msid") int msid);//删除指定的购物车商品

    public List<ShoppingCart> ShoppingCartAll(@Param("id") int id, @Param("pageindex") int pageindex, @Param("pagesize") int pagesize);//查询登陆用户的购物车商品(查询)

    public int countShoppingCart(@Param("uid") int uid);//获取指定用户购物车中商品的总商品数量

    public int insertOrder(Order order);//新增订单

    //查询用户购物车
    public List<Map<String,Object>> selectShoppingCart(Map<String, Object> map);

    //将购物车中的商品新增到订单详情表中
    public int orderXingQingInsert(Order_Detail order_detail);

    //将购物车中的商品删除
    public int deleteShopping(@Param("id") int id);

    //获取订单id
    public int selectorderId(@Param("uuid") int uuid);

    //查询用户下的订单
    public List<Order> dingdan(Map<String,Object> map);
    //查询用户下的详情订单
    public Order selectByDingDanDetail(Map<String,Object> map);
    public List<Order_Detail> dingdanxiangqing(String moid);

    //确认收货
    public Integer confirmReceiving(String moid);

    //取消订单详情
    public int cancerOrder(String moid);
    public int cancerOrderDetail(String moid);
    //修改订单
    public Integer updateOrder(Map<String,Object> map);
    //删除订单（假删除）
    Integer DelOrder(String moid);
    //为你推荐
    public List<Details> Recommend(@RequestBody int[] mc_id);
}
