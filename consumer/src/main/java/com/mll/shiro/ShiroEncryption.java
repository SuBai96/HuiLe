package com.mll.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;

/**@author 苏白
 * ShiroEncryption加密
 * */
public class ShiroEncryption {
    public static final String ALGORITHM_NAME = "MD5";  // 基础散列算法
    public static final int HASH_ITERATIONS = 2;        // 自定义散列次数

    public static String MD5Pwd(String username, String pwd) {
        // salt盐值 username
        ByteSource salt = ByteSource.Util.bytes(username);
        String md5Pwd = new SimpleHash(ALGORITHM_NAME, pwd,
                salt, HASH_ITERATIONS).toHex();
        return md5Pwd;
    }

    public static void main(String[] args) {
        System.out.println(MD5Pwd("admin","123456"));
        //加密的字符串
        String username="admin";
        String pwd="123456";
        //盐值，用于和密码混合起来用
        ByteSource salt = ByteSource.Util.bytes("accp");
        //通过SimpleHash来进行加密操作
        SimpleHash hash = new SimpleHash(ALGORITHM_NAME, pwd, salt, HASH_ITERATIONS);

        System.out.println("admin:"+MD5Pwd("admin",pwd));
        //toString()就是调用toHex()
        System.out.println("accp:"+hash.toString());
        System.out.println(salt.toHex()+"\t"+ByteSource.Util.bytes("admin").toHex());
    }
}
