package br.com.senac.prjint3.repository;

import br.com.senac.prjint3.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Integer> {

    List<ItemPedido> findByStatusNotOrderByIdAsc(Integer status);

    List<ItemPedido> findByStatusOrderByIdAsc(Integer status);

    Optional<ItemPedido> findByIdAndStatusNot(Integer id, Integer status);
}
