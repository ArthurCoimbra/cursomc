package com.arthurdsc.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arthurdsc.cursomc.domain.Categoria;
import com.arthurdsc.cursomc.repositories.CategoriaRepository;
import com.arthurdsc.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Categoria findCategoriaById (Long id) {
		Categoria obj = categoriaRepository.findCategoriaById(id);
		if (obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName());
		}
		return obj;
	}

	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return categoriaRepository.save(obj);
	}

}
