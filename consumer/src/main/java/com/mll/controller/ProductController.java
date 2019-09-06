package com.mll.controller;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.mll.pojo.Details;
import com.mll.pojo.MLL_PRODUCT_CATEGORY;
import com.mll.pojo.MLL_User;
import com.mll.service.MLLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.HighlightOptions;
import org.springframework.data.solr.core.query.HighlightQuery;
import org.springframework.data.solr.core.query.SimpleHighlightQuery;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class ProductController {


    @Autowired
    private MLLService detailsService;//注入service层

    @Autowired
    private SolrTemplate template;


    @ResponseBody
    @RequestMapping("/product_cate/all/{id}")

    public List<MLL_PRODUCT_CATEGORY> All(@PathVariable int id) {

        List<MLL_PRODUCT_CATEGORY> al = detailsService.findAll(id);


        return detailsService.findAll(id);

    }


    @RequestMapping("/product/random")
    @ResponseBody
    public List<Details> Ramdom() {

        int mc_id[] = new int[5];


        Set<Integer> set = new HashSet<Integer>();
        while (set.size() < 5) {
            set.add((int) ((19) * Math.random() + 1));
        }

        Object[] ints = set.toArray();


        for (int i = 0; i < ints.length; i++) {

            mc_id[i] = (int) ints[i];
        }


//        for(int i = 0;i < 5;i ++){
//            mc_id[i] = (int)(Math.random()*10+1);
//        }


        return detailsService.findRandom(mc_id);

    }


    @RequestMapping("/product/newproduct")
    @ResponseBody
    public List<Details> NewProduct() {

        return detailsService.NewProduct();
    }

    ;

    @RequestMapping("/product/firebuy")
    @ResponseBody
    public List<Details> FireBuy() {

        return detailsService.FireBuy();

    }

    @RequestMapping("/product/findbyid/{mpc_id}/{pgindex}")
    public String findByid(@PathVariable("mpc_id") int mpc_id, @PathVariable("pgindex") Integer pgindex, Model model, HttpSession session) {
        MLL_User user = (MLL_User) session.getAttribute("user");
        if(user!=null){
            //购物车数量
        }
        PageInfo<Details> byId = detailsService.findById(mpc_id, pgindex);
        String mpc_name = byId.getList().get(0).getMll_product_category().getMpc_name();
        model.addAttribute("all", byId);
        model.addAttribute("pgs", byId.getList());
        model.addAttribute("names", byId.getList().get(0).getMpc_name());
        model.addAttribute("total", byId.getPages());
        model.addAttribute("danye", byId.getPageNum());
        model.addAttribute("category", mpc_name);
        return "productdetalis";
    }


    @RequestMapping("/product/seach/{zhi}/{pgindex}")
    public String seachProduct(@PathVariable String zhi, @PathVariable Integer pgindex, Model model) {


        if (pgindex == null) {

            pgindex = 0;

        }


        //条件
        Criteria criteria = new Criteria("keywords").contains(zhi);

        //高亮查询
        HighlightQuery query = new SimpleHighlightQuery(criteria);
        query.setOffset((long) pgindex);
        query.setRows(1);
        //需要高亮的属性
        HighlightOptions op = new HighlightOptions();
        // op.addField("mp_name");
        op.setSimplePrefix("<em style='color:red'>");
        op.setSimplePostfix("</em>");

        query.setHighlightOptions(op);
        HighlightPage<Details> hp = template.queryForHighlightPage("mi_core", query, Details.class);

        List<HighlightEntry<Details>> list = hp.getHighlighted();

       /* for(HighlightEntry<Details> entry : list){

            List<HighlightEntry.Highlight> hl = entry.getHighlights();

            *//*for (HighlightEntry.Highlight h : hl){
                //每个域可以有多值
                List<String> ls = h.getSnipplets();
                System.out.println("ls:"+ls);
            }*//*

            if(hl.size()>0 && hl.get(0).getSnipplets().size()>0){
                Details e = entry.getEntity();
                //假设只一个高亮域
               //  e.setMp_name(hl.get(0).getSnipplets().get(0));
               e.setMp_descpiption(hl.get(0).getSnipplets().get(0));
            }


        }*/
        model.addAttribute("pgs", hp.getContent());
        System.out.println("单页" + hp.getTotalElements());
        //model.addAttribute("names",byId.getList().get(0).getMpc_name());
        model.addAttribute("total", hp.getTotalPages());
        model.addAttribute("danye", hp.getPageable().getPageNumber());
        model.addAttribute("val", zhi);

        return "productdetalis.html";
    }

    @RequestMapping("/product/seach")
    public String seachProducts(String zhi, Integer pgindex, Model model) {

        if (pgindex == null) {

            pgindex = 0;

        }


        //条件
        Criteria criteria = new Criteria("keywords").contains(zhi);

        //高亮查询
        HighlightQuery query = new SimpleHighlightQuery(criteria);
        query.setOffset((long) pgindex);
        query.setRows(1);
        //需要高亮的属性
        HighlightOptions op = new HighlightOptions();
        // op.addField("mp_name");
        op.setSimplePrefix("<em style='color:red'>");
        op.setSimplePostfix("</em>");

        query.setHighlightOptions(op);
        HighlightPage<Details> hp = template.queryForHighlightPage("mi_core", query, Details.class);

        List<HighlightEntry<Details>> list = hp.getHighlighted();

        model.addAttribute("pgs", hp.getContent());
        System.out.println("单页" + hp.getTotalElements());
        //model.addAttribute("names",byId.getList().get(0).getMpc_name());
        model.addAttribute("total", hp.getTotalPages());

        model.addAttribute("danye", hp.getPageable().getPageNumber());

        model.addAttribute("val", zhi);

        return "productdetalis.html";
    }


}
