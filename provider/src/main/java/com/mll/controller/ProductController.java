package com.mll.controller;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.mll.pojo.Details;
import com.mll.pojo.MLL_PRODUCT_CATEGORY;
import java.util.List;
import java.util.Map;

import com.mll.service.Product_cateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductController {

    @Autowired
    private Product_cateService ps;

    @RequestMapping("/product/all/{id}")
    @ResponseBody
    public List<MLL_PRODUCT_CATEGORY> findAll(@PathVariable int id){

        return ps.findAll(id);


    }

    @RequestMapping("/product/random")
    @ResponseBody
    public List<Details> findrandom(@RequestBody int[] mc_id){



        return ps.findRandom(mc_id);
    }


    @RequestMapping("/product/newproduct")
    @ResponseBody
    public List<Details> NewProduct(){

        return ps.NewProduct();
    }

    @RequestMapping("/product/firebuy")
    @ResponseBody
    public List<Details> FireBuy(){
        return ps.FireBuy();
    }


    @RequestMapping("/product/findbyid/{mpc_id}/{pageindex}")
    //@RequestMapping("/product/findbyid/as/as")
    @ResponseBody//
    public PageInfo<Details> findById(@PathVariable("mpc_id") int mpc_id, @PathVariable("pageindex") Integer pageindex){

        return ps.findByid(mpc_id,pageindex);
    };

}
