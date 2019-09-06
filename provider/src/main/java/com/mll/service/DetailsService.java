package com.mll.service;

import com.mll.pojo.Details;

import java.util.List;

public interface DetailsService {
    public List<Details> detailsAll(Integer id);//查询对应的商品详情信息
}
