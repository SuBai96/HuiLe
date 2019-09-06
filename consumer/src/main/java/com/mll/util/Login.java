package com.mll.util;

import com.mll.pojo.MLL_User;
import com.mll.pojo.Message;
import com.mll.service.MLLService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class Login {
    public static void main(String[] args) {
    }
    //用户名登录
    public static Message login(String username, String password, Message message, MLLService usersService, HttpServletRequest request){
        ServletContext application=request.getSession().getServletContext();
        List<String> Users=(List<String>)application.getAttribute("users");
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        try {
            //登录
            subject.login(token);
            message.setFlag(true);
            MLL_User user = usersService.login(token.getUsername());
            request.getSession().setAttribute("user",user);
            request.getSession().setAttribute("userpwd",password);
            //request.getSession().setMaxInactiveInterval(300);//秒
            Users.add(token.getUsername());
            return message;
        }catch (UnknownAccountException e){
            //用户不存在
            message.setFlag(false);
            message.setMess("用户名不存在");
            return message;
        }catch (IncorrectCredentialsException e){
            //密码不正确
            message.setMess("密码输入错误");
            message.setFlag(false);
            return message;
        }
    }
    //验证密码是否正确
    public static Message checkPwd(String username, String password,MLLService usersService){
        Message message=new Message();
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        try {
            //正确
            subject.login(token);
            message.setFlag(true);
            MLL_User user = usersService.login(token.getUsername());
            return message;
        }catch (UnknownAccountException e){
            //用户不存在
            message.setFlag(false);
            message.setMess("用户名不存在");
            return message;
        }catch (IncorrectCredentialsException e){
            //密码不正确
            message.setMess("原密码输入错误");
            message.setFlag(false);
            return message;
        }
    }
}