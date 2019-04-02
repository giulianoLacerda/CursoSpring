package com.giuliano.curso.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.giuliano.curso.domain.ItemPedido;
import com.giuliano.curso.domain.PagamentoComBoleto;
import com.giuliano.curso.domain.Pedido;
import com.giuliano.curso.domain.enums.EstadoPagamento;
import com.giuliano.curso.repositories.ItemPedidoRepository;
import com.giuliano.curso.repositories.PagamentoRepository;
import com.giuliano.curso.repositories.PedidoRepository;
import com.giuliano.curso.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired // Instanciada automaticamente pelo spring
	private PedidoRepository repo;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	@Autowired
	private ClienteService clienteService;
	
	/**
	 * Busca objeto pelo identificador.
	 * @param id Identificador.
	 * @return Categoria.
	 */
	public Pedido buscar(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: "+id+", Tipo: "+Pedido.class.getName()));
	}
	
	
	@Transactional
	public Pedido insert(Pedido obj) {
		// Seta os atributos do Pedido
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(clienteService.buscar(obj.getCliente().getId()));
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if(obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto =  (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		for (ItemPedido ip: obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setProduto(produtoService.find(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItens());
		System.out.println(obj);
		return obj;
	}

}
