package com.unisales.listadetarefas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unisales.listadetarefas.model.TarefaDTO;
import com.unisales.listadetarefas.service.TarefaService;

@RestController
@RequestMapping("/tarefa")
@CrossOrigin(origins = "*")
public class TarefaController {

    @Autowired
    private TarefaService service;

    @PostMapping("/")
    public ResponseEntity<?> Incluir(@RequestBody TarefaDTO tarefaDTO) {
        return service.Incluir(tarefaDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> Excluir(@PathVariable("id") Long id){
        return service.Excluir(id);
    }
    /*@PutMapping("/{id}")
    public ResponseEntity<String> Editar(@PathVariable("id") Long id, @RequestBody TarefaDTO tarefaDTO){
        return this.service.Editar(id, tarefaDTO);
    }
    @GetMapping("/")
    public Iterable<Tarefa> ListaTarefas(){
        return this.service.ListaTarefas();
    }
    @PutMapping("/ordena/{ordem}/{toup}") 
    public void Ordena(@PathVariable("ordem") Long ordem, @PathVariable("toup") boolean toup){ 
        this.service.Ordena(ordem, toup); 
    }*/
    
}
