package com.pi.deliveryservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestClientConfig {

    /** Load-balanced: lets us call "http://CourtierService/..." through Eureka. */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        // JDK HttpClient factory: the default one cannot send PATCH requests
        return new RestTemplate(new JdkClientHttpRequestFactory());
    }
}
