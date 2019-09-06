package com.mll.controller;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.mll.config.AlipayConfig;
import com.mll.pojo.MLL_User;
import com.mll.pojo.Order;
import com.mll.pojo.Order_Detail;
import com.mll.service.MLLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * 利用蚂蚁金服官方的jdk
 * Created by startym.
 */
@Controller
public class AlipayController {

    @Autowired
    private MLLService mllService;

    /**
     * 支付
     * @param order
     * @param response
     * @throws Exception
     */
    @PostMapping("/tradePay")
    public void tradePay(Order order,HttpServletResponse response, HttpSession session) throws Exception {
        MLL_User user = (MLL_User) session.getAttribute("user");
        if(user!=null){
            order = (Order)session.getAttribute("order");
            //新增订单
            mllService.insertOrder(order);

            //将购物车中的商品新增到订单详情表中
           List<Map<String,Object>> gwclist =(List<Map<String,Object>>)session.getAttribute("gwcsp");
            for (Map<String,Object> map:
                    gwclist) {
                Order_Detail order_detail=new Order_Detail();

                //获取订单id
                //int uuid= mllService.selectorderId(order.getMo_id());
                //System.out.print("订单ID："+uuid);
                order_detail.setMo_id(order.getMo_id());//插入订单id
                order_detail.setMp_id(map.get("pid")+"");//商品id
                order_detail.setMod_count(Integer.valueOf(map.get("number").toString()));
                order_detail.setMod_xiaoji(Integer.valueOf(map.get("dj").toString()));
                order_detail.setMp_xinghao(map.get("xinghao").toString());
                order_detail.setMod_image(map.get("image").toString());
                //新增成功
                mllService.orderXingQingInsert(order_detail);
                //删除购物车中的商品
                System.out.print("购物车id"+map.get("id").toString());
                mllService.deleteShopping(Integer.valueOf(map.get("id").toString()));
            }

            //获得初始化的AlipayClient
            AlipayClient alipayClient = new DefaultAlipayClient(
                    AlipayConfig.gatewayUrl,
                    AlipayConfig.app_id,
                    AlipayConfig.merchant_private_key,
                    "json",
                    AlipayConfig.charset,
                    AlipayConfig.alipay_public_key,
                    AlipayConfig.sign_type);

            //设置请求参数
            AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
            alipayRequest.setReturnUrl(AlipayConfig.return_url);
            alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

            //商户订单号，商户网站订单系统中唯一订单号，必填
            String out_trade_no = String.valueOf(order.getMo_id());
            //付款金额，必填
            String total_amount = order.getMo_sum();
            //订单名称，必填
            String subject = "来自"+user.getMu_user_name()+"的订单";
            //商品描述，可空
            String body = "来自买来乐官网";


            System.out.println(out_trade_no+":"+total_amount+":"+subject+":"+body);

            alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                    + "\"total_amount\":\""+ total_amount +"\","
                    + "\"subject\":\""+ subject +"\","
                    + "\"body\":\""+ body +"\","
                    + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

            //请求
            String result = alipayClient.pageExecute(alipayRequest).getBody();
            //System.out.println("result:"+result);

            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            //输出
            out.println(result);
            out.flush();
            out.close();
        }
    }

    /**
     * 支付宝服务器异步返回商家(POST)
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/notify_url")
    public void notifyUrl(Order order, HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception {

       /* System.out.print("订单对象:"+order.getMo_user_name());*/

        //获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名

        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        //——请在这里编写您的程序（以下代码仅作参考）——

		/* 实际验证过程建议商户务必添加以下校验：
		1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
		2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
		3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
		4、验证app_id是否为该商户本身。
		*/
        if(signVerified) {//验证成功
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //交易状态
            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

            if(trade_status.equals("TRADE_FINISHED")){//支付失败修改订单状态
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序
                //注意：
                //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
                out.println("index");
            }else if (trade_status.equals("TRADE_SUCCESS")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序
                //注意：
                //付款完成后，支付宝系统发送该交易状态通知
                out.println("success");
            }


        }else {//验证失败
            out.println("fail");
            //调试用，写文本函数记录程序运行情况是否正常
            //String sWord = AlipaySignature.getSignCheckContentV1(params);
            //AlipayConfig.logResult(sWord);
        }
    }

    /**
     * 支付宝服务器同步返回用户(GET)
     * @param request
     * @param response
     * @return
     * @throws Exception
     */

    @RequestMapping("/return_url")
    public String returnUrl(HttpServletRequest request,HttpServletResponse response) throws Exception {
        System.out.println("=========return_url");
        //获取支付宝GET过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名
        System.out.println("signVerified:"+signVerified);
        //——请在这里编写您的程序（以下代码仅作参考）——
        if(signVerified) {

            return "success";
        }else {

            return "index";
        }

    }


}