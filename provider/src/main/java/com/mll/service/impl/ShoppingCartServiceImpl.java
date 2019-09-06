package com.mll.service.impl;

import com.mll.Mapper.ShoppingCartMapper;
import com.mll.pojo.Details;
import com.mll.pojo.Order;
import com.mll.pojo.Order_Detail;
import com.mll.pojo.ShoppingCart;
import com.mll.service.ShoppingCartService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService{
    @Autowired
    private ShoppingCartMapper scm;

    @Override
    public int insetShoppingCart(ShoppingCart cart) {
        return scm.insetShoppingCart(cart);
    }

    @Override
    public List<ShoppingCart> ShoppingCartAll(int id,int pageindex, int pagesize) {
        return scm.ShoppingCartAll(id,pageindex,pagesize);
    }

    @Override
    public int updateNumber(String ms_colour,String ms_model){//如果是同一件商品则不添加到购物车,修改数量即可(型号跟颜色一致的情况下)
        return scm.updateNumber(ms_colour,ms_model);
    }


    @Override
    public int deleteShoppingCart(int msid){//删除指定的购物车商品
        return  scm.deleteShoppingCart(msid);
    }
    @Override
    public int countShoppingCart(int uid){//获取指定用户购物车中商品的总商品数量
        return  scm.countShoppingCart(uid);
    }

    @Override
    public List<Map<String,Object>> selectShoppingCart(Map<String,Object> map){
        return scm.selectShoppingCart(map);
    }

    @Override
    public int insertOrder(Order order){
        return scm.insertOrder(order);
    }
    @Override
    //将购物车中的商品新增到订单详情表中
    public int orderXingQingInsert(Order_Detail order_detail){
        return scm.orderXingQingInsert(order_detail);
    }


    //将购物车中的商品删除
    @Override
    public int deleteShopping(int id){
        return scm.deleteShopping(id);
    }

    @Override
    public int selectorderId(int uuid){
        return scm.selectorderId(uuid);
    }

    @Override
    public Order selectByDingDanDetail(Map<String, Object> map) {
        return scm.selectByDingDanDetail(map);
    }

    //查询用户下的订单
    @Override
    public List<Order> dingdan(Map<String,Object> map){
        return  scm.dingdan(map);
    }
    //查询用户下的详情订单
    @Override
    public List<Order_Detail> dingdanxiangqing( String moid){
        return scm.dingdanxiangqing(moid);
    }

    @Override
    public Integer DelOrder(String moid) {
        return scm.DelOrder(moid);
    }

    @Override
    public Integer confirmReceiving(String moid) {
        return scm.confirmReceiving(moid);
    }

    @Override
    public int cancerOrder(String moid) {
        return scm.cancerOrder(moid);
    }

    @Override
    public Integer updateOrder(Map<String, Object> map) {
        return scm.updateOrder(map);
    }

    @Override
    public int cancerOrderDetail(String moid) {
        return scm.cancerOrderDetail(moid);
    }

    @Override
    //为你推荐
    public List<Details> Recommend(int[] mc_id){
        return scm.Recommend(mc_id);
    }
}
