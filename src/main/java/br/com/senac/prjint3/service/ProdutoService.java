package br.com.senac.prjint3.service;

import br.com.senac.prjint3.domain.RegistroStatus;
import br.com.senac.prjint3.dto.ProdutoRequest;
import br.com.senac.prjint3.dto.ProdutoResponse;
import br.com.senac.prjint3.entity.Categoria;
import br.com.senac.prjint3.entity.Produto;
import br.com.senac.prjint3.exception.ResourceNotFoundException;
import br.com.senac.prjint3.repository.CategoriaRepository;
import br.com.senac.prjint3.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository repository;
    private final CategoriaRepository categoriaRepository;

    public ProdutoService(ProdutoRepository repository, CategoriaRepository categoriaRepository) {
        this.repository = repository;
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional(readOnly = true)
    public List<ProdutoResponse> listar() {
        return repository.findByStatusNotOrderByIdAsc(RegistroStatus.EXCLUIDO)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ProdutoResponse> listarAtivos() {
        return repository.findByStatusOrderByIdAsc(RegistroStatus.ATIVO)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProdutoResponse buscarPorId(Integer id) {
        return toResponse(buscarEntidadeValida(id));
    }

    @Transactional
    public ProdutoResponse criar(ProdutoRequest request) {
        Produto produto = new Produto();
        preencher(produto, request, true);
        return toResponse(repository.save(produto));
    }

    @Transactional
    public ProdutoResponse atualizar(Integer id, ProdutoRequest request) {
        Produto produto = buscarEntidadeValida(id);
        preencher(produto, request, false);
        return toResponse(repository.save(produto));
    }

    @Transactional
    public ProdutoResponse alterarStatus(Integer id, Integer status) {
        Produto produto = buscarEntidadeValida(id);
        produto.setStatus(RegistroStatus.validar(status));
        return toResponse(repository.save(produto));
    }

    @Transactional
    public void excluirLogicamente(Integer id) {
        Produto produto = buscarEntidadeValida(id);
        produto.setStatus(RegistroStatus.EXCLUIDO);
        repository.save(produto);
    }

    private void preencher(Produto produto, ProdutoRequest request, boolean novo) {
        Categoria categoria = categoriaRepository.findByIdAndStatusNot(request.categoriaId(), RegistroStatus.EXCLUIDO)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada ou apagada."));

        produto.setNome(request.nome());
        produto.setDescricao(request.descricao());
        produto.setPreco(request.preco());
        produto.setImagemUrl(request.imagemUrl());
        produto.setCategoria(categoria);

        if (novo) {
            produto.setStatus(RegistroStatus.novoOuPadrao(request.status()));
        } else if (request.status() != null) {
            produto.setStatus(RegistroStatus.validar(request.status()));
        }
    }

    private Produto buscarEntidadeValida(Integer id) {
        return repository.findByIdAndStatusNot(id, RegistroStatus.EXCLUIDO)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado ou apagado."));
    }

    private ProdutoResponse toResponse(Produto produto) {
        Categoria categoria = produto.getCategoria();
        return new ProdutoResponse(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getImagemUrl(),
                produto.getStatus(),
                categoria.getId(),
                categoria.getNome()
        );
    }
}
