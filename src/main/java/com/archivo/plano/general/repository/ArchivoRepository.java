package com.archivo.plano.general.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.archivo.plano.general.entity.ArchivoEntity;

@Repository
public interface ArchivoRepository extends JpaRepository<ArchivoEntity, Long> {

}
