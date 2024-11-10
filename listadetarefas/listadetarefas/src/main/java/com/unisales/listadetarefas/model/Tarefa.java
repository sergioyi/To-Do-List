package com.unisales.listadetarefas.model;

import java.time.LocalDate;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;
@DynamoDbBean
public class Tarefa {
    private Long id;
    private String nometarefa;
    private float custo;
    private LocalDate datalimite;
    private Long ordem;

    @DynamoDbAttribute("id")
    @DynamoDbPartitionKey
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @DynamoDbAttribute("nometarefa")
    public String getNometarefa() {
        return nometarefa;
    }
    public void setNometarefa(String nometarefa) {
        this.nometarefa = nometarefa;
    }
    @DynamoDbAttribute("custo")
    public float getCusto() {
        return custo;
    }
    public void setCusto(float custo) {
        this.custo = custo;
    }
    @DynamoDbAttribute("datalimite")
    public LocalDate getDatalimite() {
        return datalimite;
    }
    public void setDatalimite(LocalDate datalimite) {
        this.datalimite = datalimite;
    }
    @DynamoDbSortKey
    @DynamoDbAttribute("ordem")
    public Long getOrdem() {
        return ordem;
    }
    public void setOrdem(Long ordem) {
        this.ordem = ordem;
    }
    
    public Tarefa() { }
    public Tarefa(TarefaDTO tarefa){
        this.id = tarefa.id();
        this.nometarefa = tarefa.nometarefa();
        this.custo = tarefa.custo();
        this.datalimite = tarefa.datalimite();
        this.ordem = tarefa.ordem();
    }
    
}
