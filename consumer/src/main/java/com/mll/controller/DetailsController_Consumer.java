package com.mll.controller;

import com.mll.pojo.MLL_User;
import com.mll.service.MLLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class DetailsController_Consumer {
    @Autowired
    private MLLService detailsService;//注入service层


    @RequestMapping(value = "/consumer/details/list/{id}")
    public String detailsAll(HttpSession session,Model model, @PathVariable Integer id){//查询对应的商品详情信息
        System.out.println("进来了");
        //根据商品编号查出的详细商品信息
        model.addAttribute("details",detailsService.detailsAll(id).get(0));
        //int a= detailsService.detailsAll(id).get(0).getMp_id();
        //System.out.print("数据编号等于="+a);


        //从商品详情集合中拿出（介绍轮播图片）的字符
        String mp_file_imageCF = detailsService.detailsAll(id).get(0).getMp_file_image();
        String[] mpi= mp_file_imageCF.split(",");//将介绍轮播图片拆分并且转为数组
        //System.out.print(mpi[0]);
        model.addAttribute("mpi",mpi);//存储到model中转到网页显示

        //从商品详情集合中拿出（型号图片）的字符
        String Mp_file_specialCF = detailsService.detailsAll(id).get(0).getMp_file_special();
        String[] mps= Mp_file_specialCF.split(",");//将型号图片拆分并且转为数组
        model.addAttribute("mps",mps);//存储到model中转到网页显示

        //从商品详情集合中拿出（详情轮播图片）的字符
        String Mp_wheelplanting_image = detailsService.detailsAll(id).get(0).getMp_wheelplanting_image();
        String[] mwi= Mp_wheelplanting_image.split(",");//将详情轮播图片拆分并且转为数组
        model.addAttribute("mwi",mwi);//存储到model中转到网页显示

        //从商品详情集合中拿出（型号文字介绍）的字符
        String Mp_tpwenzi = detailsService.detailsAll(id).get(0).getMp_tpwenzi();
        String[] mwz= Mp_tpwenzi.split(",");//将型号文字介绍拆分并且转为数组
        model.addAttribute("mwz",mwz);//存储到model中转到网页显示

        //从商品详情集合中拿出（商品详情介绍图片）的字符
        String Mp_image_xiangqing = detailsService.detailsAll(id).get(0).getMp_image_xiangqing();
        String[] mix= Mp_image_xiangqing.split(",");//将商品详情介绍图片拆分并且转为数组
        model.addAttribute("mix",mix);//存储到model中转到网页显示

        //从商品详情集合中拿出(商品内存跟型号)的字符
        String Mm_model = detailsService.detailsAll(id).get(0).getMm_model();
        String[] mmb= Mm_model.split(",");//将商品内存跟型号拆分并且转为数组
        model.addAttribute("mmb",mmb);//存储到model中转到网页显示

        //从商品详情集合中拿出(多款商品价格)的字符
        String Mpr_price= detailsService.detailsAll(id).get(0).getMpr_price();
        String[] mpr=Mpr_price.split(",");//将多款商品价格拆分并且转为数组
        model.addAttribute("mpr",mpr);//存储到model中转到网页显示

        //从Session中拿出用户编号
        /*MLL_User user = (MLL_User) session.getAttribute("user");
        Integer uid=user.getMu_user_id();//获取用户编号
        model.addAttribute("userid",uid);//存储到model中转到网页显示*/
        return  "details";
    }

}
