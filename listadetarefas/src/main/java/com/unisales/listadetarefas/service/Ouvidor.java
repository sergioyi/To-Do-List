package com.unisales.listadetarefas.service;

import org.springframework.stereotype.Component;

import com.unisales.listadetarefas.model.Tarefa;

import io.awspring.cloud.sqs.annotation.SqsListener;
@Component
public class Ouvidor {
    @SqsListener("topicotarefa")
    public void ouvirMensagem(Tarefa mensagem) {
        System.out.println(mensagem.toString());
    }
}
