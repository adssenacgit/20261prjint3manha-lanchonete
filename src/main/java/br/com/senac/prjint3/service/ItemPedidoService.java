package br.com.senac.prjint3.service;

import br.com.senac.prjint3.domain.RegistroStatus;
import br.com.senac.prjint3.dto.ItemPedidoRequest;
import br.com.senac.prjint3.dto.ItemPedidoResponse;
import br.com.senac.prjint3.entity.ItemPedido;
import br.com.senac.prjint3.entity.Pedido;
import br.com.senac.prjint3.entity.Produto;
import br.com.senac.prjint3.exception.ResourceNotFoundException;
import br.com.senac.prjint3.repository.ItemPedidoRepository;
import br.com.senac.prjint3.repository.PedidoRepository;
import br.com.senac.prjint3.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ItemPedidoService {

    private final ItemPedidoRepository repository;
    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;

    public ItemPedidoService(ItemPedidoRepository repository, PedidoRepository pedidoRepository, ProdutoRepository produtoRepository) {
        this.repository = repository;
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
    }

    @Transactional(readOnly = true)
    public List<ItemPedidoResponse> listar() {
        return repository.findByStatusNotOrderByIdAsc(RegistroStatus.EXCLUIDO)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ItemPedidoResponse> listarAtivos() {
        return repository.findByStatusOrderByIdAsc(RegistroStatus.ATIVO)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ItemPedidoResponse buscarPorId(Integer id) {
        return toResponse(buscarEntidadeValida(id));
    }

    @Transactional
    public ItemPedidoResponse criar(ItemPedidoRequest request) {
        ItemPedido item = new ItemPedido();
        preencher(item, request, true);
        return toResponse(repository.save(item));
    }

    @Transactional
    public ItemPedidoResponse atualizar(Integer id, ItemPedidoRequest request) {
        ItemPedido item = buscarEntidadeValida(id);
        preencher(item, request, false);
        return toResponse(repository.save(item));
    }

    @Transactional
    public ItemPedidoResponse alterarStatus(Integer id, Integer status) {
        ItemPedido item = buscarEntidadeValida(id);
        item.setStatus(RegistroStatus.validar(status));
        return toResponse(repository.save(item));
    }

    @Transactional
    public void excluirLogicamente(Integer id) {
        ItemPedido item = buscarEntidadeValida(id);
        item.setStatus(RegistroStatus.EXCLUIDO);
        repository.save(item);
    }

    private void preencher(ItemPedido item, ItemPedidoRequest request, boolean novo) {
        Pedido pedido = pedidoRepository.findByIdAndStatusNot(request.pedidoId(), RegistroStatus.EXCLUIDO)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado ou apagado."));

        Produto produto = produtoRepository.findByIdAndStatusNot(request.produtoId(), RegistroStatus.EXCLUIDO)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado ou apagado."));

        item.setQuantidade(request.quantidade());
        item.setPrecoUnitario(request.precoUnitario());
        item.setSubtotal(request.subtotal());
        item.setPedido(pedido);
        item.setProduto(produto);

        if (novo) {
            item.setStatus(RegistroStatus.novoOuPadrao(request.status()));
        } else if (request.status() != null) {
            item.setStatus(RegistroStatus.validar(request.status()));
        }
    }

    private ItemPedido buscarEntidadeValida(Integer id) {
        return repository.findByIdAndStatusNot(id, RegistroStatus.EXCLUIDO)
                .orElseThrow(() -> new ResourceNotFoundException("Item do pedido não encontrado ou apagado."));
    }

    private ItemPedidoResponse toResponse(ItemPedido item) {
        Pedido pedido = item.getPedido();
        Produto produto = item.getProduto();
        return new ItemPedidoResponse(
                item.getId(),
                item.getQuantidade(),
                item.getPrecoUnitario(),
                item.getSubtotal(),
                item.getStatus(),
                pedido.getId(),
                pedido.getNumero(),
                produto.getId(),
                produto.getNome()
        );
    }
}
