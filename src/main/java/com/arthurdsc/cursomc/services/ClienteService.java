package com.arthurdsc.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arthurdsc.cursomc.domain.Cliente;
import com.arthurdsc.cursomc.repositories.ClienteRepository;
import com.arthurdsc.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public Cliente findClienteById (Long id) {
		Cliente obj = clienteRepository.findClienteById(id);
		if (obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName());
		}
		return obj;
	}

}
