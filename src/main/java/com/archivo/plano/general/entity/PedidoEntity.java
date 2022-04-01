package com.archivo.plano.general.entity;

import java.sql.Date;

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
@Table(name="PEDIDO")
public class PedidoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IDPEDIDO")
	private Long idPedido;
	
	@JoinColumn(name="IDCLIENTE", nullable=false)
	@ManyToOne(optional = false,  fetch = FetchType.EAGER)
	private ClienteEntity cliente;
	
	@Column(name="NOMBREPRODUCTO")
	private String nombreProducto;
	
	@Column(name="CANTIDADPRODUCTO")
	private int cantidadProducto;
	
	@Column(name="FECHAREQUERIDA")
	private Date fechaRequerida;
}
