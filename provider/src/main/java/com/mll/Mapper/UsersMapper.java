package com.mll.Mapper;

import com.mll.pojo.MLL_Receiving;
import com.mll.pojo.MLL_User;
import org.apache.ibatis.annotations.Param;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;


public interface UsersMapper {
    //登录
    MLL_User getUser(@Param("username") String username);
    //验证名字是否存在
    int checkUserName(@Param("username") String username);
    //验证手机号是否注册
    int checkMobile(@Param("mobile") String mobile);
    //注册用户
    int resiterUser(MLL_User mll_user);
    //根据手机号判断用户登录注册
    MLL_User ByMobileLoginOrReg(@Param("mobile") String mobile);
    //根据手机号修改密码
    Integer ByMobileUpdatePwd(@Param("mobile") String mobile,@Param("pwd")String pwd);
    //个人中心信息查询
    MLL_User ByUser_Id(@Param("mu_user_id")Integer mu_user_id);
    //添加收货地址
    Integer saveAddress(MLL_Receiving mll_receiving);
    //查询收货地址数量
    Integer selectAddressCount(@Param("mu_user_id") Integer mu_user_id);
    //按用户查询收货地址
    List<MLL_Receiving> selectAddress(@Param("mu_user_id") Integer mu_user_id);
    //删除收货地址
    Integer addressDel(@Param("mr_id") Integer mr_id);
    //按地址表id查询收货地址
    MLL_Receiving ByMr_id(@Param("mr_id") Integer mr_id);
    //修改收货地址
    Integer addressUpdate(MLL_Receiving mll_receiving);
    //设置默认地址
    Integer setDefaultAddress(@Param("mr_id") Integer mr_id,@Param("mu_user_id") Integer mu_user_id);
    Integer setDefaultAddress1(@Param("mr_id") Integer mr_id,@Param("mu_user_id") Integer mu_user_id);
    //修改密码
    Integer userUpdatePwd(@Param("mu_password")String mu_password,@Param("mu_user_name")String mu_user_name);
    //修改/绑定邮箱
    Integer userUpdateEmail(@Param("mu_email")String mu_email,@Param("mu_user_name")String mu_user_name);
    //修改/绑定手机号
    Integer userUpdateMobile(@Param("mobile")String mobile,@Param("mu_user_name")String mu_user_name);
    //修改用户名
    Integer updateUserName(@Param("mu_user_name")String mu_user_name,@Param("mu_user_id")Integer mu_user_id);
    //修改头像
    Integer updateUserPhoto(@Param("mu_headphoto")String mu_headphoto,@Param("mu_user_id")Integer mu_user_id);
}
