package com.mll.controller;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class MainsiteErrorController implements ErrorController {
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request){
        //获取statusCode:401,404,500
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if(statusCode == 401){
            return "401";
        }else if(statusCode == 404){
            return "404";
        }else if(statusCode == 403){
            return "403";
        }else{
            return "500";
        }
    }
    @Override
    public String getErrorPath() {
        return "error";
    }
}
