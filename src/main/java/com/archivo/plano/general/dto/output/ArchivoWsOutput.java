package com.archivo.plano.general.dto.output;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArchivoWsOutput implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4068003468159687901L;
	private String nombreArchivo;
	private String rutaCargue;
	private boolean estadoCargue;
}
