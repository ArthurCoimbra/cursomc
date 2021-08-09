package com.arthurdsc.cursomc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arthurdsc.cursomc.domain.ItemPedido;
import com.arthurdsc.cursomc.domain.PagamentoComBoleto;
import com.arthurdsc.cursomc.domain.Pedido;
import com.arthurdsc.cursomc.domain.enums.EstadoPagamento;
import com.arthurdsc.cursomc.repositories.ItemPedidoRepository;
import com.arthurdsc.cursomc.repositories.PagamentoRepository;
import com.arthurdsc.cursomc.repositories.PedidoRepository;
import com.arthurdsc.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	public Pedido findPedidoById (Long id) {
		Pedido obj = pedidoRepository.findPedidoById(id);
		if (obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName());
		}
		return obj;
	}
	
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if(obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		obj = pedidoRepository.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		for(ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setPreco(produtoService.findProdutoById(ip.getProduto().getId()).getPreco());
			ip.setPedido(obj);			
		}
		itemPedidoRepository.saveAll(obj.getItens());
		return obj;		
	}

}
