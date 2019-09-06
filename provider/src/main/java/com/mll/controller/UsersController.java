package com.mll.controller;

import com.mll.pojo.MLL_Receiving;
import com.mll.pojo.MLL_User;
import com.mll.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@RestController
public class UsersController {
    @Autowired
    private UsersService usersService;

    @RequestMapping("/login/User/{username}")
    public MLL_User login(@PathVariable("username") String username,String mobile) {
        System.out.println("8001Mobile"+mobile);

        return usersService.getUser(username);
    }
    @RequestMapping("/User/checkusername/{username}")
    public Integer checkUserName(@PathVariable("username") String username) {
        return usersService.checkUserName(username);
    }

    @RequestMapping("/User/checkmobile/{mobile}")
    public Integer checkMobile(@PathVariable("mobile") String mobile) {
        return usersService.checkMobile(mobile);
    }

    @RequestMapping("/User/RegisterUser")
    public Integer resiterUser(@RequestBody MLL_User mll_user) {
        return usersService.resiterUser(mll_user);
    }

    @RequestMapping("/User/ByMobileLoginOrReg/{mobile}")
    public MLL_User ByMobileLoginOrReg(@PathVariable("mobile") String mobile) {
        return usersService.ByMobileLoginOrReg(mobile);
    }
    @RequestMapping("/User/ByMobileUpdatePwd/{mobile}/{pwd}")
    public Integer ByMobileUpdatePwd(@PathVariable("mobile")String mobile,@PathVariable("pwd")String pwd) {
        return usersService.ByMobileUpdatePwd(mobile,pwd);
    }
    @RequestMapping("/User/getUser/{mu_user_id}")
    public MLL_User ByUser_Id(@PathVariable("mu_user_id")Integer mu_user_id) {
        return usersService.ByUser_Id(mu_user_id);
    }
    @RequestMapping("/User/SaveAddress")
    public Integer saveAddress(@RequestBody MLL_Receiving mll_receiving){
        return usersService.saveAddress(mll_receiving);
    }
    @RequestMapping("/User/selectAddressCount/{id}")
    public Integer selectAddressCount(@PathVariable("id") Integer mu_user_id){
        return usersService.selectAddressCount(mu_user_id);
    }
    @RequestMapping("/User/selectAddress/{id}")
    public List<MLL_Receiving> selectAddress(@PathVariable("id")Integer mu_user_id){
        return usersService.selectAddress(mu_user_id);
    }

    @RequestMapping("/User/addressDel/{id}")
    public Integer addressDel(@PathVariable("id") Integer mr_id){
        return usersService.addressDel(mr_id);
    }
    @RequestMapping("/User/ByMr_id/{id}")
    public MLL_Receiving ByMr_id(@PathVariable("id")Integer mr_id) {
        return usersService.ByMr_id(mr_id);
    }
    @RequestMapping("/User/addressUpdate")
    public Integer addressUpdate(@RequestBody MLL_Receiving mll_receiving){
        return usersService.addressUpdate(mll_receiving);
    }
    @RequestMapping("/User/setDefaultAddress/{id}/{mu_user_id}")
    public Integer setDefaultAddress(@PathVariable("id")Integer mr_id,@PathVariable("mu_user_id")Integer mu_user_id){
        return usersService.setDefaultAddress(mr_id,mu_user_id);
    }
    @RequestMapping("/User/userUpdatePwd/{mu_password}/{mu_user_name}")
    public Integer userUpdatePwd(@PathVariable("mu_password")String mu_password,@PathVariable("mu_user_name") String mu_user_name){
        return usersService.userUpdatePwd(mu_password,mu_user_name);
    }
    @RequestMapping("/User/userUpdateEmail/{mu_email}/{mu_user_name}")
    public Integer userUpdateEmail(@PathVariable("mu_email")String mu_email,@PathVariable("mu_user_name") String mu_user_name){
        return usersService.userUpdateEmail(mu_email,mu_user_name);
    }
    @RequestMapping("/User/userUpdateMobile/{mobile}/{mu_user_name}")
    public Integer userUpdateMobile(@PathVariable("mobile")String mobile,@PathVariable("mu_user_name") String mu_user_name){
        return usersService.userUpdateMobile(mobile,mu_user_name);
    }
    @RequestMapping("/User/updateUserName/{mu_user_name}/{mu_user_id}")
    public Integer updateUserName(@PathVariable("mu_user_name")String mu_user_name,@PathVariable("mu_user_id")Integer mu_user_id){
        return usersService.updateUserName(mu_user_name,mu_user_id);
    }
    //修改头像
    @RequestMapping("/User/updateUserPhoto/{mu_user_id}")
    public Integer updateUserPhoto(@RequestBody String mu_headphoto,@PathVariable("mu_user_id") Integer mu_user_id){
        return usersService.updateUserPhoto(mu_headphoto,mu_user_id);
    }
}
