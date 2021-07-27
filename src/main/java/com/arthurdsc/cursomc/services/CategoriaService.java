package com.arthurdsc.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.arthurdsc.cursomc.domain.Categoria;
import com.arthurdsc.cursomc.repositories.CategoriaRepository;
import com.arthurdsc.cursomc.services.exceptions.DataIntegrityException;
import com.arthurdsc.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public List<Categoria> findAll() {
		return categoriaRepository.findAll();
	}
	
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
	
	public Categoria update(Categoria obj) {
		findCategoriaById(obj.getId());
		return categoriaRepository.save(obj);
	}
	
	public void delete(Long id) {
		findCategoriaById(id);
		try {
			categoriaRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma Categoria que possui Produtos");
		}
		
	}

}
