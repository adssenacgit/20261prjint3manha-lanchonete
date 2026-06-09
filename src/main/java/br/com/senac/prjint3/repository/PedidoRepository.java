package br.com.senac.prjint3.repository;

import br.com.senac.prjint3.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    List<Pedido> findByStatusNotOrderByIdAsc(Integer status);

    List<Pedido> findByStatusOrderByIdAsc(Integer status);

    Optional<Pedido> findByIdAndStatusNot(Integer id, Integer status);
}
