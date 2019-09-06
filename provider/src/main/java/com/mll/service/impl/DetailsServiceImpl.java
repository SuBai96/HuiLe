package com.mll.service.impl;

import com.mll.Mapper.DetailsMapper;
import com.mll.pojo.Details;
import com.mll.service.DetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DetailsServiceImpl implements DetailsService {
    @Autowired
    private DetailsMapper detailsDao;

    @Override
    public List<Details> detailsAll(Integer id) {//查询对应的商品详情信息
        return detailsDao.detailsAll(id);
    }
}
