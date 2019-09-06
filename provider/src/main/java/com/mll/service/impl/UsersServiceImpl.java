package com.mll.service.impl;

import com.mll.Mapper.UsersMapper;
import com.mll.pojo.MLL_Receiving;
import com.mll.pojo.MLL_User;
import com.mll.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersServiceImpl implements UsersService {


    @Autowired
    private UsersMapper usersMapper;

    @Override
    public List<MLL_Receiving> selectAddress(Integer mu_user_id) {
        return usersMapper.selectAddress(mu_user_id);
    }

    @Override
    public MLL_Receiving ByMr_id(Integer mr_id) {
        return usersMapper.ByMr_id(mr_id);
    }

    @Override
    public Integer setDefaultAddress(Integer mr_id,Integer mu_user_id) {
        usersMapper.setDefaultAddress1(mr_id,mu_user_id);
        return usersMapper.setDefaultAddress(mr_id,mu_user_id);
    }

    @Override
    public Integer addressUpdate(MLL_Receiving mll_receiving) {
        return usersMapper.addressUpdate(mll_receiving);
    }

    @Override
    public Integer addressDel(Integer mr_id) {
        return usersMapper.addressDel(mr_id);
    }

    @Override
    public Integer updateUserPhoto(String mu_headphoto, Integer mu_user_id) {
        return usersMapper.updateUserPhoto(mu_headphoto,mu_user_id);
    }

    @Override
    public Integer updateUserName(String mu_user_name, Integer mu_user_id) {
        return usersMapper.updateUserName(mu_user_name,mu_user_id);
    }

    @Override
    public Integer userUpdateMobile(String mobile, String mu_user_name) {
        return usersMapper.userUpdateMobile(mobile,mu_user_name);
    }

    @Override
    public Integer userUpdateEmail(String mu_email, String mu_user_name) {
        return usersMapper.userUpdateEmail(mu_email,mu_user_name);
    }

    @Override
    public Integer userUpdatePwd(String mu_password, String mu_user_name) {
        return usersMapper.userUpdatePwd(mu_password,mu_user_name);
    }

    @Override
    public Integer selectAddressCount(Integer mu_user_id) {
        return usersMapper.selectAddressCount(mu_user_id);
    }

    @Override
    public Integer saveAddress(MLL_Receiving mll_receiving) {
        return usersMapper.saveAddress(mll_receiving);
    }
    @Override
    public Integer ByMobileUpdatePwd(String mobile, String pwd) {
        return usersMapper.ByMobileUpdatePwd(mobile,pwd);
    }

    @Override
    public MLL_User ByUser_Id(Integer mu_user_id) {
        return usersMapper.ByUser_Id(mu_user_id);
    }

    @Override
    public MLL_User ByMobileLoginOrReg(String mobile) {
        return usersMapper.ByMobileLoginOrReg(mobile);
    }

    @Override
    public MLL_User getUser(String username) {
        return usersMapper.getUser(username);
    }

    @Override
    public Integer resiterUser(MLL_User mll_user) {
        return usersMapper.resiterUser(mll_user);
    }

    @Override
    public Integer checkUserName(String username) {
        return usersMapper.checkUserName(username);
    }

    @Override
    public Integer checkMobile(String mobile) {
        return usersMapper.checkMobile(mobile);
    }
}
