package com.mll.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RoundRobinRule;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ConfigBean {
	@Bean
	@LoadBalanced    //Ribbon是基于Netflix Ribbon实现的一套客户端负载均衡的工具
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	@Bean
	public IRule getRule() {
		return new RoundRobinRule();
		//return new RandomRule();
		//return new RetryRule();
	}
}
