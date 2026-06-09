package br.com.senac.prjint3.repository;

import br.com.senac.prjint3.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

    List<Produto> findByStatusNotOrderByIdAsc(Integer status);

    List<Produto> findByStatusOrderByIdAsc(Integer status);

    Optional<Produto> findByIdAndStatusNot(Integer id, Integer status);
}
