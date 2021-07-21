package com.arthurdsc.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arthurdsc.cursomc.domain.Pedido;
import com.arthurdsc.cursomc.repositories.PedidoRepository;
import com.arthurdsc.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	public Pedido findPedidoById (Long id) {
		Pedido obj = pedidoRepository.findPedidoById(id);
		if (obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName());
		}
		return obj;
	}

}
