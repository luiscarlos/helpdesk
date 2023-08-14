package com.carlos.helpdesk.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carlos.helpdesk.model.Chamado;
import com.carlos.helpdesk.model.Tecnico;
import com.carlos.helpdesk.repository.ChamadoRepository;
import com.carlos.helpdesk.services.exceptions.ObjectnotFoundException;

@Service
public class ChamadoService {

	
	
	
	@Autowired
	private ChamadoRepository chamadoRepository;
	
	
	public Chamado findById(Integer id) {
		Optional<Chamado> obj = chamadoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectnotFoundException("Objeto não encontrado:" + id));
	}


	public List<Chamado> findAll() {
		return chamadoRepository.findAll();
	}
}
