package com.unisales.listadetarefas.model;

import java.time.LocalDate;

public record TarefaDTO(Long id, String nometarefa, float custo, LocalDate datalimite, Long ordem) {}