package com.carlos.helpdesk.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.carlos.helpdesk.dtos.ClienteDTO;
import com.carlos.helpdesk.model.Cliente;
import com.carlos.helpdesk.model.Pessoa;
import com.carlos.helpdesk.repository.ClienteRepository;
import com.carlos.helpdesk.repository.PessoaRepository;
import com.carlos.helpdesk.services.exceptions.ObjectnotFoundException;



@Service
public class ClienteService {

	@Autowired
	private ClienteRepository tecnicoRepository;

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	  private BCryptPasswordEncoder encoder;

	public Cliente findById(Integer id) {
		Optional<Cliente> obj = tecnicoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectnotFoundException("Objeto não encontrado:" + id));
	}

	public List<Cliente> findAll() {
		ArrayList<Cliente> tecnicos = (ArrayList<Cliente>) tecnicoRepository.findAll();
		// return tecnicos;
		return tecnicoRepository.findAll();
	}

	public Cliente create(ClienteDTO objDTO) {
		objDTO.setId(null);
		objDTO.setSenha(encoder.encode(objDTO.getSenha()));
		validaPorCpfEEmail(objDTO);
		Cliente newObj = new Cliente(objDTO);
		return tecnicoRepository.save(newObj);
	}

	public Cliente update(Integer id, @Valid ClienteDTO objDTO) {
		objDTO.setId(id);
		Cliente oldObj = findById(id);

		if(!objDTO.getSenha().equals(oldObj.getSenha()))
		objDTO.setSenha(encoder.encode(objDTO.getSenha()));

		validaPorCpfEEmail(objDTO);
		oldObj = new Cliente(objDTO);
		return tecnicoRepository.save(oldObj);
	}

	public void delete(Integer id) {
		Cliente obj = findById(id);
		
		if (obj.getChamados().size() > 0) {
			throw new DataIntegrityViolationException("Cliente possui ordens de serviço e não pode ser deletado!");
		}
		
		tecnicoRepository.deleteById(id);
	}

	private void validaPorCpfEEmail(ClienteDTO objDTO) {
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
