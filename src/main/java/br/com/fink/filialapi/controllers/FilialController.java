package br.com.fink.filialapi.controllers;

import java.net.URI;
import javax.validation.Valid;

import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.fink.filialapi.payload.FilialDTO;
import br.com.fink.filialapi.services.FilialService;
import io.micrometer.core.instrument.MeterRegistry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Filial", description = "Filial API")
@RestController
@RequestMapping("/filiais")
public class FilialController {

    @Autowired
    private FilialService filialService;
    
    @Autowired
    private MeterRegistry registry;

    @Operation(summary = "Lista filiais", description = "Retorna uma lista de filiais cadastradas")
    @GetMapping
    public ResponseEntity<Page<FilialDTO>> listAll(@ParameterObject Pageable pageable) {        
        return ResponseEntity.ok().body(filialService.listFiliais(pageable));
    }

    @Operation(summary = "Obtem filial por ID", description = "Retorna uma única filial")
    @GetMapping("/{id}")
    public ResponseEntity<FilialDTO> findById(
            @Parameter(description = "ID de filial", required = true) @PathVariable Integer id) {
        return ResponseEntity.ok().body(filialService.findById(id));
    }

    @Operation(summary = "Insere uma nova filial", description = "Retorna a nova filial")
    @PostMapping
    public ResponseEntity<FilialDTO> create(@Valid @RequestBody FilialDTO filialDTO) {
        filialDTO = filialService.insert(filialDTO);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(filialDTO.getId()).toUri();
        registry.counter("contador.filiais.inseridas").increment();
        return ResponseEntity.created(uri).body(filialDTO);
    }

    @Operation(summary = "Altera uma filial", description = "Retorna filial atualizada")
    @PutMapping
    public ResponseEntity<FilialDTO> update(@Valid @RequestBody FilialDTO filialDTO) {
        return ResponseEntity.ok().body(filialService.update(filialDTO));
    }

    @Operation(summary = "Exclui uma filial", description = "Sem retorno")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
        @Parameter(description = "ID de filial", required = true) @PathVariable Integer id) {
        filialService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Ativa/Desativa uma filial", description = "Retorna filial atualizada")
    @PutMapping("/{id}")
    public ResponseEntity<FilialDTO> activateDeactivate(
            @Parameter(description = "ID de filial", required = true) @PathVariable Integer id) {
        return ResponseEntity.ok().body(filialService.activateDeactivate(id));
    }
}
