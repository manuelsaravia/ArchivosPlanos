package com.archivo.plano.general.dto.output;

import java.io.Serializable;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespuestaGeneralWS implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2012214555111598429L;
	private String descripcion;
	private String accionRealizada;
	private HttpStatus estadoPeticion;
	private Object obj;
}
