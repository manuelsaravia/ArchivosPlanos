package com.archivo.plano.general.interfaces;

import org.springframework.http.ResponseEntity;

import com.archivo.plano.general.dto.output.RespuestaGeneralWS;

public interface IPedido {

	public ResponseEntity<RespuestaGeneralWS> obtenerTodos();
}
