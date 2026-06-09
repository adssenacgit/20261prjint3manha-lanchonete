package br.com.senac.prjint3.controller;

import br.com.senac.prjint3.dto.CategoriaRequest;
import br.com.senac.prjint3.dto.CategoriaResponse;
import br.com.senac.prjint3.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@Tag(name = "Categorias", description = "CRUD de categorias com exclusão lógica")
public class CategoriaController {

    private final CategoriaService service;

    public CategoriaController(CategoriaService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Lista categorias não apagadas")
    public List<CategoriaResponse> listar() {
        return service.listar();
    }

    @GetMapping("/ativos")
    @Operation(summary = "Lista categorias ativas")
    public List<CategoriaResponse> listarAtivos() {
        return service.listarAtivos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca categoria por id")
    public CategoriaResponse buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    @Operation(summary = "Cria categoria")
    public ResponseEntity<CategoriaResponse> criar(@Valid @RequestBody CategoriaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza categoria")
    public CategoriaResponse atualizar(@PathVariable Integer id, @Valid @RequestBody CategoriaRequest request) {
        return service.atualizar(id, request);
    }

    @PatchMapping("/{id}/status/{status}")
    @Operation(summary = "Altera status da categoria")
    public CategoriaResponse alterarStatus(@PathVariable Integer id, @PathVariable Integer status) {
        return service.alterarStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Apaga categoria logicamente, marcando status -1")
    public ResponseEntity<Void> excluirLogicamente(@PathVariable Integer id) {
        service.excluirLogicamente(id);
        return ResponseEntity.noContent().build();
    }
}
