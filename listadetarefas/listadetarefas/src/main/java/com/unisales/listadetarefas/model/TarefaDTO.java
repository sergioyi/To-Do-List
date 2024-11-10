package com.unisales.listadetarefas.model;

import java.time.LocalDate;

public record TarefaDTO(String nometarefa, float custo, LocalDate datalimite) {}