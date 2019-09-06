package com.mll.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.mll.pojo.Details;
import com.mll.pojo.MLL_PRODUCT_CATEGORY;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


public interface Product_cateService {

    public List<MLL_PRODUCT_CATEGORY> findAll(int id);

    public List<Details> findRandom(@RequestBody int[] mc_id);


    public List<Details> NewProduct();


    public List<Details> FireBuy();


    public PageInfo<Details> findByid(int mpc_id, Integer pgindex);


}
