package com.example.eurekaclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Eureka Client启动类
 * 是使用SpringBoot启动类实现启动的
 * @EnableEurekaClient 注解，就是用于通知SpringBoot应用，当前应用是一个Irk客户端
 * 需要做一下服务的注册。
 * 使用全局配置文件application.properties配置Eureka Server的相关信息
 * 如：Eureka Server的地址，个数，是否需要安全认证等。
 */
@SpringBootApplication
@EnableDiscoveryClient // Eureka Discovery Client 标识
public class EurekaClientApplication {
	@Bean
	@LoadBalanced
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
	public static void main(String[] args) {
		SpringApplication.run(EurekaClientApplication.class, args);
	}

}
