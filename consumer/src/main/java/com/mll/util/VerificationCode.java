package com.mll.util;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

//获取验证码
public class VerificationCode {
    public static void main(String[] args) {
        /*for (int i=0;i<30;i++){
            int code=(int)((Math.random()*9+1)*10000000);
            SimpleDateFormat sj2 = new SimpleDateFormat("yyyy-MM-dd");
            Random r = new Random();
            String uuid = sj2.format(new Date()).replaceAll("[[\\s-:punct:]]", "")+(code+"");
            System.out.println(uuid);
        }*/
        /*
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAInpS9s1SlED1W", "jsdkS7rPPPixn6iAtodRGBysP3HUd8");
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", "17608487624");
        request.putQueryParameter("SignName", "买来乐");
        request.putQueryParameter("TemplateCode", "SMS_171116667");
        request.putQueryParameter("TemplateParam", "{\"code\":" + ((Math.random() * 1000000) + "").substring(0, 6) + "}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }*/
    }

    public static Integer getVerificationCode(String moboile) {
        int code=(int)((Math.random()*9+1)*100000);
        System.out.println("验证码"+code);
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAInpS9s1SlED1W", "jsdkS7rPPPixn6iAtodRGBysP3HUd8");
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", moboile);
        request.putQueryParameter("SignName", "买来乐");
        request.putQueryParameter("TemplateCode", "SMS_171114073");//SMS_171116667
        request.putQueryParameter("TemplateParam", "{\"code\":" +code+"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return code;
    }
}
