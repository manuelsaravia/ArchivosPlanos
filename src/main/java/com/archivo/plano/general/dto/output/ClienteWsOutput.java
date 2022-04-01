package com.archivo.plano.general.dto.output;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteWsOutput implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = -3825707928668644606L;
	private long idcliente;
	private String nombreCliente;

}
