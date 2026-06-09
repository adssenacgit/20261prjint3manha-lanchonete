package br.com.senac.prjint3.controller;

import br.com.senac.prjint3.dto.ProdutoRequest;
import br.com.senac.prjint3.dto.ProdutoResponse;
import br.com.senac.prjint3.service.ProdutoService;
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
@RequestMapping("/api/produtos")
@Tag(name = "Produtos", description = "CRUD de produtos com exclusão lógica")
public class ProdutoController {

    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Lista produtos não apagados")
    public List<ProdutoResponse> listar() {
        return service.listar();
    }

    @GetMapping("/ativos")
    @Operation(summary = "Lista produtos ativos")
    public List<ProdutoResponse> listarAtivos() {
        return service.listarAtivos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca produto por id")
    public ProdutoResponse buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    @Operation(summary = "Cria produto")
    public ResponseEntity<ProdutoResponse> criar(@Valid @RequestBody ProdutoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza produto")
    public ProdutoResponse atualizar(@PathVariable Integer id, @Valid @RequestBody ProdutoRequest request) {
        return service.atualizar(id, request);
    }

    @PatchMapping("/{id}/status/{status}")
    @Operation(summary = "Altera status do produto")
    public ProdutoResponse alterarStatus(@PathVariable Integer id, @PathVariable Integer status) {
        return service.alterarStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Apaga produto logicamente, marcando status -1")
    public ResponseEntity<Void> excluirLogicamente(@PathVariable Integer id) {
        service.excluirLogicamente(id);
        return ResponseEntity.noContent().build();
    }
}
