package com.mll.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mll.pojo.*;
import com.mll.service.impl.ShoppingCartServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ShoppingCartController {
    @Autowired
    private ShoppingCartServiceImpl si;

    @RequestMapping("/cart/insertCart")
    public int insetShoppingCart(@RequestBody ShoppingCart shoppingCart) {//加入购物车(新增)
        //System.out.print("11111111111111111111111就回来了");
        //System.out.print("颜色111"+shoppingCart.getMs_colour()+"商品编号:"+shoppingCart.getMs_pid());
        return si.insetShoppingCart(shoppingCart);
    }

    @RequestMapping("/cart/updateNumber/{ms_colour}/{ms_model}")
    public int updateNumber(@PathVariable("ms_colour") String ms_colour, @PathVariable("ms_model") String ms_model) {//如果是同一件商品则不添加到购物车,修改数量即可(型号跟颜色一致的情况下)
        return si.updateNumber(ms_colour, ms_model);
    }

    @RequestMapping("/cart/delete/{msid}")
    public int deleteShoppingCart(@PathVariable("msid") int msid) {//删除指定的购物车商品
        return si.deleteShoppingCart(msid);
    }

    //获取指定用户购物车中商品的总商品数量
    @RequestMapping("/cart/list1/{uid}")
    public int countShoppingCart(@PathVariable("uid") int uid) {
        return si.countShoppingCart(uid);
    }


    @RequestMapping("/cart/list/{id}/{pageindex}/{pagesize}")
    public List<ShoppingCart> ShoppingCartAll(@PathVariable int id, @PathVariable int pageindex, @PathVariable int pagesize) {//查询登陆用户的购物车商品(查询)
        //id=1;//用户编号
        return si.ShoppingCartAll(id, pageindex, pagesize);
    }

    @RequestMapping("/cart/ShoppingCartAll")
    public PageInfo selectShoppingCart(@RequestBody Map<String, Object> map) {//查询登陆用户的购物车商品(查询)
        PageHelper.startPage(Integer.parseInt(map.get("pageindex") + ""), Integer.parseInt(map.get("pagesize") + ""));
        PageInfo pageInfo = new PageInfo(si.selectShoppingCart(map));
        return pageInfo;
    }

    @RequestMapping("/order/insertorder")//新增订单
    public int insertOrder(@RequestBody Order order) {
        //System.out.print("订单对象:"+order);
        return si.insertOrder(order);
    }

    //将购物车中的商品新增到订单详情表中
    @RequestMapping("/order/orderXingQingInsert")
    public int orderXingQingInsert(@RequestBody Order_Detail order_detail) {
        return si.orderXingQingInsert(order_detail);
    }

    //将购物车中的商品删除
    @RequestMapping("/order/deleteShopping/{id}")
    public int deleteShopping(@PathVariable("id") int id) {
        return si.deleteShopping(id);
    }

    //获取订单id
    @RequestMapping("/order/selectorderId/{uuid}")
    public int selectorderId(@PathVariable("uuid") int uuid) {
        return si.selectorderId(uuid);
    }

    //查询用户下的订单
    @RequestMapping("/cart/successfulPayment")
    public PageInfo<Order> dingdan(@RequestBody Map<String, Object> map) {
        PageHelper.startPage(Integer.parseInt(map.get("pageindex") + ""), Integer.parseInt(map.get("pagesize") + ""), "mo_create_time desc");
        PageInfo<Order> pageInfo = new PageInfo(si.dingdan(map));
        return pageInfo;
    }

    //查询用户下的详情订单
    @RequestMapping("/cart/failureToPay/{oid}")
    public List<Order_Detail> dingdanxiangqing(@PathVariable("oid") String moid) {
        return si.dingdanxiangqing(moid);
    }

    @RequestMapping("/cart/selectByDingDanDetail")
    public Order selectByDingDanDetail(@RequestBody Map<String, Object> map) {
        return si.selectByDingDanDetail(map);
    }

    //确认收货
    @RequestMapping("/Order/confirmReceiving/{moid}")
    public Integer confirmReceiving(@PathVariable("moid") String moid) {
        return si.confirmReceiving(moid);
    }

    //取消订单
    @RequestMapping("/cart/cancerOrder/{moid}")
    public int cancerOrder(@PathVariable("moid") String moid) {
        si.cancerOrderDetail(moid);
        return si.cancerOrder(moid);
    }

    //修改订单
    @RequestMapping("/cart/updateOrder")
    public Integer updateOrder(@RequestBody Map<String, Object> map) {
        return si.updateOrder(map);
    }

    @RequestMapping("/Order/DelOrder/{moid}")
    public Integer DelOrder(@PathVariable("moid") String moid) {
        return si.DelOrder(moid);
    }


    @RequestMapping("/cart/Recommend")
    public List<Details> Recommend(@RequestBody int[] mc_id) {//为你推荐
        return si.Recommend(mc_id);
    }
}
