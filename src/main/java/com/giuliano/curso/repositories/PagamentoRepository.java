package com.giuliano.curso.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.giuliano.curso.domain.Pagamento;

// Operações de acesso a dados referente aos pagamentos
public interface PagamentoRepository extends JpaRepository<Pagamento, Integer>{

}
