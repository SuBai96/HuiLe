package com.mll.service;

import com.mll.pojo.MLL_Receiving;
import com.mll.pojo.MLL_User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UsersService {
    //登录
    MLL_User getUser(String username);
    //验证名字是否存在
    Integer checkUserName( String username);
    //验证手机号是否注册
    Integer checkMobile( String mobile);
    //注册用户
    Integer resiterUser(MLL_User mll_user);
    //根据手机号判断用户登录注册
    MLL_User ByMobileLoginOrReg(String mobile);
    //根据手机号修改密码
    Integer ByMobileUpdatePwd( String mobile,String pwd);
    //个人中心信息查询
    MLL_User ByUser_Id(Integer mu_user_id);
    //添加收货地址
    Integer saveAddress(MLL_Receiving mll_receiving);
    //查询收货地址数量
    Integer selectAddressCount(Integer mu_user_id);
    //按用户查询收货地址
    List<MLL_Receiving> selectAddress(Integer mu_user_id);
    //删除收货地址
    Integer addressDel(Integer mr_id);
    //按地址表id查询收货地址
    MLL_Receiving ByMr_id(Integer mr_id);
    //修改收货地址
    Integer addressUpdate(MLL_Receiving mll_receiving);
    //设置默认地址
    Integer setDefaultAddress(Integer mr_id,Integer mu_user_id);
    //修改密码
    Integer userUpdatePwd(String mu_password,String mu_user_name);
    //修改/绑定邮箱
    Integer userUpdateEmail(String mu_email,String mu_user_name);
    //修改/绑定手机号
    Integer userUpdateMobile(String mobile,String mu_user_name);
    //修改用户名
    Integer updateUserName(String mu_user_name,Integer mu_user_id);
    //修改头像
    Integer updateUserPhoto(String mu_headphoto,Integer mu_user_id);


}
