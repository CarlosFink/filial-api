package br.com.fink.filialapi.controllers;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.fink.filialapi.models.Filial;
import br.com.fink.filialapi.services.FilialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Filial", description = "Filial API")
@RestController
@RequestMapping("/api/filial")
public class FilialController {

    private static final Logger log = LoggerFactory.getLogger(FilialController.class);

    @Autowired
    private FilialService filialService;

    @Operation(summary = "Lista filiais", description = "Retorna uma lista de filiais cadastradas")
    @GetMapping
    public ResponseEntity<List<Filial>> listAll() {
        log.info("Obtendo lista de Filiais");
        return ResponseEntity.ok().body(filialService.ListAll());
    }

    @Operation(summary = "Obtem filial por ID", description = "Retorna uma única filial")
    @GetMapping("/{id}")
    public ResponseEntity<Filial> findById(
            @Parameter(description = "ID de filial", required = true) @PathVariable Integer id) {
        log.info("Obtendo Filial pelo ID");
        return ResponseEntity.ok().body(filialService.findById(id));
    }

    @Operation(summary = "Insere uma nova filial", description = "Retorna a nova filial")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Filial> create(@Valid @RequestBody Filial filial) {
        log.info("Incluindo nova Filial");
        filial = filialService.save(filial);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(filial.getId()).toUri();
        return ResponseEntity.created(uri).body(filial);
    }

    @Operation(summary = "Altera uma filial", description = "Retorna filial atualizada")
    @PutMapping
    public ResponseEntity<Filial> update(@Valid @RequestBody Filial filial) {
        log.info("Alterando dados da Filial");
        return ResponseEntity.ok().body(filialService.save(filial));
    }

    @Operation(summary = "Exclui uma filial", description = "Sem retorno")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
        @Parameter(description = "ID de filial", required = true) @PathVariable Integer id) {
        log.info("Excluindo a Filial por ID");
        filialService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Ativa/Desativa uma filial", description = "Retorna filial atualizada")
    @PutMapping("/{id}")
    public ResponseEntity<Filial> activateDeactivate(
            @Parameter(description = "ID de filial", required = true) @PathVariable Integer id) {
        log.info("Ativando ou desativando a Filial");
        return ResponseEntity.ok().body(filialService.activateDeactivate(id));
    }
}
