package br.com.senac.prjint3.controller;

import br.com.senac.prjint3.dto.ItemPedidoRequest;
import br.com.senac.prjint3.dto.ItemPedidoResponse;
import br.com.senac.prjint3.service.ItemPedidoService;
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
@RequestMapping("/api/itens-pedidos")
@Tag(name = "Itens de pedidos", description = "CRUD de itens de pedidos com exclusão lógica")
public class ItemPedidoController {

    private final ItemPedidoService service;

    public ItemPedidoController(ItemPedidoService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Lista itens de pedidos não apagados")
    public List<ItemPedidoResponse> listar() {
        return service.listar();
    }

    @GetMapping("/ativos")
    @Operation(summary = "Lista itens de pedidos ativos")
    public List<ItemPedidoResponse> listarAtivos() {
        return service.listarAtivos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca item de pedido por id")
    public ItemPedidoResponse buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    @Operation(summary = "Cria item de pedido")
    public ResponseEntity<ItemPedidoResponse> criar(@Valid @RequestBody ItemPedidoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza item de pedido")
    public ItemPedidoResponse atualizar(@PathVariable Integer id, @Valid @RequestBody ItemPedidoRequest request) {
        return service.atualizar(id, request);
    }

    @PatchMapping("/{id}/status/{status}")
    @Operation(summary = "Altera status do item de pedido")
    public ItemPedidoResponse alterarStatus(@PathVariable Integer id, @PathVariable Integer status) {
        return service.alterarStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Apaga item de pedido logicamente, marcando status -1")
    public ResponseEntity<Void> excluirLogicamente(@PathVariable Integer id) {
        service.excluirLogicamente(id);
        return ResponseEntity.noContent().build();
    }
}
