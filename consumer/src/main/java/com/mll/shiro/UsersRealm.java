package com.mll.shiro;


import com.mll.pojo.MLL_User;
import com.mll.service.MLLService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 认证和授权
 * Created by tym on 2019/5/4 0004.
 */
public class UsersRealm extends AuthorizingRealm {

    @Autowired
    private MLLService service;

    /**
     * 鉴权(认证)
     * 控制器调用subject.login(token)将调用该方法
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    /*@Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("鉴权：");
        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
        //调用service根据用户名查询数据库获得Users对象
        MLL_User user = service.login(token.getUsername());
        if(user==null){
            //抛出UnknownAccountException
            return null;
        }
        return new SimpleAuthenticationInfo(user,user.getMu_password(),"");
    }*/



    /**
     * 授权
     * @param principalCollection
     * @return
     */
    /*@Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("授权："+service);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        //获得当前登录用户
        Subject subject = SecurityUtils.getSubject();
        Users user = (Users) subject.getPrincipal();
        //和上面一样
        Users user2 = (Users)principalCollection.getPrimaryPrincipal();
        System.out.println(user+"\t"+user2);

        info.addStringPermission(user2.getPermission());
        info.addRole(user2.getRoles());
        System.out.println("permission:"+user2.getPermission());
        System.out.println("orders:"+user2.getRoles());

        return info;
    }*/

    /**
     * 加密
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("鉴权：");
        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
        //调用service根据用户名查询数据库获得Users对象

        MLL_User user = service.login(token.getUsername());
        if(user==null){
            //抛出UnknownAccountException
            return null;
        }
        //第三个参数：盐值(这个盐是 username)
        ByteSource solt = ByteSource.Util.bytes(user.getMu_user_name());
        //第四个参数：获取这个Realm的信息
        String name = this.getName();
        System.out.println(solt+"\t"+name);
        return new SimpleAuthenticationInfo(user.getMu_user_name(),user.getMu_password(),solt,name);
    }

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("授权："+service);

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获得当前登录用户
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        MLL_User user = service.login(username);

        /*info.addStringPermission(user.getPermission());
        info.addRole(user.getRoles());
        System.out.println("permission:"+user.getPermission());
        System.out.println("orders:"+user.getRoles());
*/
        return info;
    }
}
