package com.archivo.plano.general.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="ARCHIVO")
public class ArchivoEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IDARCHIVO")
	private Long idArchivo;
	
	@Column(name="NOMBREARCHIVO", nullable = false, length = 250)
	private String nombreArchivo;
	
	@Column(name="NOMBREARCHIVOUUID", nullable = false, length = 250, unique = true)
	private String nombreArchivoUUID;
	
	@JoinColumn(name="IDCLIENTE", nullable=false)
	@ManyToOne(optional = false,  fetch = FetchType.EAGER)
	private ClienteEntity cliente;

}
