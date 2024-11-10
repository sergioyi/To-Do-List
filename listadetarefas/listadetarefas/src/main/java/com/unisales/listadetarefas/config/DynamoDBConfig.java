package com.unisales.listadetarefas.config;

import java.net.URI;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Configuration
public class DynamoDBConfig {
    @Bean
    public DynamoDbClient dynamoDbClient(){
        return DynamoDbClient
        .builder()
        .endpointOverride(URI.create("https://localhost.localstack.cloud:4566"))//"http://localhost:4566"))
        .region(Region.of("sa-east-1"))
        .credentialsProvider(StaticCredentialsProvider.create(
                AwsBasicCredentials.create("test", "test")
        ))
        .build();
    }
}
