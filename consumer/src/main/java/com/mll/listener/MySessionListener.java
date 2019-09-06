package com.mll.listener;

import com.mll.pojo.MLL_User;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.ArrayList;
import java.util.List;

@WebListener
public class MySessionListener implements HttpSessionListener, ServletContextListener {
    private ServletContext application;

    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session=se.getSession();
        session.setMaxInactiveInterval(200);
        System.out.println("session创建"+session.getId());
    }

    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session=se.getSession();
        System.out.println("session销毁"+session.getId());
        MLL_User user=(MLL_User) session.getAttribute("user");
        List<String> users=(List<String>)application.getAttribute("users");
        if(user!=null){
            if(users.contains(user.getMu_user_name())){//该用户已在线
                users.remove(user.getMu_user_name());
            }
        }
    }
    /**
     * application销毁
     */
    public void contextDestroyed(ServletContextEvent sce)  {
        System.out.println("application销毁");
    }

    /**
     * application初始化
     */
    public void contextInitialized(ServletContextEvent sce)  {
        System.out.println("application初始化");
        application = sce.getServletContext();
        //保存所有在线用户
        application.setAttribute("users", new ArrayList<String>());
    }

}
