package com.mll.controller;

import com.mll.pojo.MLL_Receiving;
import com.mll.pojo.MLL_User;
import com.mll.pojo.Message;
import com.mll.service.MLLService;
import com.mll.shiro.ShiroEncryption;
import com.mll.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Controller
@SessionAttributes("user")
public class UsersController {
    @Autowired
    private MLLService usersService;

    //登录
    @RequestMapping("/login/User/{username}/{password}")
    @ResponseBody
    public Message login(@PathVariable("username") String username, @PathVariable("password") String password, HttpServletRequest request) {
        Message message = new Message();
        HttpSession session = request.getSession();
        ServletContext application = session.getServletContext();
        List<String> Users = (List<String>) application.getAttribute("users");
        if (Users.contains(username)) {//用户在线
            message.setFlag(false);
            message.setMess("当前用户在线");
            return message;
        } else {
            if (ISNAN.isNAN(username)) {// true 手机号
                MLL_User mll_user = usersService.ByMobileLoginOrReg(username);
                if (Users.contains(mll_user.getMu_user_name())) {//用户在线
                    message.setFlag(false);
                    message.setMess("当前用户在线");
                    return message;
                } else {
                    if (mll_user == null) {
                        message.setFlag(false);
                        message.setMess("当前手机号未注册用户");
                        return message;
                    } else {
                        return Login.login(mll_user.getMu_user_name(), password, message, usersService, request);
                    }
                }
            } else {
                return Login.login(username, password, message, usersService, request);
            }
        }
    }

    //退出登录
    @RequestMapping("/loginOut/User")
    @ResponseBody
    public Message loginOut(HttpSession session) {
        System.out.println("退出来");
        Message message = new Message();
        MLL_User user = (MLL_User) session.getAttribute("user");
        if (user != null) {
            List<String> Users = (List<String>) session.getServletContext().getAttribute("users");
            System.out.println("退出登录前：" + Users);
            Users.remove(user.getMu_user_name());
            session.setAttribute("user", null);
            message.setFlag(true);
            message.setMess("退出成功~");
            System.out.println("退出登录后：" + Users);
        } else {
            message.setFlag(true);
        }
        return message;
    }

    //验证名字是否存在
    @RequestMapping("/User/checkusername/{username}")
    @ResponseBody
    public Message checkUserName(@PathVariable("username") String username) {
        Message mess = new Message();
        mess.setFlag(true);
        if (usersService.checkUserName(username) > 0) {//名字重复
            mess.setFlag(false);
            mess.setMess("用户名已存在");
        }
        return mess;
    }

    //验证手机号是否注册
    @RequestMapping("/User/checkmobile/{mobile}")
    @ResponseBody
    public Message checkMobile(@PathVariable("mobile") String mobile) {
        Message mess = new Message();
        mess.setFlag(true);
        if (usersService.checkMobile(mobile) > 0) {//该手机号已注册
            mess.setFlag(false);
            mess.setMess("该手机号已注册,请换一个试试~");
        }
        return mess;
    }

    //注册验证码
    @RequestMapping("/User/getVerification/{mobile}")
    @ResponseBody
    public Integer getVerification(@PathVariable("mobile") String mobile) {
        if (usersService.checkMobile(mobile) > 0) {//该手机号已被注册
            return 000000;
        } else {
            System.out.println("mobile" + mobile);
            return VerificationCode.getVerificationCode(mobile);
        }
    }

    //忘记密码验证码
    @RequestMapping("/User/getVerification3/{mobile}")
    @ResponseBody
    public Integer getVerification3(@PathVariable("mobile") String mobile) {
        System.out.println("mobile" + mobile);
        if (usersService.checkMobile(mobile) > 0) {//该手机号已被注册 被注册才可更改密码
            return VerificationCode.getVerificationCode(mobile);
        } else {
            return 000000;
        }
    }

    //登录验证码
    @RequestMapping("/User/getVerification2/{mobile}")
    @ResponseBody
    public Integer getVerification2(@PathVariable("mobile") String mobile) {
        return VerificationCode.getVerificationCode(mobile);
    }

    //注册
    @RequestMapping("/User/Register/{username}/{password}/{mobile}/{email}")
    @ResponseBody
    public Message userRegister(@PathVariable("username") String username, @PathVariable("password") String password, @PathVariable("mobile") String mobile, @PathVariable("email") String email) {
        System.out.println("注册");
        MLL_User mll_user = new MLL_User();
        mll_user.setMu_user_name(username);
        mll_user.setMu_password(ShiroEncryption.MD5Pwd(username, password));
        mll_user.setMu_mobile(mobile);
        mll_user.setMu_email(email);
        mll_user.setMu_status("1");
        mll_user.setMu_headphoto("http://pw5yjo7ws.bkt.clouddn.com/Fq-XQLxlN4pvaG9hsNacVG_B_UH0");
        mll_user.setMu_registerTime(new Date());
        Message mess = new Message();
        if (usersService.registerUser(mll_user) > 0) {
            mess.setFlag(true);
            mess.setMess("注册成功");
        } else {
            mess.setFlag(false);
            mess.setMess("注册失败");
        }
        return mess;
    }

    //登录OR注册
    @RequestMapping("/User/ByMobileLoginOrReg/{mobile}")
    @ResponseBody
    public Message ByMobileLoginOrReg(@PathVariable("mobile") String mobile) {
        MLL_User mll_user = usersService.ByMobileLoginOrReg(mobile);
        Message message = new Message();
        if (mll_user == null) {//用户未注册  注册
            mll_user = new MLL_User();
            UUID uuid = UUID.randomUUID();
            String username = (uuid + "").substring(0, 8);
            String pwd = ShiroEncryption.MD5Pwd(username, "123456");
            mll_user.setMu_user_name(username);
            mll_user.setMu_password(pwd);
            mll_user.setMu_headphoto("http://pw5yjo7ws.bkt.clouddn.com/Fq-XQLxlN4pvaG9hsNacVG_B_UH0");
            mll_user.setMu_registerTime(new Date());
            mll_user.setMu_email("暂未填写邮箱");
            mll_user.setMu_mobile(mobile);
            mll_user.setMu_status("1");
            usersService.registerUser(mll_user);
            message.setFlag(true);
            message.setMess("注册成功");
        } else {
            System.out.println("username\t" + mll_user.getMu_user_name() + "pwd\t" + mll_user.getMu_password());
            message.setFlag(true);
            message.setMess("登录成功");
        }
        return message;
    }

    @RequestMapping("/User/ByMobileUpdatePwd/{mobile}/{pwd}")
    @ResponseBody
    public Message ByMobileUpdatePwd(@PathVariable("mobile") String mobile, @PathVariable("pwd") String pwd) {
        Message message = new Message();
        MLL_User mll_user = usersService.ByMobileLoginOrReg(mobile);
        if (usersService.ByMobileUpdatePwd(mobile, ShiroEncryption.MD5Pwd(mll_user.getMu_user_name(), pwd)) > 0) {
            message.setFlag(true);
            message.setMess("密码修改成功,请重新登录");
        } else {
            message.setFlag(false);
            message.setMess("密码修改失败");
        }
        return message;
    }

    @RequestMapping("/getUser")//获取当前登录对象
    @ResponseBody
    public MLL_User getUser(HttpSession session) {
        MLL_User user = (MLL_User) session.getAttribute("user");
        return user;
    }

    @RequestMapping("/User/getUser/PersonalCenter")
    public String ByUser_Id(HttpSession session, Model model) {
        MLL_User user = (MLL_User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("User", usersService.ByUser_Id(user.getMu_user_id()));
            return "PersonalCenter";
        } else {
            return "login";
        }
    }

    @RequestMapping(value = "/User/SaveAddress")
    @ResponseBody
    public Message saveAddress(String name, String mobile, String address, String detailaddress, String postalcode, HttpSession session) {
        MLL_User user = (MLL_User) session.getAttribute("user");
        Message message = new Message();
        if (user != null) {
            MLL_Receiving mll_receiving = new MLL_Receiving();
            mll_receiving.setMr_default(0);
            if (usersService.selectAddressCount(user.getMu_user_id()) == 0) {
                mll_receiving.setMr_default(1);
            }
            mll_receiving.setMu_user_id(user.getMu_user_id());
            mll_receiving.setMr_name(name);
            mll_receiving.setMr_mobile(mobile);
            mll_receiving.setMr_address(address);
            mll_receiving.setMr_detail_address(detailaddress);
            mll_receiving.setMr_postal_code(postalcode);
            if (usersService.saveAddress(mll_receiving) > 0) {
                message.setFlag(true);
                message.setMess("添加地址成功");
            } else {
                message.setFlag(false);
                message.setMess("添加地址失败");
            }
        } else {
            message.setFlag(false);
            message.setMess("请先登录,再添加收货地址");
        }
        return message;
    }

    @RequestMapping("/User/selectAddressCount")
    @ResponseBody
    public Message selectAddressCount(HttpSession session) {
        MLL_User user = (MLL_User) session.getAttribute("user");
        Message message = new Message();
        if (user != null) {
            if (usersService.selectAddressCount(user.getMu_user_id()) == 3) {
                message.setFlag(false);
                message.setMess("最多只能添加3个收货地址~");
            } else {
                message.setFlag(true);
            }
        } else {
            message.setFlag(false);
            message.setMess("请先登录,再添加收货地址");
        }
        return message;
    }

    @RequestMapping("/User/selectAddress")
    @ResponseBody
    public Message selectAddress(HttpSession session) {
        MLL_User user = (MLL_User) session.getAttribute("user");
        Message message = new Message();
        if (user != null) {
            message.setFlag(true);
            message.setLists(usersService.selectAddress(user.getMu_user_id()));
        } else {
            message.setFlag(false);
            message.setMess("请先登录");
        }
        return message;
    }

    @RequestMapping("/User/addressDel/{id}")
    @ResponseBody
    public Message addressDel(@PathVariable("id") Integer mr_id) {
        Message message = new Message();
        if (usersService.addressDel(mr_id) > 0) {
            message.setFlag(true);
            message.setMess("删除成功~");
        } else {
            message.setFlag(false);
            message.setMess("删除失败~");
        }
        return message;
    }

    @RequestMapping("/User/ByMr_id/{id}")
    @ResponseBody
    public MLL_Receiving ByMr_id(@PathVariable("id") Integer mr_id) {
        return usersService.ByMr_id(mr_id);
    }

    @RequestMapping("/User/addressUpdate")
    @ResponseBody
    public Message addressUpdate(String id, String name, String mobile, String address, String detailaddress, String postalcode, HttpSession session) {
        MLL_User user = (MLL_User) session.getAttribute("user");
        Message message = new Message();
        if (user != null) {
            MLL_Receiving mll_receiving = new MLL_Receiving();
            mll_receiving.setMr_id(Integer.parseInt(id));
            mll_receiving.setMu_user_id(user.getMu_user_id());
            mll_receiving.setMr_name(name);
            mll_receiving.setMr_mobile(mobile);
            mll_receiving.setMr_address(address);
            mll_receiving.setMr_detail_address(detailaddress);
            mll_receiving.setMr_postal_code(postalcode);
            if (usersService.addressUpdate(mll_receiving) > 0) {
                message.setFlag(true);
                message.setMess("编辑地址成功");
            } else {
                message.setFlag(false);
                message.setMess("编辑地址失败");
            }
        } else {
            message.setFlag(false);
            message.setMess("请先登录,再编辑收货地址");
        }
        return message;
    }

    @RequestMapping("/User/setDefaultAddress/{id}")
    @ResponseBody
    public Message setDefaultAddress(@PathVariable("id") Integer mr_id, HttpSession session) {
        MLL_User user = (MLL_User) session.getAttribute("user");
        Message message = new Message();
        if (user != null) {
            if (usersService.setDefaultAddress(mr_id, user.getMu_user_id()) > 0) {
                message.setFlag(true);
                message.setMess("设置成功~");
            } else {
                message.setFlag(false);
                message.setMess("设置失败~");
            }
        } else {
            message.setFlag(false);
            message.setMess("请先登录，再设置默认地址~");
        }
        return message;
    }

    @RequestMapping("/User/PersonalDetails")
    public String PersonalDetails(HttpSession session, Model model) {
        MLL_User user = (MLL_User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("user", user);
            return "PersonalDetails";
        } else {
            return "login";
        }
    }

    @RequestMapping("/User/selectByUser")
    public String selectByUser(HttpSession session, Model model) {
        MLL_User user = (MLL_User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("user", user);
            return "Safety";
        } else {
            return "login";
        }
    }

    @RequestMapping("/User/checkPwd/{pwd}")
    @ResponseBody
    public Message checkPwd(@PathVariable("pwd") String pwd, HttpSession session) {
        Message message = new Message();
        MLL_User user = (MLL_User) session.getAttribute("user");
        if (user != null) {
            return Login.checkPwd(user.getMu_user_name(), pwd, usersService);
        } else {
            message.setFlag(false);
            message.setMess("请先登录，再来修改密码~");
        }
        return message;
    }

    @RequestMapping("/User/getverificationCode")//拿到验证码返回给前台
    @ResponseBody
    public String getverificationCode(HttpSession session) {
        String verificationCode = (String) session.getAttribute("verificationCode");
        return verificationCode;
    }

    //修改密码
    @RequestMapping("/User/userUpdatePwd")
    @ResponseBody
    public Message userUpdatePwd(String password, HttpSession session) {
        Message message = new Message();
        MLL_User user = (MLL_User) session.getAttribute("user");
        List<String> Users = (List<String>) session.getServletContext().getAttribute("users");
        if (user != null) {
            if (usersService.userUpdatePwd(ShiroEncryption.MD5Pwd(user.getMu_user_name(), password), user.getMu_user_name()) > 0) {
                Users.remove(user.getMu_user_name());
                session.setAttribute("user", null);
                message.setFlag(true);
                message.setMess("修改密码成功,请重新登录~");
            } else {
                message.setFlag(false);
                message.setMess("修改密码失败~");
            }
        } else {
            message.setFlag(false);
            message.setMess("请先登录，再修改密码");
        }
        return message;
    }

    @RequestMapping("/User/userUpdateEmail")
    @ResponseBody
    public Message userUpdateEmail(String mu_email, HttpSession session) {
        Message message = new Message();
        MLL_User user = (MLL_User) session.getAttribute("user");
        if (user != null) {
            if (usersService.userUpdateEmail(mu_email, user.getMu_user_name()) > 0) {
                user.setMu_email(mu_email);
                session.setAttribute("user", user);
                message.setFlag(true);
                message.setMess("绑定邮箱成功~");
            } else {
                message.setFlag(false);
                message.setMess("绑定邮箱失败~");
            }
        } else {
            message.setFlag(false);
            message.setMess("请先登录，再修改密码");
        }
        return message;
    }

    @RequestMapping("/User/userUpdateMobile")
    @ResponseBody
    public Message userUpdateMobile(String mu_mobile, HttpSession session) {
        Message message = new Message();
        MLL_User user = (MLL_User) session.getAttribute("user");
        if (user != null) {
            if (usersService.userUpdateMobile(mu_mobile, user.getMu_user_name()) > 0) {
                user.setMu_mobile(mu_mobile);
                session.setAttribute("user", user);
                message.setFlag(true);
                message.setMess("修改手机号码成功~");
            } else {
                message.setFlag(false);
                message.setMess("修改手机号码失败~");
            }
        } else {
            message.setFlag(false);
            message.setMess("请先登录，再修改手机号码");
        }
        return message;
    }

    @RequestMapping("/User/updateUserName")
    @ResponseBody
    public Message updateUserName(String mu_user_name, HttpSession session) {
        Message message = new Message();
        MLL_User user = (MLL_User) session.getAttribute("user");
        String pwd=(String) session.getAttribute("userpwd");
        if (user != null) {
            if (usersService.updateUserName(mu_user_name, user.getMu_user_id()) > 0) {
                usersService.userUpdatePwd(ShiroEncryption.MD5Pwd(mu_user_name,pwd),mu_user_name);
                user.setMu_user_name(mu_user_name);
                session.setAttribute("user",user);
                message.setFlag(true);
            } else {
                message.setFlag(false);
            }
        } else {
            message.setFlag(false);
        }
        return message;
    }

    //修改头像
    @RequestMapping("/User/updateUserPhoto")
    public String updateUserPhoto(HttpSession session, MultipartFile files) throws IOException {
        MLL_User user = (MLL_User) session.getAttribute("user");
        System.out.println(session.getId());
        if (user != null) {
            System.out.println("up:" + files);
            byte[] imgBytes = files.getBytes();
            String imgUrl = Qiniu.upLoadImage(imgBytes);
            user.setMu_headphoto(imgUrl);
            session.setAttribute("user",user);
            System.out.println(session.getId());
            if (usersService.updateUserPhoto(imgUrl, user.getMu_user_id()) > 0) {
                return "PersonalDetails";
            } else {
                return "login";
            }
        } else {
            return "login";
        }
    }

}
