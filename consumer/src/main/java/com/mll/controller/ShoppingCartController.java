package com.mll.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mll.pojo.*;
import com.mll.service.MLLService;
import com.mll.util.ISNAN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class ShoppingCartController {
    @Autowired
    private MLLService detailsService;//注入service层


    @RequestMapping("/cart/insertCart/{yanse}/{xinghao}/{bianhao}")
    @ResponseBody
    public Message insetShoppingCart(HttpSession session, @PathVariable("yanse") String yanse, @PathVariable("xinghao") String xinghao, @PathVariable("bianhao") String bianhao) {//加入购物车(新增)

        Message message = new Message();

        MLL_User user = (MLL_User) session.getAttribute("user");//从Session中拿出用户编号
        //System.out.print("用户"+user);
        if (user == null) {//如果用户等于空代表没登陆 则跳转去登陆页面
            message.setFlag(false);
            message.setMess("请先登陆再进行操作~");
            return message;
        } else {//用户登陆了则进入这里进行购物车添加
            Integer uid = user.getMu_user_id();//获取用户编号
            // System.out.print("用户id:"+uid+"颜色:"+yanse+"型号:"+xinghao+"价格:"+xinghao+"商品编号:"+bianhao+"\n");
            String[] xinghaojiage = xinghao.split(",");//将(型号)跟(价钱)拆分拿出来

            //赋值
            ShoppingCart shoppingCart = new ShoppingCart();//创建购物车对象
            shoppingCart.setMs_pid(Integer.valueOf(bianhao));//商品编号
            shoppingCart.setMs_uid(uid);//用户id
            shoppingCart.setMs_number(1);//商品数量
            shoppingCart.setMs_price(Double.valueOf(xinghaojiage[1]));//商品价格
            shoppingCart.setMs_colour(yanse);//商品颜色
            //System.out.print("颜色"+shoppingCart.getMs_colour());
            shoppingCart.setMs_model(xinghaojiage[0]);//商品型号

            String ms_colour = shoppingCart.getMs_colour();//商品颜色
            String ms_model = shoppingCart.getMs_model();//商品型号
            System.out.print("商品颜色:" + ms_colour + "商品型号:" + ms_model);
            if (detailsService.updateNumber(ms_colour, ms_model) > 0) {//商品相同修改商品数量
                message.setFlag(true);
                message.setMess("加入购物车成功~~");
                return message;
            } else {
                int pd = 0;
                if (detailsService.insetShoppingCart(shoppingCart) > 0) {//表示购物车加入成功
                    message.setFlag(true);
                    message.setMess("加入购物车成功~~");
                    return message;
                } else {//表示购物车加入失败
                    message.setFlag(false);
                    message.setMess("请选择商品属性~");
                    return message;
                }
            }
        }

    }

    @RequestMapping("/cart/list/{id}/{pageindex}/{pagesize}")
    public String ShoppingCartAll(HttpSession session, Model model, @PathVariable("id") int id, @PathVariable("pageindex") int pageindex, @PathVariable("pagesize") int pagesize) {//查询登陆用户的购物车商品(查询)
        if (pageindex == 0) {
            pageindex = 1;
        }

        pagesize = 3;

        MLL_User user = (MLL_User) session.getAttribute("user");//从Session中拿出用户编号
        if (user == null || "".equals(user)) {//如果等于空,则表示用户没登陆,显示提示登陆模块
            model.addAttribute("cart", "nodeng");
        } else if (detailsService.ShoppingCartAll(id, pageindex, pagesize).size() == 0) {//代表用户购物车里面没有商品,显示提示购物模块
            model.addAttribute("cart", "kong");
        } else if (detailsService.ShoppingCartAll(id, pageindex, pagesize).size() > 0) {//显示购物车商品
            model.addAttribute("cart", "ok");
            model.addAttribute("uid", id);//传过去用户id
            pageindex = (pageindex - 1) * pagesize;
            model.addAttribute("pageindex", pageindex);//分页起始角标
            model.addAttribute("pagesize", pagesize);//分页显示的页数
            model.addAttribute("spc", detailsService.ShoppingCartAll(id, pageindex, pagesize));
            model.addAttribute("count", detailsService.countShoppingCart(id));//获取总记录数
        }
        return "ShoppingCart";
    }

    @RequestMapping("/cart/delete/{msid}")//删除
    @ResponseBody
    public Message deleteShoppingCart(@PathVariable("msid") int msid) {
        //System.out.print("aaaaaaaaaaaa商品id:"+msid);
        Message message = new Message();
        if (detailsService.deleteShoppingCart(msid) > 0) {//表示购物车加入成功
            message.setFlag(true);
            message.setMess("删除成功");
            return message;
        } else {//表示购物车加入失败
            message.setFlag(false);
            message.setMess("删除失败");
            return message;
        }
    }


    @RequestMapping("/cart/ShoppingCartAll")//分页
    @ResponseBody
    public PageInfo selectShoppingCart(HttpSession session, String pageindex) throws IOException {
        Map<String, Object> map = new HashMap<>();
        MLL_User user = (MLL_User) session.getAttribute("user");//从Session中拿出用户编号
        if (user != null) {
            map.put("id", user.getMu_user_id());
            map.put("pageindex", pageindex);
            map.put("pagesize", 4);
            return detailsService.selectShoppingCart(map);
        } else {
            return null;
        }
    }


    List<Map<String, Object>> list;

    @RequestMapping("/cart/JieSuan")//结算
    @ResponseBody
    public Message selectShoppingJieSuan(@RequestBody List<Map<String, Object>> list1) {
        System.out.println("购物车商品集合:" + list1);
        for (Map<String, Object> map :
                list1) {
            System.out.println("名称:" + map.get("mp_name") + "图片:" + map.get("image"));
        }
        list = list1;//将获取到的购物车商品赋值给定义号的集合
        return null;
    }

    @RequestMapping("/cart/tiaoDingDan")//跳转订单
    public String tiaozhuanDingDan(HttpSession session, Model model) {
        int num = 0;
        for (Map<String, Object> map :
                list) {
            num += Integer.parseInt(map.get("dj").toString());//获取购物车选中商品的总金额
        }
        MLL_User user = (MLL_User) session.getAttribute("user");//从Session中拿出用户编号
        if (user == null) {//如果用户等于空代表没登陆 则跳转去登陆页面
            return "login";//去登陆页面
        } else {
            Integer uid = user.getMu_user_id();//获取用户编号
            List<MLL_Receiving> address = detailsService.selectAddress(uid);//查询登陆用户的收获地址
            model.addAttribute("address", address);//将收获地址传给确认订单页面显示
            model.addAttribute("dingdan", list);//将购物车中的商品传给确认订单页面显示
            model.addAttribute("sum", num);//将购物车中计算好的商品总价格传给确认订单页面显示
            return "confirmationoforders";
        }
    }


    @RequestMapping("/cart/generatingOrders")//支付宝沙箱支付
    public String generatingOrders(Order order, HttpSession session) {
        MLL_User user = (MLL_User) session.getAttribute("user");//从Session中拿出用户编号
        if (user == null) {//如果用户等于空代表没登陆 则跳转去登陆页面
            return "login";//去登陆页面
        } else {
            order.setMo_fangshi(1);//支付方式
            order.setMo_type(1);//订单状态
            order.setMu_user_id(user.getMu_user_id());//用户编号

            //下单时间
            SimpleDateFormat sj = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String format = sj.format(new Date());
            order.setMo_create_time(format);
            //订单号
            int code=(int)((Math.random()*9+1)*10000000);
            SimpleDateFormat sj2 = new SimpleDateFormat("yyyy-MM-dd");
            Random r = new Random();
            String uuid = sj2.format(new Date()).replaceAll("[[\\s-:punct:]]", "")+(code+"");
            order.setMo_id(uuid);
            session.setAttribute("order", order);//将订单信息存放到session中方便沙箱获取
            session.setAttribute("gwcsp", list);//购物车中的商品
            return "alipayIndex";//去支付宝沙箱类
        }


    }

    @RequestMapping("/cart/successfulPayment")//查询用户下的订单
    @ResponseBody
    public PageInfo<Order> dingdan(HttpSession session,String pageindex,String typeName,String keywords) {
        Map<String, Object> map = new HashMap<>();
        MLL_User user = (MLL_User) session.getAttribute("user");
        PageInfo<Order> pageInfo = null;
        String type="";
        if(typeName.equals("全部有效订单")){
            type="NOT  IN (4,5)";
        }else if(typeName.equals("待发货")){
            type="IN (1)";
        }else if(typeName.equals("待收货")){
            type="IN (2)";
        }
        if(keywords!="" ||keywords!=null){
            if(ISNAN.isName(keywords)){//true 说明搜索条件是按姓名查找
                map.put("name", keywords);
            }else {//false 可能是按订单号也有可能是按姓名
                map.put("moid", keywords);
            }
        }
        if (user != null) {
            map.put("id", 5);
            map.put("pageindex", pageindex);
            map.put("pagesize", 2);
            map.put("type", type);
            pageInfo = detailsService.dingdan(map);
            for (Order dd :
                    pageInfo.getList()) {
                dd.setLists(detailsService.dingdanxiangqing(dd.getMo_id()));
                List<Order_Detail> list1 = dd.getLists();
                for (Order_Detail od :
                        list1) {
                    od.setDetails(detailsService.detailsAll(Integer.parseInt(od.getMp_id())).get(0));
                }
            }
            return pageInfo;
        } else {
            return null;
        }
    }

    @RequestMapping("/cart/selectByDingDanDetail/{moid}")//查询用户下的详情订单
    public String selectByDingDanDetail(HttpSession session, Model model, @PathVariable("moid") String moid) {
        MLL_User user = (MLL_User) session.getAttribute("user");
        Map<String, Object> map = new HashMap<>();
        map.put("moid", moid);
        Order dingdan = null;
        if (user != null) {
            dingdan = detailsService.selectByDingDanDetail(map);
            dingdan.setLists(detailsService.dingdanxiangqing(map.get("moid") + ""));
            List<Order_Detail> list = dingdan.getLists();
            for (Order_Detail od :
                    list) {
                od.setDetails(detailsService.detailsAll(Integer.parseInt(od.getMp_id())).get(0));
            }
            model.addAttribute("OrderDetail", dingdan);
            return "OrderDetails";
        } else {
            return "login";
        }

    }

    //确认收货
    @RequestMapping("/Order/confirmReceiving")
    @ResponseBody
    public Message confirmReceiving(String moid, HttpSession session) {
        MLL_User user = (MLL_User) session.getAttribute("user");//从Session中拿出用户编号
        Message message = new Message();
        if (user == null) {//如果用户等于空代表没登陆 则跳转去登陆页面
            message.setFlag(false);
        } else {
            //修改订单状态
            if(detailsService.confirmReceiving(moid)>0){
                message.setFlag(true);
            }else {
                message.setFlag(false);
            }
        }
        return message;
    }

    @RequestMapping("/cart/cancerOrder/{moid}")
    @ResponseBody
    public Message cancerOrder(@PathVariable("moid") String moid) {
        Message message = new Message();
        if (detailsService.cancerOrder(moid) > 0) {
            message.setFlag(true);
            message.setMess("取消订单成功~");
        } else {
            message.setFlag(false);
            message.setMess("取消订单失败~");
        }
        return message;
    }

    @RequestMapping("/cart/updateOrder")
    @ResponseBody
    public Message updateOrder(@RequestBody Map<String, Object> map) {
        Message message = new Message();
        System.out.println(map);
        if (detailsService.updateOrder(map) > 0) {
            message.setFlag(true);
        } else {
            message.setFlag(false);
            message.setMess("修改订单失败~");
        }
        return message;
    }
    @RequestMapping("/Order/DelOrder")
    @ResponseBody
    public Message DelOrder(String moid,HttpSession session){
        MLL_User user = (MLL_User) session.getAttribute("user");//从Session中拿出用户编号
        Message message = new Message();
        if (user == null) {//如果用户等于空代表没登陆 则跳转去登陆页面
            message.setFlag(false);
        } else {
            //修改订单状态
            if(detailsService.DelOrder(moid)>0){
                message.setFlag(true);
                message.setMess("删除订单成功~");
            }else {
                message.setFlag(false);
            }
        }
        return message;
    }


    @RequestMapping("/cart/Recommend")
    public String Recommend(Model model){//为你推荐

        int mc_id[] = new int[4];


        Set<Integer> set = new HashSet<Integer>();
        while (set.size() < 4) {
            set.add((int) ((19) * Math.random() + 1));
        }

        Object[] ints = set.toArray();


        for (int i = 0; i < ints.length; i++) {

            mc_id[i] = (int) ints[i];
        }
        model.addAttribute("tui",detailsService.Recommend(mc_id));
        return "ShoppingCart";
    }

}
