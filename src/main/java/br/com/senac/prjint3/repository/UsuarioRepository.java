package br.com.senac.prjint3.repository;

import br.com.senac.prjint3.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    List<Usuario> findByStatusNotOrderByIdAsc(Integer status);

    List<Usuario> findByStatusOrderByIdAsc(Integer status);

    Optional<Usuario> findByIdAndStatusNot(Integer id, Integer status);
}
