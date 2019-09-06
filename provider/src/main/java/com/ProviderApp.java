package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.mll.Mapper")
@EnableCaching
public class ProviderApp {
    public  static void main(String[]args){
        SpringApplication.run(ProviderApp.class,args);
    }
}
