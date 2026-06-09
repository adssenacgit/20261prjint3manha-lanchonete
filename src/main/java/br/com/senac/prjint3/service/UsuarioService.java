package br.com.senac.prjint3.service;

import br.com.senac.prjint3.domain.RegistroStatus;
import br.com.senac.prjint3.dto.UsuarioRequest;
import br.com.senac.prjint3.dto.UsuarioResponse;
import br.com.senac.prjint3.entity.Usuario;
import br.com.senac.prjint3.exception.ResourceNotFoundException;
import br.com.senac.prjint3.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponse> listar() {
        return repository.findByStatusNotOrderByIdAsc(RegistroStatus.EXCLUIDO)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponse> listarAtivos() {
        return repository.findByStatusOrderByIdAsc(RegistroStatus.ATIVO)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public UsuarioResponse buscarPorId(Integer id) {
        return toResponse(buscarEntidadeValida(id));
    }

    @Transactional
    public UsuarioResponse criar(UsuarioRequest request) {
        Usuario usuario = new Usuario();
        usuario.setNome(request.nome());
        usuario.setLogin(request.login());
        usuario.setSenha(request.senha());
        usuario.setStatus(RegistroStatus.novoOuPadrao(request.status()));
        return toResponse(repository.save(usuario));
    }

    @Transactional
    public UsuarioResponse atualizar(Integer id, UsuarioRequest request) {
        Usuario usuario = buscarEntidadeValida(id);
        usuario.setNome(request.nome());
        usuario.setLogin(request.login());
        usuario.setSenha(request.senha());

        if (request.status() != null) {
            usuario.setStatus(RegistroStatus.validar(request.status()));
        }

        return toResponse(repository.save(usuario));
    }

    @Transactional
    public UsuarioResponse alterarStatus(Integer id, Integer status) {
        Usuario usuario = buscarEntidadeValida(id);
        usuario.setStatus(RegistroStatus.validar(status));
        return toResponse(repository.save(usuario));
    }

    @Transactional
    public void excluirLogicamente(Integer id) {
        Usuario usuario = buscarEntidadeValida(id);
        usuario.setStatus(RegistroStatus.EXCLUIDO);
        repository.save(usuario);
    }

    private Usuario buscarEntidadeValida(Integer id) {
        return repository.findByIdAndStatusNot(id, RegistroStatus.EXCLUIDO)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado ou apagado."));
    }

    private UsuarioResponse toResponse(Usuario usuario) {
        return new UsuarioResponse(usuario.getId(), usuario.getNome(), usuario.getLogin(), usuario.getStatus());
    }
}
