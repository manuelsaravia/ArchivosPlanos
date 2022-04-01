package com.archivo.plano.general.dto.output;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidosWsOutput implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3630728683987813618L;
	private ClienteWsOutput cliente;
	private List<PedidoWsOutput> pedidos;

}
