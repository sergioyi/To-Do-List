package com.unisales.listadetarefas.service;

import java.util.Optional;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.unisales.listadetarefas.model.Tarefa;
import com.unisales.listadetarefas.model.TarefaDTO;
import com.unisales.listadetarefas.persistence.TarefaRepository;

@Service
public class TarefaService {
    @Autowired
    private TarefaRepository repository;
    @Autowired
    private RabbitTemplate rabbitMQServices;

    public ResponseEntity<String> Incluir(@RequestBody TarefaDTO tarefaDTO) {
        if (tarefaDTO.descricao() == null || tarefaDTO.descricao() == "") { 
            return ResponseEntity.badRequest().body("A descrição da tarefa não pode ser nula ou vazio"); 
        }
        Optional<Tarefa> byNometarefa = this.repository.findByDescricao(tarefaDTO.descricao());
        if (byNometarefa.isPresent()) {
            return ResponseEntity.badRequest().body("Não pode haver duas tarefas com a mesma descrição");
        }
        this.repository.save(new Tarefa(new TarefaDTO(tarefaDTO.descricao())));
        //busca a mensagem
        Optional<Tarefa> byDescricao = this.repository.findByDescricao(tarefaDTO.descricao());
        Tarefa payload =byDescricao.get();
        this.enviarMensagem(payload);
        return ResponseEntity.ok().body("Tarefa inclusa com sucesso");
    }

    public ResponseEntity<String> Excluir(@PathVariable Long id) {
        Optional<Tarefa> byId = this.repository.findById(id);

        if (byId.isPresent()) {
            this.repository.deleteById(id);
            return ResponseEntity.ok().body("Tarefa excluida !");
        }

        return ResponseEntity.badRequest().body("Tarefa não encontrada !");
    }

    public ResponseEntity<String> Editar(@PathVariable("id") Long id, @RequestBody TarefaDTO tarefaDTO) {

        Optional<Tarefa> t = this.repository.findById(id);
        if (!t.isPresent()) {
            return ResponseEntity.badRequest().body("Tarefa não encontrada");
        }
        Tarefa tarefa = t.get();

        Optional<Tarefa> byNometarefa = this.repository.findByDescricao(tarefaDTO.descricao());
        if (byNometarefa.isPresent()) {
            return ResponseEntity.badRequest().body("Não pode haver duas tarefas com a mesma descrição");
        }

        tarefa.setDescricao(tarefaDTO.descricao());
        this.repository.save(tarefa);
        return ResponseEntity.ok().body("editado com sucesso !");
    }

    public Iterable<Tarefa> ListaTarefas() {
        return this.repository.findAll();
    }

    public void enviarMensagem(Object mensagem){
        this.rabbitMQServices.convertAndSend("topicotarefa", mensagem);
    }

}
