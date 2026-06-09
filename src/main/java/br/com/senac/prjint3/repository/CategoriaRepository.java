package br.com.senac.prjint3.repository;

import br.com.senac.prjint3.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    List<Categoria> findByStatusNotOrderByIdAsc(Integer status);

    List<Categoria> findByStatusOrderByIdAsc(Integer status);

    Optional<Categoria> findByIdAndStatusNot(Integer id, Integer status);
}
