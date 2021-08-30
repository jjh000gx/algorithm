package com.example.eurekaclient.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * Customer HelloWorld 案例
 * <p>
 * Created by bysocket on 06/22/17.
 */
@RestController
public class CustomerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private RestTemplate restTemplate; // HTTP 访问操作类


    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @RequestMapping("/customer")
    public String customer() {
        String providerMsg = restTemplate.getForEntity("http://EUREKA-CLIENT/provider", String.class).getBody();

        return "Hello,Customer! msg from provider : <br/><br/> " + providerMsg;
    }

    /**
     * 通过HTTP协议，发起远程服务调用，实现一个远程的服务消费。
     * @return
     */
    @RequestMapping("/customer1")
    public List<Map<String, Object>> test() {

        // 通过spring应用命名，获取服务实例ServiceInstance对象
        // ServiceInstance 封装了服务的基本信息，如 IP，端口
        /*
         * 在Eureka中，对所有注册到Eureka Server中的服务都称为一个service instance服务实例。
         * 一个服务实例，就是一个有效的，可用的，provider单体实例或集群实例。
         * 每个service instance都和spring application name对应。
         * 可以通过spring application name查询service instance
         */
        ServiceInstance si = this.loadBalancerClient.choose("eureka-client");
        // 拼接访问服务的URL
        StringBuilder sb = new StringBuilder();
        sb.append("http://").append(si.getHost()).append(":").append(si.getPort()).append("/test");

        System.out.println("本次访问的service是： " + sb.toString());

        // SpringMVC RestTemplate，用于快速发起REST请求的模板对象。
        /*
         * RestTemplate是SpringMVC提供的一个用于发起REST请求的模板对象。
         * 基于HTTP协议发起请求的。
         * 发起请求的方式是exchange。需要的参数是： URL, 请求方式， 请求头， 响应类型，【URL rest参数】。
         */
        RestTemplate rt = new RestTemplate();

        /*
         * 创建一个响应类型模板。
         * 就是REST请求的响应体中的数据类型。
         * ParameterizedTypeReference - 代表REST请求的响应体中的数据类型。
         */
        ParameterizedTypeReference<List<Map<String, Object>>> type = new ParameterizedTypeReference<List<Map<String, Object>>>() {};

        /*
         * ResponseEntity:封装了返回值信息，相当于是HTTP Response中的响应体。
         * 发起REST请求。
         */
        ResponseEntity<List<Map<String, Object>>> response = rt.exchange(sb.toString(), HttpMethod.GET, null, type);
        /*
         * ResponseEntity.getBody() - 就是获取响应体中的java对象或返回数据结果。
         */
        List<Map<String, Object>> result = response.getBody();

        return result;
    }

}