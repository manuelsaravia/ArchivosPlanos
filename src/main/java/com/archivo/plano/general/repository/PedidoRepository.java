package com.archivo.plano.general.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.archivo.plano.general.entity.PedidoEntity;

public interface PedidoRepository extends JpaRepository<PedidoEntity, Long> {
	
	@Query(value="SELECT p.* FROM PEDIDO p "
			+ "INNER JOIN CLIENTE c ON c.IDCLIENTE=p.IDCLIENTE "
			+ "WHERE c.IDCLIENTE=:idCliente "
			+ "ORDER BY p.FECHAREQUERIDA ASC", nativeQuery = true)
	List<PedidoEntity> obtenerTodosPorCliente(@Param("idCliente") long idCliente);

}
