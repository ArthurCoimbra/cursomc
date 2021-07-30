package com.arthurdsc.cursomc.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.arthurdsc.cursomc.domain.Cidade;
import com.arthurdsc.cursomc.domain.Cliente;
import com.arthurdsc.cursomc.domain.Endereco;
import com.arthurdsc.cursomc.domain.enums.TipoCliente;
import com.arthurdsc.cursomc.dto.ClienteDTO;
import com.arthurdsc.cursomc.dto.ClienteNewDTO;
import com.arthurdsc.cursomc.repositories.ClienteRepository;
import com.arthurdsc.cursomc.repositories.EnderecoRepository;
import com.arthurdsc.cursomc.services.exceptions.DataIntegrityException;
import com.arthurdsc.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}	
	
	public Cliente findClienteById (Long id) {
		Cliente obj = clienteRepository.findClienteById(id);
		if (obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName());
		}
		return obj;
	}
	
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = clienteRepository.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}
	
	public Cliente update(Cliente obj) {
		Cliente newObj = findClienteById(obj.getId());
		updateData(newObj, obj);
		return clienteRepository.save(newObj);
	}
	
	public void delete(Long id) {
		findClienteById(id);
		try {
			clienteRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há entidades relacionadas");
		}
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return clienteRepository.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
	}
	
	public Cliente fromDTO(ClienteNewDTO objDto) {
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()));
		Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cid, cli);
		cli.getEnderecos().addAll(Arrays.asList(end));
		cli.getTelefones().addAll(Arrays.asList(objDto.getTelefone1()));
		if (objDto.getTelefone2() != null) {
			cli.getTelefones().addAll(Arrays.asList(objDto.getTelefone2()));
		}
		if (objDto.getTelefone3() != null) {
			cli.getTelefones().addAll(Arrays.asList(objDto.getTelefone3()));
		}
		return cli;
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}

}
