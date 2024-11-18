package com.unisales.listadetarefas.conexao;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.core.Binding.DestinationType;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQ implements CommandLineRunner{
    
    private final String tipoDeEnvio = "amq.topic";
    private final String nomeDeFila = "topicotarefa";
    
    private AmqpAdmin rabbitAdmin;
    public RabbitMQ(AmqpAdmin rabbitAdmin) {
        this.rabbitAdmin = rabbitAdmin;
    }

    private Queue fila (){
        return new Queue(nomeDeFila);
    }
    private TopicExchange trocaDireta (){
        return new TopicExchange(tipoDeEnvio);
    }
    private Binding relacionamento (){
        return new Binding(nomeDeFila, DestinationType.QUEUE, tipoDeEnvio, nomeDeFila, null);
    }
    
    public void contrucao(){
        Queue fila = this.fila();
        TopicExchange trocaDireta = this.trocaDireta();
        Binding relacionamento = this.relacionamento();
        
        this.rabbitAdmin.declareQueue(fila);
        this.rabbitAdmin.declareExchange(trocaDireta);
        this.rabbitAdmin.declareBinding(relacionamento);
    }
    @Override
    public void run(String... args) throws Exception {
        // TODO Auto-generated method stub
        this.contrucao();
    }
}
