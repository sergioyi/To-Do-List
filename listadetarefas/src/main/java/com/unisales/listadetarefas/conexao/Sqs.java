package com.unisales.listadetarefas.conexao;

import java.net.URI;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

@Configuration
public class Sqs {
    @Bean
    public SqsAsyncClient sqsAsyncClient() {
        return SqsAsyncClient.builder()
        .endpointOverride(URI.create("https://sqs.sa-east-1.amazonaws.com/593793054253/topicotarefa"))
        .region(Region.of("sa-east-1"))
        .build();
    }
}
