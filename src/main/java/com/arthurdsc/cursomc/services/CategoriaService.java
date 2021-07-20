package com.arthurdsc.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arthurdsc.cursomc.domain.Categoria;
import com.arthurdsc.cursomc.repositories.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Categoria findCategoriaById (Long id) {
		return categoriaRepository.findCategoriaById(id);
	}

}
