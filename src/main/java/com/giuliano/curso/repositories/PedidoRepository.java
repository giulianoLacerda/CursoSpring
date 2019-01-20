package com.giuliano.curso.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.giuliano.curso.domain.Pedido;

// Operações de acesso a dados referente aos pedidos.
public interface PedidoRepository extends JpaRepository<Pedido, Integer>{

}
