package com.example.order.clients;


import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.restclient.autoconfigure.RestClientBuilderConfigurer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.Optional;

@Configuration
public class ProductServiceClientConfig {

    // BUILDER 1: Normal builder for Eureka
    @Bean
    @Primary
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RestClient.Builder restClientBuilder(
            RestClientBuilderConfigurer configurer) {

        return configurer.configure(RestClient.builder());
    }

    @Bean
    @LoadBalanced
    public RestClient.Builder loadBalancedRestClientBuilder(
            RestClientBuilderConfigurer configurer) {


        return configurer.configure(RestClient.builder());
    }




    @Bean
    public ProductServiceClient restClientInterface(@LoadBalanced  RestClient.Builder restClientBuilder) {
        // Create a RestClient with the base URL of the product service
        RestClient restClient = restClientBuilder.baseUrl("http://product-service")
                .defaultStatusHandler(HttpStatusCode::is4xxClientError,(((request, response) -> Optional.empty())))
                .build();

        // Create a RestClientAdapter for the RestClient
    RestClientAdapter restClientAdapter=RestClientAdapter.create(restClient);

    // This factory knows how to generate an implementation of the ProductServiceClient interface
    HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(restClientAdapter).build();

    //This will create an implementation of the ProductServiceClient interface that uses the RestClient to make HTTP requests
    ProductServiceClient productServiceClient=factory.createClient(ProductServiceClient.class);

    return productServiceClient;

    }

}
