package com.carlos.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.carlos.helpdesk.model.Tecnico;

public interface TecnicoRepository  extends JpaRepository<Tecnico, Integer>{

}
