package com.archivo.plano.general.dto.output;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoWsOutput implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 4581320331627565008L;
	
	private Date fechaRequerida;
	private String nombreProducto;
	private int cantidadProducto;

}
