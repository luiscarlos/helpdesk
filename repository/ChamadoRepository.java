package com.carlos.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carlos.helpdesk.model.Chamado;


public interface ChamadoRepository extends JpaRepository<Chamado, Integer>{
	
}



