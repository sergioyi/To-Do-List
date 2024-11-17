package com.unisales.listadetarefas.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.unisales.listadetarefas.model.Tarefa;
import com.unisales.listadetarefas.model.TarefaDTO;
import com.unisales.listadetarefas.persistence.TarefaRepository;
import com.unisales.listadetarefas.service.TarefaService;

public class TarefaControllerTest {
    @Mock
    private TarefaRepository repository;
    @InjectMocks
    private TarefaService service;
    @Mock
    private TarefaService servicemock;
    
    @BeforeEach 
    void setUp() { 
        MockitoAnnotations.openMocks(this);
    } 

    @Test
    void testEditar() {
        Long id = 1L;
        TarefaDTO tarefaDTO = new TarefaDTO("nova tarefa");
        TarefaDTO tarefaAntiga = new TarefaDTO("tarefa antiga");

        Tarefa tarefaExistente = new Tarefa(id, tarefaAntiga.descricao());
        when(repository.findById(id)).thenReturn(Optional.of(tarefaExistente));

        ResponseEntity<String> response = service.Editar(id, tarefaDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("editado com sucesso !", response.getBody());

        verify(repository).save(any(Tarefa.class));
    }

    @Test
    void testExcluir() {
        Long id = 1L;
        TarefaDTO tarefaDTO = new TarefaDTO("nova tarefa");
        Tarefa tarefaExistente = new Tarefa(id, tarefaDTO.descricao());

        when(repository.findById(id)).thenReturn(Optional.of(tarefaExistente));
        ResponseEntity<String> excluir = service.Excluir(id);
        assertEquals(HttpStatus.OK, excluir.getStatusCode());
        assertEquals("Tarefa excluida !", excluir.getBody());
    }

    @Test
    void testIncluir() {
        ResponseEntity<String> incluir = this.service.Incluir(new TarefaDTO("tarefa"));
        assertEquals(incluir.getStatusCode(), HttpStatus.OK);
        assertEquals(incluir.getBody(), "Tarefa inclusa com sucesso");
    }

    @Test
    @DisplayName("Lista de vazia")
    void testListaTarefas() {
        Iterable<Tarefa> listaTarefas = this.service.ListaTarefas();
        List<Tarefa> lista2 = new ArrayList<>();
        assertEquals(listaTarefas, lista2);
    }
}
