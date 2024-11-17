package com.unisales.listadetarefas.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.unisales.listadetarefas.model.Tarefa;

public interface TarefaRepository extends JpaRepository<Tarefa, Long>{
    @Query("SELECT t FROM Tarefa t WHERE t.descricao = :descricao")
    public Optional<Tarefa> findByDescricao(@Param("descricao") String descricao);
}
