package com.giuliano.curso.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.giuliano.curso.domain.Estado;

public interface EstadoRepository extends JpaRepository<Estado, Integer>{

}
