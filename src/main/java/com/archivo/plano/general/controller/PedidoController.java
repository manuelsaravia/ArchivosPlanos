package com.archivo.plano.general.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.archivo.plano.general.constantes.ApiConstant;
import com.archivo.plano.general.dto.output.RespuestaGeneralWS;
import com.archivo.plano.general.interfaces.IPedido;

@RestController
@RequestMapping(ApiConstant.PEDIDO_CONTROLLER)
public class PedidoController {
	
	@Autowired
	private IPedido pedido;
	
	@GetMapping(ApiConstant.PEDIDO_CONTROLLER_OBTENER_TODOS)
	public ResponseEntity<RespuestaGeneralWS> obtenerTodos(){
		return this.pedido.obtenerTodos();
	}

}
