package br.com.senac.prjint3.service;

import br.com.senac.prjint3.domain.RegistroStatus;
import br.com.senac.prjint3.dto.PedidoRequest;
import br.com.senac.prjint3.dto.PedidoResponse;
import br.com.senac.prjint3.entity.Pedido;
import br.com.senac.prjint3.exception.ResourceNotFoundException;
import br.com.senac.prjint3.repository.PedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository repository;

    public PedidoService(PedidoRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<PedidoResponse> listar() {
        return repository.findByStatusNotOrderByIdAsc(RegistroStatus.EXCLUIDO)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PedidoResponse> listarAtivos() {
        return repository.findByStatusOrderByIdAsc(RegistroStatus.ATIVO)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public PedidoResponse buscarPorId(Integer id) {
        return toResponse(buscarEntidadeValida(id));
    }

    @Transactional
    public PedidoResponse criar(PedidoRequest request) {
        Pedido pedido = new Pedido();
        preencher(pedido, request, true);
        return toResponse(repository.save(pedido));
    }

    @Transactional
    public PedidoResponse atualizar(Integer id, PedidoRequest request) {
        Pedido pedido = buscarEntidadeValida(id);
        preencher(pedido, request, false);
        return toResponse(repository.save(pedido));
    }

    @Transactional
    public PedidoResponse alterarStatus(Integer id, Integer status) {
        Pedido pedido = buscarEntidadeValida(id);
        pedido.setStatus(RegistroStatus.validar(status));
        return toResponse(repository.save(pedido));
    }

    @Transactional
    public void excluirLogicamente(Integer id) {
        Pedido pedido = buscarEntidadeValida(id);
        pedido.setStatus(RegistroStatus.EXCLUIDO);
        repository.save(pedido);
    }

    private void preencher(Pedido pedido, PedidoRequest request, boolean novo) {
        pedido.setNumero(request.numero());
        pedido.setData(request.data());
        pedido.setTotal(request.total());

        if (novo) {
            pedido.setStatus(RegistroStatus.novoOuPadrao(request.status()));
        } else if (request.status() != null) {
            pedido.setStatus(RegistroStatus.validar(request.status()));
        }
    }

    private Pedido buscarEntidadeValida(Integer id) {
        return repository.findByIdAndStatusNot(id, RegistroStatus.EXCLUIDO)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado ou apagado."));
    }

    private PedidoResponse toResponse(Pedido pedido) {
        return new PedidoResponse(pedido.getId(), pedido.getNumero(), pedido.getData(), pedido.getTotal(), pedido.getStatus());
    }
}
