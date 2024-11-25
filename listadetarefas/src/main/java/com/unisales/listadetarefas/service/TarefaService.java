package com.unisales.listadetarefas.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.unisales.listadetarefas.model.Tarefa;
import com.unisales.listadetarefas.model.TarefaDTO;
import com.unisales.listadetarefas.persistence.TarefaRepository;

import io.awspring.cloud.sqs.operations.SqsTemplate;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class TarefaService {
    @Autowired
    private TarefaRepository repository;
    @Value("${amazon.aws.bucket.name}")
    private String SQS;
    @Autowired
    private SqsTemplate sqsTemplate;

    @Value("${cloud.aws.region}")
    private String region;
    //private final S3Client s3Client = S3Client.builder().build();
    private final String bucketName = "sergio29";

    @Value("${amazon.aws.access-key}")
    private String awsacess;

    @Value("${amazon.aws.secret-key}")
    private String awssecretkey;


    /** 
     * Inclui uma nova tarefa no sistema. 
     * <p> 
     * Este método verifica se a descrição da tarefa não é nula ou vazia e se já existe uma tarefa 
     * com a mesma descrição no banco de dados. Se a descrição for válida e não houver duplicatas, 
     * a tarefa é salva no repositório e uma mensagem de confirmação é enviada. 
     * </p> 
     *
     * @param tarefaDTO Objeto DTO contendo os dados da tarefa a ser incluída. 
     * A descrição da tarefa não pode ser nula ou vazia. 
     * @return ResponseEntity<String> contendo uma mensagem de confirmação ou erro. 
     * <ul> 
     *      <li>200 OK: Se a tarefa foi incluída com sucesso.</li> 
     *      <li>400 Bad Request: Se a descrição da tarefa for nula, vazia, ou já existir uma tarefa com a mesma descrição.</li> 
     * </ul> 
    */
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
        
        sqsTemplate.send(SQS, payload);

        return ResponseEntity.ok().body("Tarefa inclusa com sucesso");
    }

    /**
     * Exclui uma tarefa do sistema pelo seu ID.
     * <p>
     * Este método verifica se a tarefa com o ID fornecido existe no banco de dados.
     * Se a tarefa existir, ela é removida do banco de dados.
     * Se a tarefa não existir, é retornada uma mensagem de erro.
     * </p>
     *
     * @param id O ID da tarefa a ser excluída.
     * @return ResponseEntity<String> contendo uma mensagem de confirmação ou erro.
     *         <ul>
     *         <li>200 OK: Se a tarefa foi excluída com sucesso.</li>
     *         <li>400 Bad Request: Se a tarefa com o ID fornecido não for
     *         encontrada.</li>
     *         </ul>
     */
    public ResponseEntity<String> Excluir(@PathVariable Long id) {
        Optional<Tarefa> byId = this.repository.findById(id);

        if (byId.isPresent()) {
            this.repository.deleteById(id);
            return ResponseEntity.ok().body("Tarefa excluida !");
        }

        return ResponseEntity.badRequest().body("Tarefa não encontrada !");
    }

    /**
     * Edita uma tarefa existente no sistema.
     * <p>
     * Este método busca a tarefa pelo seu ID e atualiza sua descrição. Se a tarefa
     * não
     * for encontrada ou se já existir uma tarefa com a mesma descrição, uma
     * mensagem
     * de erro é retornada. Caso contrário, a descrição da tarefa é atualizada e
     * salva
     * no banco de dados.
     * </p>
     *
     * @param id        O ID da tarefa a ser editada.
     * @param tarefaDTO Objeto DTO contendo os dados atualizados da tarefa.
     * @return ResponseEntity<String> contendo uma mensagem de confirmação ou erro.
     *         <ul>
     *         <li>200 OK: Se a tarefa foi editada com sucesso.</li>
     *         <li>400 Bad Request: Se a tarefa com o ID fornecido não for
     *         encontrada ou se já existir uma tarefa com a mesma descrição.</li>
     *         </ul>
     */
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

        sqsTemplate.send(SQS, tarefa);

        return ResponseEntity.ok().body("editado com sucesso !");
    }

    public Iterable<Tarefa> ListaTarefas() {
        return this.repository.findAll();
    }


    public String uploadFile(@org.springframework.web.bind.annotation.RequestBody @RequestPart("file") MultipartFile file) throws IOException {
            S3Client s3Client = S3Client.builder()
                    .region(Region.of(region))
                    .credentialsProvider(
                            StaticCredentialsProvider.create(
                                    AwsBasicCredentials.create(awsacess, awssecretkey)
                            )
                    )
                    .build();
            
            String fileName = file.getOriginalFilename();
    
            Path tempFile = Files.createTempFile("temp", fileName);
            file.transferTo(tempFile.toFile());
    
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build();
    
            s3Client.putObject(putObjectRequest, tempFile);
    
            Files.delete(tempFile);
    
            return fileName;
    }

}
