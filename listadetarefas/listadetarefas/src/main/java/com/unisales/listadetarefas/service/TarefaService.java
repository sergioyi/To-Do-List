package com.unisales.listadetarefas.service;

import java.text.AttributedCharacterIterator.Attribute;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.unisales.listadetarefas.model.Tarefa;
import com.unisales.listadetarefas.model.TarefaDTO;

import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

@Service
public class TarefaService {

    private DynamoDbTemplate dbTemplate;

    public TarefaService(DynamoDbTemplate dbTemplate) {
        this.dbTemplate = dbTemplate;
    }

    public ResponseEntity<?> Incluir(@RequestBody TarefaDTO tarefaDTO) {
        /**
         * Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
         * expressionAttributeValues.put(":name", new
         * AttributeValue().withS("NomeDesejado"));
         * 
         * QueryRequest queryRequest = new QueryRequest()
         * .withTableName("NomeDaTabela")
         * .withIndexName("NomeDoGSI") // Nome do índice secundário
         * .withKeyConditionExpression("name = :name")
         * .withExpressionAttributeValues(expressionAttributeValues);
         * 
         * QueryResult result = dynamoDB.query(queryRequest);
         * 
         */
        //findByNometarefa
        /*String nometarefa = tarefaDTO.nometarefa();

        Map<String, AttributeValue> findByNometarefa = new HashMap<>(); 
        findByNometarefa.put(":nometarefa", AttributeValue.builder().s(nometarefa).build());
        QueryRequest queryRequest = new QueryRequest().
//        .tableName()
        //.indexName()
        //  return 

        Optional<Tarefa> byNometarefa = this.repository.findByNometarefa(tarefaDTO.nometarefa());
        if (byNometarefa.isPresent()) {
            return ResponseEntity.badRequest().body("Não pode haver duas tarefas com o mesmo nome");
        }*/

        Tarefa novaTarefa = new Tarefa(tarefaDTO);
        dbTemplate.save(novaTarefa);

        /*List<Tarefa> all = this.repository.findAll();
        atualizarOrdens(all);*/

        return ResponseEntity.ok().body("Tarefa incluída");
    }

    public ResponseEntity<String> Excluir(@PathVariable Long id) {
        Map<Long, Attribute> findById = new HashMap<>();
        findById.remove("id", AttributeValue.builder().n(id.toString()).build());
        return ResponseEntity.ok().body("Tarefa excluida");
    }
/*
    public ResponseEntity<String> Editar(@PathVariable("id") Long id, @RequestBody TarefaDTO tarefaDTO) {
        Optional<Tarefa> t = this.repository.findById(id);
        if (!t.isPresent()) {
            return ResponseEntity.badRequest().body("Tarefa não encontrada");
        }
        Tarefa tarefa = t.get();

        List<Tarefa> all = this.repository.findAll();
        all.remove(tarefa);
        for (Tarefa tarefaExistente : all) {
            if (tarefaExistente.getNometarefa().equals(tarefaDTO.nometarefa())) {
                return ResponseEntity.badRequest().body("o novo nome da tarefa já existe na base de dados.");
            }
        }
        List<Tarefa> all2 = this.repository.findAll();
        all2.remove(t.get());

        tarefa.setNometarefa(tarefaDTO.nometarefa());
        tarefa.setCusto(tarefaDTO.custo());
        tarefa.setDatalimite(tarefaDTO.datalimite());
        this.repository.save(tarefa);

        return ResponseEntity.ok().body("editado com sucesso !");
    }

    public Iterable<Tarefa> ListaTarefas() {
        List<Tarefa> all = this.repository.findAllOrderByOrdem();

        List<Tarefa> lista = new ArrayList<Tarefa>();
        for (Tarefa tarefa : all) {
            Tarefa dto = new Tarefa(tarefa.getId(), tarefa.getNometarefa(), tarefa.getCusto(), tarefa.getDatalimite(),
                    tarefa.getOrdem());
            lista.add(dto);
        }
        return lista;
    }

    public void Ordena(Long ordem, boolean toup) {
        Tarefa tarefa = this.repository.findByOrdem(ordem);

        if (tarefa == null) {
            throw new IllegalArgumentException("Tarefa com a ordem " + ordem + " nao encontrada");
        }

        if (toup) {
            if (ordem == 1) {
                throw new IllegalArgumentException("Nao e possível mover a tarefa com ordem 1 para cima.");
            }

            Long ordemDecima = ordem - 1;
            Tarefa tarefaDecima = this.repository.findByOrdem(ordemDecima);

            if (tarefaDecima == null) {
                throw new IllegalArgumentException("Tarefa com a ordem " + ordemDecima + " nao encontrada");
            }

            System.out.println("A tarefa acima era: " + tarefaDecima);
            tarefa.setOrdem(null);
            this.repository.save(tarefa);

            tarefaDecima.setOrdem(ordem);
            this.repository.save(tarefaDecima);

            tarefa.setOrdem(ordemDecima);
            this.repository.save(tarefa);
        } else {
            List<Tarefa> todasTarefas = this.repository.findAll();
            Long ultimaOrdem = todasTarefas.stream().mapToLong(Tarefa::getOrdem).max().orElseThrow(
                    () -> new IllegalArgumentException("Não foi possível determinar a ultima ordem da lista."));

            if (ordem.equals(ultimaOrdem)) {
                throw new IllegalArgumentException("Não é possível mover a ultima tarefa para baixo.");
            }

            Long ordemDebaixo = ordem + 1;
            Tarefa tarefaDebaixo = this.repository.findByOrdem(ordemDebaixo);

            if (tarefaDebaixo == null) {
                throw new IllegalArgumentException("Tarefa com a ordem " + ordemDebaixo + " nao encontrada");
            }

            System.out.println("A tarefa abaixo era: " + tarefaDebaixo);
            tarefa.setOrdem(null);
            this.repository.save(tarefa);

            tarefaDebaixo.setOrdem(ordem);
            this.repository.save(tarefaDebaixo);

            tarefa.setOrdem(ordemDebaixo);
            this.repository.save(tarefa);
        }
    }

    private void atualizarOrdens(List<Tarefa> tarefas) {
        for (int i = 0; i < tarefas.size(); i++) {
            Tarefa tarefa = tarefas.get(i);
            tarefa.setOrdem((long) (i + 1));
            this.repository.save(tarefa);
        }
    }
*/
}
