package br.com.senac.prjint3.controller;

import br.com.senac.prjint3.dto.PedidoRequest;
import br.com.senac.prjint3.dto.PedidoResponse;
import br.com.senac.prjint3.service.PedidoService;
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
@RequestMapping("/api/pedidos")
@Tag(name = "Pedidos", description = "CRUD de pedidos com exclusão lógica")
public class PedidoController {

    private final PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Lista pedidos não apagados")
    public List<PedidoResponse> listar() {
        return service.listar();
    }

    @GetMapping("/ativos")
    @Operation(summary = "Lista pedidos ativos")
    public List<PedidoResponse> listarAtivos() {
        return service.listarAtivos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca pedido por id")
    public PedidoResponse buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    @Operation(summary = "Cria pedido")
    public ResponseEntity<PedidoResponse> criar(@Valid @RequestBody PedidoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza pedido")
    public PedidoResponse atualizar(@PathVariable Integer id, @Valid @RequestBody PedidoRequest request) {
        return service.atualizar(id, request);
    }

    @PatchMapping("/{id}/status/{status}")
    @Operation(summary = "Altera status do pedido")
    public PedidoResponse alterarStatus(@PathVariable Integer id, @PathVariable Integer status) {
        return service.alterarStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Apaga pedido logicamente, marcando status -1")
    public ResponseEntity<Void> excluirLogicamente(@PathVariable Integer id) {
        service.excluirLogicamente(id);
        return ResponseEntity.noContent().build();
    }
}
