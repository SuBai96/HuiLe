package com.mll;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@ServletComponentScan("com.mll.listener")
//在调用微服务的时候就能去加载我们的自定义Ribbon负载策略类
//@RibbonClient(name="MICROSERVICE-DEPT",configuration=MyRule.class)
public class Consumer81_App {
	public static void main(String[] args) {
		SpringApplication.run(Consumer81_App.class, args);
	}
}
