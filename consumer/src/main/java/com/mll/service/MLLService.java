package com.mll.service;

import com.github.pagehelper.PageInfo;
import com.mll.pojo.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

//@FeignClient("microservice-zuul-gateway")
@FeignClient("microservice-mll")
public interface MLLService {
    /**
     * 苏白 Service
     *
     * */
    //登录
    @PostMapping("/login/User/{username}")
    public MLL_User login(@PathVariable("username") String username);
    //验证名字是否存在
    @RequestMapping("/User/checkusername/{username}")
    public int checkUserName(@PathVariable("username") String username);
    //验证手机号是否注册
    @RequestMapping("/User/checkmobile/{mobile}")
    public int checkMobile(@PathVariable("mobile")String mobile);
    //注册
    @RequestMapping("/User/RegisterUser")
    int registerUser(MLL_User mll_user);
    //根据手机号判断用户登录注册
    @RequestMapping("/User/ByMobileLoginOrReg/{mobile}")
    public MLL_User ByMobileLoginOrReg(@PathVariable("mobile") String mobile);
    //根据手机号修改密码
    @RequestMapping("/User/ByMobileUpdatePwd/{mobile}/{pwd}")
    public Integer ByMobileUpdatePwd(@PathVariable("mobile")String mobile,@PathVariable("pwd")String pwd);
    //个人中心信息查询
    @RequestMapping("/User/getUser/{mu_user_id}")
    public MLL_User ByUser_Id(@PathVariable("mu_user_id")Integer mu_user_id);
    //添加收货地址
    @RequestMapping("/User/SaveAddress")
    public Integer saveAddress(MLL_Receiving mll_receiving);
    //查询收货地址数量
    @RequestMapping("/User/selectAddressCount/{id}")
    public Integer selectAddressCount(@PathVariable("id") Integer mu_user_id);
    //按用户id查询收货地址
    @RequestMapping("/User/selectAddress/{id}")
    public List<MLL_Receiving> selectAddress(@PathVariable("id") Integer mu_user_id);
    //按id删除收货地址
    @RequestMapping("/User/addressDel/{id}")
    public Integer addressDel(@PathVariable("id") Integer mr_id);
    //按地址表id查询收货地址
    @RequestMapping("/User/ByMr_id/{id}")
    public MLL_Receiving ByMr_id(@PathVariable("id")Integer mr_id);
    //修改收货地址
    @RequestMapping("/User/addressUpdate")
    public Integer addressUpdate(MLL_Receiving mll_receiving);
    //设置默认地址
    @RequestMapping("/User/setDefaultAddress/{id}/{mu_user_id}")
    public Integer setDefaultAddress(@PathVariable("id")Integer mr_id,@PathVariable("mu_user_id")Integer mu_user_id);
    //修改密码
    @RequestMapping("/User/userUpdatePwd/{mu_password}/{mu_user_name}")
    public Integer userUpdatePwd(@PathVariable("mu_password")String mu_password,@PathVariable("mu_user_name") String mu_user_name);
    //修改绑定邮箱
    @RequestMapping("/User/userUpdateEmail/{mu_email}/{mu_user_name}")
    public Integer userUpdateEmail(@PathVariable("mu_email")String mu_email,@PathVariable("mu_user_name") String mu_user_name);
    //修改手机号
    @RequestMapping("/User/userUpdateMobile/{mobile}/{mu_user_name}")
    public Integer userUpdateMobile(@PathVariable("mobile")String mobile,@PathVariable("mu_user_name") String mu_user_name);
    //修改用户名
    @RequestMapping("/User/updateUserName/{mu_user_name}/{mu_user_id}")
    public Integer updateUserName(@PathVariable("mu_user_name")String mu_user_name,@PathVariable("mu_user_id")Integer mu_user_id);
    //修改头像
    @RequestMapping("/User/updateUserPhoto/{mu_user_id}")
    public Integer updateUserPhoto(@RequestBody String mu_headphoto,@PathVariable("mu_user_id") Integer mu_user_id);








    /*
    PG Service
    **/
    @RequestMapping("/details/list/{id}")
    public List<Details> detailsAll(@PathVariable("id") Integer id);//查询对应的商品详情信息

    //购物车
    @RequestMapping("/cart/insertCart")
    public int insetShoppingCart(ShoppingCart shoppingCart);//加入购物车(新增)

    //如果是同一件商品则不添加到购物车,修改数量即可(型号跟颜色一致的情况下)
    @RequestMapping("/cart/updateNumber/{ms_colour}/{ms_model}")
    public int updateNumber(@PathVariable("ms_colour")String ms_colour, @PathVariable("ms_model")String ms_model);

    //删除指定的购物车商品
    @RequestMapping("/cart/delete/{msid}")
    public int deleteShoppingCart(@PathVariable("msid") int msid);

    //获取指定用户购物车中商品的总商品数量
    @RequestMapping("/cart/list1/{uid}")
    public int countShoppingCart(@PathVariable("uid") int uid);


    //查询登陆用户的购物车商品(查询)
    @RequestMapping("/cart/list/{id}/{pageindex}/{pagesize}")
    public List<ShoppingCart> ShoppingCartAll(@PathVariable("id") int id,@PathVariable("pageindex") int pageindex,@PathVariable("pagesize") int pagesize);


    @RequestMapping("/cart/Recommend")
    public List<Details> Recommend(@RequestBody int[] mc_id);//为你推荐

    @RequestMapping("/cart/ShoppingCartAll")
    public PageInfo selectShoppingCart(@RequestBody Map<String,Object> map);

    @RequestMapping("/order/insertorder")//新增订单
    public int insertOrder(Order order);
    @RequestMapping("/order/orderXingQingInsert")
    public int orderXingQingInsert(Order_Detail order_detail);

    //将购物车中的商品删除
    @RequestMapping("/order/deleteShopping/{id}")
    public int deleteShopping(@PathVariable("id") int id);

    //获取订单id
    @RequestMapping("/order/selectorderId/{uuid}")
    public int selectorderId(@PathVariable("uuid") int uuid);

    //查询用户下的订单
    @RequestMapping("/cart/successfulPayment")
    public PageInfo<Order> dingdan(@RequestBody Map<String,Object> map);
    //查询用户下的详情订单
    @RequestMapping("/cart/failureToPay/{oid}")
    public List<Order_Detail> dingdanxiangqing(@PathVariable("oid") String moid);
    @RequestMapping("/cart/selectByDingDanDetail")
    public Order selectByDingDanDetail(@RequestBody Map<String,Object> map);

    //确认收货
    @RequestMapping("/Order/confirmReceiving/{moid}")
    public Integer confirmReceiving(@PathVariable("moid") String moid);

    //取消订单
    @RequestMapping("/cart/cancerOrder/{moid}")
    public int cancerOrder(@PathVariable("moid") String moid);
    //修改订单
    @RequestMapping("/cart/updateOrder")
    public Integer updateOrder(@RequestBody Map<String,Object> map);
    //删除订单（假删除）
    @RequestMapping("/Order/DelOrder/{moid}")
    public Integer DelOrder(@PathVariable("moid") String moid);

    /*
    God Service
    **/
    @RequestMapping("/product/all/{id}")
    public List<MLL_PRODUCT_CATEGORY> findAll(@PathVariable("id") int id);

    @RequestMapping("/product/random")
    public List<Details> findRandom(@RequestBody  int[] mc_id);

    @RequestMapping("/product/newproduct")
    public List<Details> NewProduct();

    @RequestMapping("/product/firebuy")
    public List<Details> FireBuy();

    @RequestMapping("/product/findbyid/{mpc_id}/{pgindex}")
    public PageInfo<Details> findById(@PathVariable("mpc_id") int mpc_id, @PathVariable("pgindex") Integer pgindex);
}
