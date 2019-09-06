package com.mll.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mll.Mapper.Product_cateMapper;
import com.mll.pojo.Details;
import com.mll.pojo.MLL_PRODUCT_CATEGORY;
import java.util.List;

import com.mll.service.Product_cateService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class Product_cateServiceImpl implements Product_cateService {

    @Autowired
    private Product_cateMapper pdao;


    @Cacheable(value = "shitcate")
    @Override
    public List<MLL_PRODUCT_CATEGORY> findAll(int id) {

        List<MLL_PRODUCT_CATEGORY> all = pdao.findAll(id);

        for (MLL_PRODUCT_CATEGORY mll : all) {


            mll.setChildList(pdao.findAll(mll.getMpc_id()));


            for (MLL_PRODUCT_CATEGORY mc : mll.getChildList()) {



                mc.setChildList(pdao.findAll(mc.getMpc_id()));

            }



        }
        return all;

    }

    @Override
    public List<Details> findRandom(int[] mc_id) {

        return pdao.findRandom(mc_id);
    }

    @Override
    public List<Details> NewProduct() {
        return pdao.NewProduct();
    }

    @Override
    public List<Details> FireBuy() {

        return pdao.FireBuy();

    }

    @Override
    public PageInfo<Details> findByid(int mpc_id, Integer pgindex) {



        Page<Details> Pages = PageHelper.startPage(pgindex,1);
        return  new PageInfo<Details>(pdao.findByid(mpc_id)) ;

    }

}
