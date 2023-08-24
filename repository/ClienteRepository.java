package com.carlos.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carlos.helpdesk.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

}
