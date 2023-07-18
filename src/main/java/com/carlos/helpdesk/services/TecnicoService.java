package com.carlos.helpdesk.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.carlos.helpdesk.dtos.TecnicoDTO;
import com.carlos.helpdesk.model.Pessoa;
import com.carlos.helpdesk.model.Tecnico;
import com.carlos.helpdesk.repository.PessoaRepository;
import com.carlos.helpdesk.repository.TecnicoRepository;
import com.carlos.helpdesk.services.exceptions.ObjectnotFoundException;

@Service
public class TecnicoService {
	
	@Autowired
	private TecnicoRepository tecnicoRepository;
	
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	
	
	public Tecnico findById(Integer id) {
		Optional<Tecnico> obj = tecnicoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectnotFoundException("Objeto não encontrado:" + id));
	}



	public List<Tecnico> findAll() {
		ArrayList<Tecnico> tecnicos = (ArrayList<Tecnico>) tecnicoRepository.findAll();
		//return tecnicos;
		return tecnicoRepository.findAll();
	}



	public Tecnico create(TecnicoDTO objDTO) {
		objDTO.setId(null);
		//objDTO.setSenha(encoder.encode(objDTO.getSenha()));
		validaPorCpfEEmail(objDTO);
		Tecnico newObj = new Tecnico(objDTO);
		return tecnicoRepository.save(newObj);
	}

	private void validaPorCpfEEmail(TecnicoDTO objDTO) {
		Optional<Pessoa> obj = pessoaRepository.findByCpf(objDTO.getCpf());
		if (obj.isPresent() && obj.get().getId() != objDTO.getId()) {
			throw new DataIntegrityViolationException("CPF já cadastrado no sistema!");
		}

		obj = pessoaRepository.findByEmail(objDTO.getEmail());
		if (obj.isPresent() && obj.get().getId() != objDTO.getId()) {
			throw new DataIntegrityViolationException("E-mail já cadastrado no sistema!");
		}
	}
	


}
