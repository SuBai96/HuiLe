package com.mll.Mapper;

import com.mll.pojo.Details;
import com.mll.pojo.Order;
import com.mll.pojo.Order_Detail;
import com.mll.pojo.ShoppingCart;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ShoppingCartMapper {

    public int insetShoppingCart(ShoppingCart cart);//加入购物车(新增)

    //如果是同一件商品则不添加到购物车,修改数量即可(型号跟颜色一致的情况下)
    public int updateNumber(@Param("ms_colour") String ms_colour, @Param("ms_model") String ms_model);

    public int deleteShoppingCart(@Param("msid") int msid);//删除指定的购物车商品

    public List<ShoppingCart> ShoppingCartAll(@Param("id") int id, @Param("pageindex") int pageindex, @Param("pagesize") int pagesize);//查询登陆用户的购物车商品(查询)

    public int countShoppingCart(@Param("uid") int uid);//获取指定用户购物车中商品的总商品数量
    //查询用户购物车
    public List<Map<String,Object>> selectShoppingCart(Map<String, Object> map);

    public int insertOrder(Order order);//新增订单

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
    public List<Order_Detail> dingdanxiangqing(@Param("moid") String moid);
    //确认收货
    public Integer confirmReceiving(@Param("moid") String moid);

    //取消订单详情
    public int cancerOrder(@Param("moid") String moid);
    public int cancerOrderDetail(@Param("moid") String moid);
    //修改订单
    public Integer updateOrder(Map<String,Object> map);
    //删除订单（假删除）
    Integer DelOrder(@Param("moid") String moid);
    //为你推荐
    public List<Details> Recommend(int[] mc_id);
}
