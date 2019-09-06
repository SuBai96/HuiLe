package com.mll.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by tym on 2019/5/4 0004.
 */
@Configuration
public class ShiroConfig {

    /**
     * 创建ShiroFilterFactoryBean
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactory(SecurityManager securityManager){
        // 1.定义shiroFactoryBean
        ShiroFilterFactoryBean factory = new ShiroFilterFactoryBean();
        // 2.设置securityManager
        factory.setSecurityManager(securityManager);

        // 3.LinkedHashMap是有序的，进行顺序拦截器配置，也就是前面匹配放过后不会执行后面拦截，不用HashMap
        //anon:无须认证 authc:必须认证 perms:授权 roles:角色
        Map<String,String> filterMap = new LinkedHashMap<>();
        // 静态资源不拦截，默认静态映射static，所以不要加
        filterMap.put("/**","anon");
        // 4.配置logout过滤器
        //filterMap.put("/logout","logout");
        // 5.除了上面的其他url必须通过认证才可以访问
        //filterMap.put("/add","perms[user:add]");
        //filterMap.put("/update","perms[user:update]");
        //filterMap.put("/**","authc");
        //注意顺序：这里的已经被上面拦截不能过去
        //filterMap.put("/index","anon");

        // 6.设置默认登录的url
        factory.setLoginUrl("/login.html");
        // 7.设置未授权界面
        factory.setUnauthorizedUrl("/NOrights.html");
        // 8.设置shiroFilterFactoryBean的FilterChainDefinitionMap
        factory.setFilterChainDefinitionMap(filterMap);
        return factory;
    }

    /**
     * 创建DefaultWebSecurityManager
     */
    @Bean
    public DefaultWebSecurityManager securityManager(Realm usersRealm){
        DefaultWebSecurityManager sm = new DefaultWebSecurityManager();
        sm.setRealm(usersRealm);
        return  sm;
    }

    /**
     * 创建Realm
     */
    @Bean
    public UsersRealm usersRealm(HashedCredentialsMatcher hcm){
        UsersRealm realm = new UsersRealm();
        realm.setCachingEnabled(true);
        realm.setAuthorizationCachingEnabled(true);
        //设置加密
        realm.setCredentialsMatcher(hcm);
        return realm;
    }

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hcm = new HashedCredentialsMatcher();
        hcm.setHashAlgorithmName(ShiroEncryption.ALGORITHM_NAME);    // 散列算法
        hcm.setHashIterations(ShiroEncryption.HASH_ITERATIONS);      // 散列次数
        return hcm;
    }

    /**
     * 用于thymeleaf和shiro标签整合
     * 没有标签不起作用
     * @return
     */
    @Bean
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }


    /**
     * Shiro生命周期处理器
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }

    /**
     *  开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator和AuthorizationAttributeSourceAdvisor)即可实现此功能
     * @return
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    /**
     * 开启aop注解支持
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }




}
