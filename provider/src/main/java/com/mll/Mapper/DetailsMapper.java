package com.mll.Mapper;

import com.mll.pojo.Details;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DetailsMapper {
     List<Details> detailsAll(@Param("id") Integer id);//查询指定商品详情
}
