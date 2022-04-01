package com.archivo.plano.general.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="CLIENTE")
public class ClienteEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IDCLIENTE")
	private Long idCliente;
	
	@Column(name="NOMBRECLIENTE", nullable = false, length = 50)
	private String nombreCliente;
	
	@Column(name="PATHCLIENTE", nullable = false, length = 100, unique = true)
	private String path;
	
	@Column(name="DELIMITADORCLIENTE", nullable = false, length = 50)
	private String delimitadorCliente;
	
	@Column(name="CARACTERREEMPLAZO", nullable = true, length = 50)
	private String caracterReemplazo;
	
	@Column(name="ORDENCOLUMNAS", nullable = false, length = 50)
	private String ordenColumnas;
	
	@Column(name="ORDENFECHA", nullable = false, length = 50)
	private String ordenFecha;
	
	@Column(name="DELIMITADORFECHA", nullable = true, length = 50)
	private String delimitadorFecha;
	
}
