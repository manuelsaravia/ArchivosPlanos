package com.archivo.plano.general.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.archivo.plano.general.entity.ClienteEntity;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {
	
	Optional<ClienteEntity> findByNombreCliente(String nombre);

}
