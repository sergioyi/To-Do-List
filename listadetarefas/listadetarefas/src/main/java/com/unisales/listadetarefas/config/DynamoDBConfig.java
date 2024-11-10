package com.unisales.listadetarefas.config;

import java.net.URI;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Configuration
public class DynamoDBConfig {
    @Bean
    public DynamoDbClient dynamoDbClient(){
        return DynamoDbClient
        .builder()
        .endpointOverride(URI.create("http://localhost:4566"))
        .region(Region.of("sa-east-1"))
        .build();
    }
}
