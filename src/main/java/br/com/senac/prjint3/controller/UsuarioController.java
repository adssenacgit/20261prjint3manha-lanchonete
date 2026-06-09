package br.com.senac.prjint3.controller;

import br.com.senac.prjint3.dto.UsuarioRequest;
import br.com.senac.prjint3.dto.UsuarioResponse;
import br.com.senac.prjint3.service.UsuarioService;
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
@RequestMapping("/api/usuarios")
@Tag(name = "Usuários", description = "CRUD de usuários com exclusão lógica")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Lista usuários não apagados")
    public List<UsuarioResponse> listar() {
        return service.listar();
    }

    @GetMapping("/ativos")
    @Operation(summary = "Lista usuários ativos")
    public List<UsuarioResponse> listarAtivos() {
        return service.listarAtivos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca usuário por id")
    public UsuarioResponse buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    @Operation(summary = "Cria usuário")
    public ResponseEntity<UsuarioResponse> criar(@Valid @RequestBody UsuarioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza usuário")
    public UsuarioResponse atualizar(@PathVariable Integer id, @Valid @RequestBody UsuarioRequest request) {
        return service.atualizar(id, request);
    }

    @PatchMapping("/{id}/status/{status}")
    @Operation(summary = "Altera status do usuário")
    public UsuarioResponse alterarStatus(@PathVariable Integer id, @PathVariable Integer status) {
        return service.alterarStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Apaga usuário logicamente, marcando status -1")
    public ResponseEntity<Void> excluirLogicamente(@PathVariable Integer id) {
        service.excluirLogicamente(id);
        return ResponseEntity.noContent().build();
    }
}
