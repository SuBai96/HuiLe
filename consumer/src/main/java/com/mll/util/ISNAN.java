package com.mll.util;

public class ISNAN {
    public static void main(String[] args) {
        System.out.println(isName(""));
    }
    //判断用户名是否是手机号
    public static boolean isNAN(String username){
        boolean flag=true;
        char[] chars = username.toCharArray();
        if(chars.length==11){
            for (int i=0;i<chars.length;i++){
                if(Character.isDigit(chars[i])){
                }else {
                    return  false;
                }
            }
        }else {
            return  false;
        }
        return  flag;
    }
    //判断搜索条件是否是收货人是姓名
    public static boolean isName(String keywords){
        boolean flag=true;//默认是收货人姓名
        char[] chars = keywords.toCharArray();
            for (int i=0;i<chars.length;i++){
                if(Character.isDigit(chars[i])){
                    flag=false;
                }else {
                    return  true;
                }
            }
        return  flag;
    }
}
