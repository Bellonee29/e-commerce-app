package com.waystech.authmanagement.emailNotification.config;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class RestTemplateConfig {

    @Value("${connection.time.out}")
    private int connectionTimeOutSeconds;

    @Value("${brevo.base.url}")
    private String baseUrl;

    @Value("${brevo.access.key}")
    private String accessKey;
    public RestTemplate restTemplate(){
        HttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectionRequestTimeout(connectionTimeOutSeconds, TimeUnit.SECONDS)
                        .build())
                .build();
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        httpRequestFactory.setConnectTimeout(connectionTimeOutSeconds * 1000);
        httpRequestFactory.setConnectionRequestTimeout(connectionTimeOutSeconds * 1000);

        return new RestTemplateBuilder(rt -> rt.getInterceptors().add((request, body, execution) ->{
            request.getHeaders().add("api-key", accessKey);
            request.getHeaders().add("accept", "application/json");
            return execution.execute(request, body);
        })).uriTemplateHandler(new DefaultUriBuilderFactory(baseUrl))
                .requestFactory(() -> httpRequestFactory)
                .setConnectTimeout(Duration.ofSeconds(connectionTimeOutSeconds))
                .defaultHeader("Content-Type", "application/json")
                .build();
    }
}
