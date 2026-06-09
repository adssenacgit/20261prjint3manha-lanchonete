package br.com.senac.prjint3.service;

import br.com.senac.prjint3.domain.RegistroStatus;
import br.com.senac.prjint3.dto.CategoriaRequest;
import br.com.senac.prjint3.dto.CategoriaResponse;
import br.com.senac.prjint3.entity.Categoria;
import br.com.senac.prjint3.exception.ResourceNotFoundException;
import br.com.senac.prjint3.repository.CategoriaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository repository;

    public CategoriaService(CategoriaRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<CategoriaResponse> listar() {
        return repository.findByStatusNotOrderByIdAsc(RegistroStatus.EXCLUIDO)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CategoriaResponse> listarAtivos() {
        return repository.findByStatusOrderByIdAsc(RegistroStatus.ATIVO)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public CategoriaResponse buscarPorId(Integer id) {
        return toResponse(buscarEntidadeValida(id));
    }

    @Transactional
    public CategoriaResponse criar(CategoriaRequest request) {
        Categoria categoria = new Categoria();
        categoria.setNome(request.nome());
        categoria.setStatus(RegistroStatus.novoOuPadrao(request.status()));
        return toResponse(repository.save(categoria));
    }

    @Transactional
    public CategoriaResponse atualizar(Integer id, CategoriaRequest request) {
        Categoria categoria = buscarEntidadeValida(id);
        categoria.setNome(request.nome());

        if (request.status() != null) {
            categoria.setStatus(RegistroStatus.validar(request.status()));
        }

        return toResponse(repository.save(categoria));
    }

    @Transactional
    public CategoriaResponse alterarStatus(Integer id, Integer status) {
        Categoria categoria = buscarEntidadeValida(id);
        categoria.setStatus(RegistroStatus.validar(status));
        return toResponse(repository.save(categoria));
    }

    @Transactional
    public void excluirLogicamente(Integer id) {
        Categoria categoria = buscarEntidadeValida(id);
        categoria.setStatus(RegistroStatus.EXCLUIDO);
        repository.save(categoria);
    }

    private Categoria buscarEntidadeValida(Integer id) {
        return repository.findByIdAndStatusNot(id, RegistroStatus.EXCLUIDO)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada ou apagada."));
    }

    private CategoriaResponse toResponse(Categoria categoria) {
        return new CategoriaResponse(categoria.getId(), categoria.getNome(), categoria.getStatus());
    }
}
