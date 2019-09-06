package com.mll.controller;

import com.mll.pojo.Details;
import com.mll.service.impl.DetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DetailsController {
    @Autowired
    private DetailsServiceImpl dsic;

    @RequestMapping("/details/list/{id}")
    public List<Details> detailsAll(@PathVariable int id){
        return dsic.detailsAll(id);
    }
}
