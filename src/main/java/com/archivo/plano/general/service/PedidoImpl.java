package com.archivo.plano.general.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.archivo.plano.general.constantes.ApiAccionesEnum;
import com.archivo.plano.general.dto.output.ClienteWsOutput;
import com.archivo.plano.general.dto.output.PedidoWsOutput;
import com.archivo.plano.general.dto.output.PedidosWsOutput;
import com.archivo.plano.general.dto.output.RespuestaGeneralWS;
import com.archivo.plano.general.entity.ClienteEntity;
import com.archivo.plano.general.entity.PedidoEntity;
import com.archivo.plano.general.interfaces.IPedido;
import com.archivo.plano.general.repository.ClienteRepository;
import com.archivo.plano.general.repository.PedidoRepository;

@Service
public class PedidoImpl implements IPedido {

	private Logger log = LoggerFactory.getLogger(PedidoImpl.class);
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Override
	public ResponseEntity<RespuestaGeneralWS> obtenerTodos() {
		log.info("Entra a consultar todos los pedidos");
		RespuestaGeneralWS respuesta = new RespuestaGeneralWS("Inicio de Proceso",
				ApiAccionesEnum.OBTENER_PEDIDOS.toString(), HttpStatus.OK, null);
		try {
			log.info("Consultando Clientes");
			List<ClienteEntity> clientes = this.clienteRepository.findAll();
			if(clientes == null || clientes.isEmpty()) {
				log.info("Cliente no existente");
				respuesta = new RespuestaGeneralWS("Clientes no existenten", ApiAccionesEnum.OBTENER_PEDIDOS.toString(),
						HttpStatus.NOT_FOUND, null);
				return new ResponseEntity<RespuestaGeneralWS>(respuesta, HttpStatus.NOT_FOUND);
			}
			List<Object> list = new ArrayList();
			for(ClienteEntity cliente: clientes) {
				log.info("Consultando pedidos");
				List<PedidoEntity> pedidos = this.pedidoRepository.obtenerTodosPorCliente(cliente.getIdCliente().longValue());
				if(pedidos != null && !pedidos.isEmpty()) {
					PedidosWsOutput listado = new PedidosWsOutput();
					listado.setCliente(new ClienteWsOutput(cliente.getIdCliente(),cliente.getNombreCliente()));
					listado.setPedidos(new ArrayList<PedidoWsOutput>());
					for(PedidoEntity pedido: pedidos) {
						PedidoWsOutput prod = new PedidoWsOutput();
						prod.setNombreProducto(pedido.getNombreProducto());
						prod.setCantidadProducto(pedido.getCantidadProducto());
						prod.setFechaRequerida(new java.util.Date(pedido.getFechaRequerida().getTime()));
						
						listado.getPedidos().add(prod);
					}
					list.add(listado);
				}
			}
			if(list.isEmpty()) {
				respuesta = new RespuestaGeneralWS("Pedidos no existentes", ApiAccionesEnum.OBTENER_PEDIDOS.toString(),
						HttpStatus.NOT_FOUND, null);
				return new ResponseEntity<RespuestaGeneralWS>(respuesta, HttpStatus.NOT_FOUND);
			}
			respuesta = new RespuestaGeneralWS("Consulta Exitosa", ApiAccionesEnum.OBTENER_PEDIDOS.toString(),
					HttpStatus.OK, list);
		} catch (Exception e) {
			log.info("ERROR " + e.getMessage());
			respuesta = new RespuestaGeneralWS("Error del Sistema", ApiAccionesEnum.OBTENER_PEDIDOS.toString(),
					HttpStatus.INTERNAL_SERVER_ERROR, null);
			return new ResponseEntity<RespuestaGeneralWS>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<RespuestaGeneralWS>(respuesta, HttpStatus.OK);
	}

}
