package com.arthurdsc.cursomc.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.arthurdsc.cursomc.domain.Cliente;
import com.arthurdsc.cursomc.domain.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>{
	
	@Transactional(readOnly = true)
	Pedido findPedidoById(Long id);
	
	@Transactional
	Page<Pedido> findByCliente(Cliente cliente, Pageable pageRequest);
	
}
